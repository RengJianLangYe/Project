package com.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.BijifenxiangEntity;
import com.entity.CollabGroupEntity;
import com.entity.CollabMemberEntity;
import com.service.BijifenxiangService;
import com.service.CollabGroupService;
import com.service.CollabMemberService;
import com.service.RedisLockService;
import com.utils.R;

import javax.servlet.http.HttpServletRequest;

/**
 * 协同编辑控制器
 */
@RestController
@RequestMapping("/collab")
public class CollabController {
    @Autowired
    private com.service.YonghuService yonghuService;

    @Autowired
    private CollabGroupService collabGroupService;

    @Autowired
    private CollabMemberService collabMemberService;

    @Autowired
    private BijifenxiangService bijifenxiangService;

    @Autowired
    private RedisLockService redisLockService;

    /**
     * 检查用户是否是已批准的协同成员
     * @param noteId 笔记ID
     * @param userId 用户ID
     * @return R
     */
    @GetMapping("/checkMember")
    public R checkMember(@RequestParam Long noteId, @RequestParam String userId) {
        // 1. 根据 noteId 找到对应的协同小组 (collab_group)
        EntityWrapper<CollabGroupEntity> groupWrapper = new EntityWrapper<>();
        groupWrapper.eq("resource_type", "bijifenxiang")
                .eq("resource_id", noteId.toString());
        CollabGroupEntity group = collabGroupService.selectOne(groupWrapper);

        // 2. 如果没有小组，说明没开启协同，肯定不是成员
        if (group == null) {
            return R.ok().put("isMember", false);
        }

        // 3. 查询该用户在这个小组中，状态是否为 'approved'
        EntityWrapper<CollabMemberEntity> memberWrapper = new EntityWrapper<>();
        memberWrapper.eq("group_id", group.getId())
                .eq("user_id", userId)
                .eq("status", "approved");
        int count = collabMemberService.selectCount(memberWrapper);

        // 4. 返回结果
        return R.ok().put("isMember", count > 0);
    }
    /**
     * 1. 用户申请加入协同编辑
     */
    @PostMapping("/apply")
    public R apply(@RequestBody Map<String, Long> params, HttpServletRequest request) {
        // 【修改】安全获取 userId
        Object userIdObj = request.getSession().getAttribute("userId");
        String userId = (userIdObj != null) ? userIdObj.toString() : null;

        if (userId == null) {
            return R.error("请先登录");
        }

        Long noteId = params.get("noteId");
        if (noteId == null) {
            return R.error("笔记ID不能为空");
        }

        // 查询笔记
        BijifenxiangEntity note = bijifenxiangService.selectById(noteId);
        if (note == null) {
            return R.error("笔记不存在");
        }

        // 检查笔记是否开启协同模式
        if (!"group".equals(note.getEditMode())) {
            return R.error("该笔记未开启协同编辑");
        }

        // 检查是否是笔记主人（主人不需要申请）
        if (note.getUserid() != null && userId.equals(note.getUserid())) {
            return R.error("您是笔记主人，无需申请");
        }

        // 查询小组
        EntityWrapper<CollabGroupEntity> groupWrapper = new EntityWrapper<>();
        groupWrapper.eq("resource_type", "bijifenxiang")
                .eq("resource_id", noteId.toString());
        CollabGroupEntity group = collabGroupService.selectOne(groupWrapper);

        if (group == null) {
            return R.error("协同小组不存在");
        }

        // 检查是否已经申请过
        EntityWrapper<CollabMemberEntity> memberWrapper = new EntityWrapper<>();
        memberWrapper.eq("group_id", group.getId())
                .eq("user_id", userId);
        CollabMemberEntity existing = collabMemberService.selectOne(memberWrapper);

        if (existing != null) {
            if ("pending".equals(existing.getStatus())) {
                return R.error("您的申请正在审核中");
            } else if ("approved".equals(existing.getStatus())) {
                return R.error("您已经是协同成员");
            } else if ("rejected".equals(existing.getStatus())) {
                return R.error("您的申请已被拒绝");
            }
        }

        // 创建申请记录
        CollabMemberEntity member = new CollabMemberEntity();
        member.setGroupId(group.getId());
        member.setUserId(userId);
        member.setStatus("pending");
        member.setAppliedAt(new Date());
        collabMemberService.insert(member);

        return R.ok("申请已提交，等待笔记主人审核");
    }

    /**
     * 2. 笔记主人查看待审核申请列表
     */
    @GetMapping("/pending-list")
    public R getPendingList(@RequestParam String noteId, HttpServletRequest request) {
        // 【修改】安全获取 userId
        Object userIdObj = request.getSession().getAttribute("userId");
        String userId = (userIdObj != null) ? userIdObj.toString() : null;

        if (userId == null) {
            return R.error("请先登录");
        }

        // 转换 noteId 为 Long
        Long noteIdLong;
        try {
            noteIdLong = Long.parseLong(noteId);
        } catch (NumberFormatException e) {
            return R.error("笔记ID格式错误");
        }

        // 查询笔记
        BijifenxiangEntity note = bijifenxiangService.selectById(noteIdLong);
        if (note == null) {
            return R.error("笔记不存在");
        }

        // 验证是否是笔记主人（用 userid 比较）
        if (note.getUserid() == null || !userId.equals(note.getUserid())) {
            return R.error("只有笔记主人可以查看申请列表");
        }

        // 查询小组（使用字符串）
        EntityWrapper<CollabGroupEntity> groupWrapper = new EntityWrapper<>();
        groupWrapper.eq("resource_type", "bijifenxiang")
                .eq("resource_id", noteId);
        CollabGroupEntity group = collabGroupService.selectOne(groupWrapper);

        if (group == null) {
            return R.ok("暂无待审核申请");
        }

        // 查询待审核成员
        EntityWrapper<CollabMemberEntity> memberWrapper = new EntityWrapper<>();
        memberWrapper.eq("group_id", group.getId())
                .eq("status", "pending");
        List<CollabMemberEntity> pendingList = collabMemberService.selectList(memberWrapper);

        return R.ok().put("data", pendingList);
    }

    /**
     * 3. 笔记主人批准/拒绝申请
     */
    @PostMapping("/review")
    public R review(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        // 【修改】安全获取 userId
        Object userIdObj = request.getSession().getAttribute("userId");
        String userId = (userIdObj != null) ? userIdObj.toString() : null;

        if (userId == null) {
            return R.error("请先登录");
        }

        Long applicationId = null;
        String action = null;

        if (params.get("applicationId") instanceof Integer) {
            applicationId = ((Integer) params.get("applicationId")).longValue();
        } else if (params.get("applicationId") instanceof Long) {
            applicationId = (Long) params.get("applicationId");
        }
        action = (String) params.get("action");

        if (applicationId == null) {
            return R.error("申请ID不能为空");
        }
        if (!"approve".equals(action) && !"reject".equals(action)) {
            return R.error("操作类型错误");
        }

        // 获取申请记录
        CollabMemberEntity application = collabMemberService.selectById(applicationId);
        if (application == null) {
            return R.error("申请记录不存在");
        }

        // 获取小组
        CollabGroupEntity group = collabGroupService.selectById(application.getGroupId());
        if (group == null) {
            return R.error("协同小组不存在");
        }

        // 获取笔记
        BijifenxiangEntity note = bijifenxiangService.selectOne(
                new EntityWrapper<BijifenxiangEntity>()
                        .eq("id", group.getResourceId())
        );
        if (note == null) {
            return R.error("笔记不存在");
        }

        // 验证是否是笔记主人（用 userid 比较）
        if (note.getUserid() == null || !userId.equals(note.getUserid())) {
            return R.error("只有笔记主人可以审核申请");
        }

        // 更新申请状态
        application.setStatus("approve".equals(action) ? "approved" : "rejected");
        if ("approve".equals(action)) {
            application.setApprovedAt(new Date());
        }
        collabMemberService.updateById(application);

        String message = "approve".equals(action) ? "已批准用户加入协同编辑" : "已拒绝用户的申请";
        return R.ok(message);
    }

    /**
     * 4. 查看当前用户的申请状态
     */
    @GetMapping("/my-status")
    public R getMyStatus(@RequestParam String noteId, HttpServletRequest request) {
        // 【修改】安全获取 userId
        Object userIdObj = request.getSession().getAttribute("userId");
        String userId = (userIdObj != null) ? userIdObj.toString() : null;

        if (userId == null) {
            return R.error("请先登录");
        }

        // 转换 noteId 为 Long
        Long noteIdLong;
        try {
            noteIdLong = Long.parseLong(noteId);
        } catch (NumberFormatException e) {
            return R.error("笔记ID格式错误");
        }

        // 查询笔记
        BijifenxiangEntity note = bijifenxiangService.selectById(noteIdLong);
        if (note == null) {
            return R.error("笔记不存在");
        }

        // 如果是笔记主人
        // 如果是笔记主人（用 userid 比较）
        if (note.getUserid() != null && userId.equals(note.getUserid())) {
            Map<String, Object> result = new HashMap<>();
            result.put("isOwner", true);
            result.put("status", "owner");
            return R.ok(result);
        }

        // 查询小组（使用字符串）
        EntityWrapper<CollabGroupEntity> groupWrapper = new EntityWrapper<>();
        groupWrapper.eq("resource_type", "bijifenxiang")
                .eq("resource_id", noteId);
        CollabGroupEntity group = collabGroupService.selectOne(groupWrapper);

        if (group == null) {
            Map<String, Object> result = new HashMap<>();
            result.put("isOwner", false);
            result.put("status", "not_available");
            result.put("msg", "笔记未开启协同编辑");
            return R.ok(result);
        }

        // 查询申请状态
        EntityWrapper<CollabMemberEntity> memberWrapper = new EntityWrapper<>();
        memberWrapper.eq("group_id", group.getId())
                .eq("user_id", userId);
        CollabMemberEntity member = collabMemberService.selectOne(memberWrapper);

        Map<String, Object> result = new HashMap<>();
        result.put("isOwner", false);

        if (member == null) {
            result.put("status", "not_applied");
            result.put("msg", "未申请");
        } else {
            result.put("status", member.getStatus());
            if ("pending".equals(member.getStatus())) {
                result.put("msg", "审核中");
            } else if ("approved".equals(member.getStatus())) {
                result.put("msg", "已批准，可参与协同编辑");
            } else if ("rejected".equals(member.getStatus())) {
                result.put("msg", "已拒绝");
            }
        }

        return R.ok(result);
    }

    /**
     * 5. 获取笔记的所有协同成员（已批准的）
     */
    @GetMapping("/members")
    public R getMembers(@RequestParam String noteId, HttpServletRequest request) {
        // 【修改】安全获取 userId
        Object userIdObj = request.getSession().getAttribute("userId");
        String userId = (userIdObj != null) ? userIdObj.toString() : null;

        if (userId == null) {
            return R.error("请先登录");
        }

        // 转换 noteId 为 Long（用于验证笔记是否存在）
        Long noteIdLong;
        try {
            noteIdLong = Long.parseLong(noteId);
        } catch (NumberFormatException e) {
            return R.error("笔记ID格式错误");
        }

        // 验证笔记是否存在
        BijifenxiangEntity note = bijifenxiangService.selectById(noteIdLong);
        if (note == null) {
            return R.error("笔记不存在");
        }

        // 查询小组（使用字符串）
        EntityWrapper<CollabGroupEntity> groupWrapper = new EntityWrapper<>();
        groupWrapper.eq("resource_type", "bijifenxiang")
                .eq("resource_id", noteId);
        CollabGroupEntity group = collabGroupService.selectOne(groupWrapper);

        if (group == null) {
            return R.ok("暂无协同成员");
        }

        // 查询已批准的成员
        EntityWrapper<CollabMemberEntity> memberWrapper = new EntityWrapper<>();
        memberWrapper.eq("group_id", group.getId())
                .eq("status", "approved");
        List<CollabMemberEntity> members = collabMemberService.selectList(memberWrapper);

        return R.ok().put("data", members);
    }

    /**
     * 6. 获取编辑锁（开始编辑前调用）
     */
    @PostMapping("/acquire-lock")
    public R acquireEditLock(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        // 优先从请求参数获取 userId
        String userId = null;
        if (params.get("userId") != null) {
            userId = params.get("userId").toString();
        }

        // 如果没有，从 Session 获取
        if (userId == null) {
            Object userIdObj = request.getSession().getAttribute("userId");
            userId = (userIdObj != null) ? userIdObj.toString() : null;
        }

        // 最后用默认值
        if (userId == null) {
            userId = "1773073964082";
        }

        Long noteId = null;
        if (params.get("noteId") != null) {
            noteId = Long.parseLong(params.get("noteId").toString());
        }

        if (noteId == null) {
            return R.error("笔记ID不能为空");
        }

        // 检查是否有编辑权限
        BijifenxiangEntity note = bijifenxiangService.selectById(noteId);
        if (note == null) {
            return R.error("笔记不存在");
        }

        // 检查是否是主人或已批准的成员
        boolean canEdit = false;
        if (note.getUserid() != null && userId.equals(note.getUserid())) {
            canEdit = true;
        } else {
            EntityWrapper<CollabGroupEntity> groupWrapper = new EntityWrapper<>();
            groupWrapper.eq("resource_type", "bijifenxiang")
                    .eq("resource_id", noteId.toString());
            CollabGroupEntity group = collabGroupService.selectOne(groupWrapper);
            if (group != null) {
                EntityWrapper<CollabMemberEntity> memberWrapper = new EntityWrapper<>();
                memberWrapper.eq("group_id", group.getId())
                        .eq("user_id", userId)
                        .eq("status", "approved");
                CollabMemberEntity member = collabMemberService.selectOne(memberWrapper);
                canEdit = (member != null);
            }
        }

        if (!canEdit) {
            return R.error("您没有编辑此笔记的权限");
        }

        // 尝试获取锁
        String lockToken = redisLockService.acquireEditLock("bijifenxiang", noteId.toString(), userId, 120);

        if (lockToken == null) {
            String currentEditor = redisLockService.getCurrentEditor("bijifenxiang", noteId.toString());
            return R.error("笔记正在被 " + currentEditor + " 编辑，请稍后再试");
        }

        Map<String, Object> result = new HashMap<>();
        result.put("lockToken", lockToken);
        result.put("expireSeconds", 120);
        return R.ok(result);
    }

    /**
     * 7. 释放编辑锁（编辑完成或取消时调用）
     */
    @PostMapping("/release-lock")
    public R releaseEditLock(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        // 【修改】安全获取 userId
        Object userIdObj = request.getSession().getAttribute("userId");
        String userId = (userIdObj != null) ? userIdObj.toString() : null;

        if (userId == null) {
            return R.error("请先登录");
        }

        Long noteId = null;
        String lockToken = (String) params.get("lockToken");

        if (params.get("noteId") instanceof Integer) {
            noteId = ((Integer) params.get("noteId")).longValue();
        } else if (params.get("noteId") instanceof Long) {
            noteId = (Long) params.get("noteId");
        }

        if (noteId == null) {
            return R.error("笔记ID不能为空");
        }
        if (lockToken == null) {
            return R.error("锁令牌不能为空");
        }

        boolean released = redisLockService.releaseEditLock("bijifenxiang", noteId.toString(), lockToken);

        if (released) {
            return R.ok("已释放编辑锁");
        } else {
            return R.error("释放失败，锁不存在或已被他人获取");
        }
    }

    /**
     * 8. 续期锁（编辑中定时调用，防止锁过期）
     */
    @PostMapping("/renew-lock")
    public R renewEditLock(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        // 【修改】安全获取 userId
        Object userIdObj = request.getSession().getAttribute("userId");
        String userId = (userIdObj != null) ? userIdObj.toString() : null;

        if (userId == null) {
            return R.error("请先登录");
        }

        Long noteId = null;
        String lockToken = (String) params.get("lockToken");

        if (params.get("noteId") instanceof Integer) {
            noteId = ((Integer) params.get("noteId")).longValue();
        } else if (params.get("noteId") instanceof Long) {
            noteId = (Long) params.get("noteId");
        }

        if (noteId == null) {
            return R.error("笔记ID不能为空");
        }
        if (lockToken == null) {
            return R.error("锁令牌不能为空");
        }

        boolean renewed = redisLockService.renewEditLock("bijifenxiang", noteId.toString(), lockToken, 120);

        if (renewed) {
            return R.ok("锁已续期，剩余120秒");
        } else {
            return R.error("续期失败，请重新获取锁");
        }
    }

    /**
     * 创建协同小组（开启协同时调用）
     */
    @PostMapping("/createGroup")
    public R createGroup(@RequestBody Map<String, Object> params) {
        Long noteId;
        String userId;

        try {
            noteId = Long.parseLong(params.get("noteId").toString());
            userId = params.get("userId").toString();
        } catch (Exception e) {
            return R.error("参数错误");
        }

        // 检查是否已存在
        EntityWrapper<CollabGroupEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("resource_type", "bijifenxiang")
                .eq("resource_id", noteId.toString());
        CollabGroupEntity existGroup = collabGroupService.selectOne(wrapper);

        if (existGroup == null) {
            // 创建小组
            CollabGroupEntity group = new CollabGroupEntity();
            group.setResourceType("bijifenxiang");
            group.setResourceId(noteId.toString());
            group.setOwnerUserId(userId);
            group.setCreatedAt(new Date());
            collabGroupService.insert(group);

            // 主人自动成为已批准成员
            CollabMemberEntity member = new CollabMemberEntity();
            member.setGroupId(group.getId());
            member.setUserId(userId);
            member.setStatus("approved");
            member.setAppliedAt(new Date());
            member.setApprovedAt(new Date());
            collabMemberService.insert(member);

            return R.ok("小组创建成功");
        }
        return R.ok("小组已存在");
    }

    /**
     * 获取笔记的锁状态（谁在编辑）
     */
    @GetMapping("/lock-status")
    public R getLockStatus(@RequestParam Long noteId) {
        String currentEditor = redisLockService.getCurrentEditor("bijifenxiang", noteId.toString());

        Map<String, Object> result = new HashMap<>();
        if (currentEditor != null) {
            result.put("isLocked", true);
            result.put("editorId", currentEditor);

            // 查询用户名
            try {
                EntityWrapper<com.entity.YonghuEntity> wrapper = new EntityWrapper<>();
                wrapper.eq("userid", currentEditor);
                // 正确写法
                com.entity.YonghuEntity user = yonghuService.selectOne(wrapper);
                result.put("editorName", user != null ? user.getYonghuming() : currentEditor);
            } catch (Exception e) {
                result.put("editorName", currentEditor);
            }
        } else {
            result.put("isLocked", false);
            result.put("editorId", null);
            result.put("editorName", null);
        }
        return R.ok(result);
    }
}