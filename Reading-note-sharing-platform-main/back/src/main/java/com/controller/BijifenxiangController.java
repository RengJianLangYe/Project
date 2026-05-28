package com.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.service.*;
import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.BijifenxiangEntity;
import com.entity.view.BijifenxiangView;
import com.entity.CollabGroupEntity;      // 添加这行
import com.entity.CollabMemberEntity;     // 添加这行

import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;
import com.entity.StoreupEntity;

/**
 * 笔记分享
 * 后端接口
 * @author 
 * @email 
 * @date 2023-02-26
 */
@RestController
@RequestMapping("/bijifenxiang")
public class BijifenxiangController {
    @Autowired
    private BijifenxiangService bijifenxiangService;

    @Autowired
    private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

    @Autowired
    private CollabMemberService collabMemberService;

    @Autowired
    private CollabGroupService collabGroupService;

    /**
     * 后端列表
     */
//    @RequestMapping("/page")
//    public R page(@RequestParam Map<String, Object> params, BijifenxiangEntity bijifenxiang, HttpServletRequest request) {
//        String tableName = request.getSession().getAttribute("tableName").toString();
//        if(tableName.equals("yonghu")) {
//            bijifenxiang.setYonghuming((String)request.getSession().getAttribute("username"));
//        }
//        EntityWrapper<BijifenxiangEntity> ew = new EntityWrapper<BijifenxiangEntity>();
//        PageUtils page = bijifenxiangService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, bijifenxiang), params), params));
//        return R.ok().put("data", page);
//    }

    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, BijifenxiangEntity bijifenxiang, HttpServletRequest request) {
        // 【调试日志】打印当前会话信息，方便排查“查不到数据”的问题
        String tableName = Optional.ofNullable(request.getSession().getAttribute("tableName")).map(Object::toString).orElse(null);
        String userId = Optional.ofNullable(request.getSession().getAttribute("userId")).map(Object::toString).orElse(null);
        String username = Optional.ofNullable(request.getSession().getAttribute("username")).map(Object::toString).orElse(null);

        System.out.println("[Page Query] Table: " + tableName + ", UserId: " + userId + ", Username: " + username);

        EntityWrapper<BijifenxiangEntity> ew = new EntityWrapper<>();

        // 只有当当前登录者是普通用户(yonghu)时，才进行权限过滤
        if ("yonghu".equals(tableName)) {
            List<String> collabNoteIds = new ArrayList<>();

            // 查询该用户已批准的协同笔记 ID
            if (userId != null && !userId.isEmpty()) {
                try {
                    // 1. 查询协同成员表：查找状态为 approved 且 user_id 匹配的记录
                    EntityWrapper<CollabMemberEntity> memberWrapper = new EntityWrapper<>();
                    // 注意：这里假设数据库 collab_member.user_id 存储的是字符串格式的 userId
                    memberWrapper.eq("user_id", userId)
                            .eq("status", "approved");

                    List<CollabMemberEntity> members = collabMemberService.selectList(memberWrapper);
                    System.out.println("[Page Query] Found approved members count: " + (members != null ? members.size() : 0));

                    if (members != null && !members.isEmpty()) {
                        for (CollabMemberEntity member : members) {
                            // 2. 通过 group_id 找到对应的资源组
                            CollabGroupEntity group = collabGroupService.selectById(member.getGroupId());

                            // 确保资源类型是笔记，且 resource_id 有效
                            if (group != null && "bijifenxiang".equals(group.getResourceType())) {
                                String resourceIdStr = group.getResourceId();
                                if (resourceIdStr != null && !resourceIdStr.isEmpty()) {
                                    collabNoteIds.add(resourceIdStr);
                                }
                            }
                        }
                    }
                    System.out.println("[Page Query] Collaborative Note IDs: " + collabNoteIds);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("[Page Query] Error fetching collaborative notes: " + e.getMessage());
                    // 出错时不影响主流程，至少还能看到自己创建的
                }
            }

            // 3. 构建最终查询条件：(自己创建的) OR (协同笔记ID在列表中)
            if (collabNoteIds.isEmpty()) {
                // 如果没有协同笔记，只查自己创建的
                if (username != null && !username.isEmpty()) {
                    ew.eq("yonghuming", username);
                    System.out.println("[Page Query] Condition: yonghuming = " + username);
                } else {
                    // 如果连用户名都没有，返回空结果防止报错或查出所有数据
                    ew.eq("id", -1);
                    System.out.println("[Page Query] Condition: id = -1 (No username found)");
                }
            } else {
                // 【关键】使用 andNew() 开启括号，避免与其他全局条件（如搜索框）冲突
                // SQL 类似: AND (yonghuming = '张三' OR id IN ('1', '2', '3'))
                ew.andNew().eq("yonghuming", username).or().in("id", collabNoteIds);
                System.out.println("[Page Query] Condition: (yonghuming = " + username + ") OR (id IN " + collabNoteIds + ")");
            }
        } else {
            // 如果是管理员或其他角色，可能不需要过滤，或者根据需求添加其他逻辑
            // 默认不添加额外条件，即查询所有符合 params 的数据
            System.out.println("[Page Query] No specific user filter applied for table: " + tableName);
        }

        // 4. 执行分页查询
        PageUtils page = bijifenxiangService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, bijifenxiang), params), params));

        return R.ok().put("data", page);
    }
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,BijifenxiangEntity bijifenxiang, 
		HttpServletRequest request){
        EntityWrapper<BijifenxiangEntity> ew = new EntityWrapper<BijifenxiangEntity>();
		PageUtils page = bijifenxiangService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, bijifenxiang), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( BijifenxiangEntity bijifenxiang){
       	EntityWrapper<BijifenxiangEntity> ew = new EntityWrapper<BijifenxiangEntity>();
      	ew.allEq(MPUtil.allEQMapPre( bijifenxiang, "bijifenxiang")); 
        return R.ok().put("data", bijifenxiangService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(BijifenxiangEntity bijifenxiang){
        EntityWrapper< BijifenxiangEntity> ew = new EntityWrapper< BijifenxiangEntity>();
 		ew.allEq(MPUtil.allEQMapPre( bijifenxiang, "bijifenxiang")); 
		BijifenxiangView bijifenxiangView =  bijifenxiangService.selectView(ew);
		return R.ok("查询笔记分享成功").put("data", bijifenxiangView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        BijifenxiangEntity bijifenxiang = bijifenxiangService.selectById(id);
        return R.ok().put("data", bijifenxiang);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        BijifenxiangEntity bijifenxiang = bijifenxiangService.selectById(id);
        return R.ok().put("data", bijifenxiang);
    }

	    /**
     * 赞或踩
     */
    @RequestMapping("/thumbsup/{id}")
    public R thumbsup(@PathVariable("id") String id,String type){
        BijifenxiangEntity bijifenxiang = bijifenxiangService.selectById(id);
        if(type.equals("1")) {
        	bijifenxiang.setThumbsupnum(bijifenxiang.getThumbsupnum()+1);
        } else {
        	bijifenxiang.setCrazilynum(bijifenxiang.getCrazilynum()+1);
        }
        bijifenxiangService.updateById(bijifenxiang);
        return R.ok();
    }
    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody BijifenxiangEntity bijifenxiang, HttpServletRequest request){
        bijifenxiang.setId(new Date().getTime() + new Double(Math.floor(Math.random()*1000)).longValue());

        Object userIdObj = request.getSession().getAttribute("userId");
        if (userIdObj != null) {
            bijifenxiang.setUserid(userIdObj.toString());
        }

        bijifenxiangService.insert(bijifenxiang);
        return R.ok();
    }

    /**
     * 前端保存
     */
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody BijifenxiangEntity bijifenxiang, HttpServletRequest request){
        bijifenxiang.setId(new Date().getTime() + new Double(Math.floor(Math.random()*1000)).longValue());

        // 获取当前登录用户的 userid
        Object userIdObj = request.getSession().getAttribute("userId");
        if (userIdObj != null) {
            bijifenxiang.setUserid(userIdObj.toString());  // 转换为字符串
        }

        // 获取用户名
        Object usernameObj = request.getSession().getAttribute("username");
        if (usernameObj != null) {
            bijifenxiang.setYonghuming(usernameObj.toString());
        }

        // 设置默认值
        if (bijifenxiang.getEditMode() == null) {
            bijifenxiang.setEditMode("private");
        }
        if (bijifenxiang.getSfsh() == null) {
            bijifenxiang.setSfsh("是");
        }

        bijifenxiangService.insert(bijifenxiang);
        return R.ok();
    }
    /**
     * 修改
     */
    @RequestMapping("/update")
    //@Transactional
    public R update(@RequestBody BijifenxiangEntity bijifenxiang, HttpServletRequest request){
        //ValidatorUtils.validateEntity(bijifenxiang);
        bijifenxiangService.updateById(bijifenxiang);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        bijifenxiangService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<BijifenxiangEntity> wrapper = new EntityWrapper<BijifenxiangEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}

String tableName = request.getSession().getAttribute("tableName").toString();
if(tableName.equals("yonghu")) {
			wrapper.eq("yonghuming", (String)request.getSession().getAttribute("username"));
		}
int count = bijifenxiangService.selectCount(wrapper);
		return R.ok().put("count", count);
	}

    // 在 BijifenxiangController 类中注入服务
    @Autowired
    private RedisLockService redisLockService;


    //锁
    /**
     * 1. 获取编辑锁
     * 访问路径: /bijifenxiang/requestEdit?id=xxx
     */
    @RequestMapping("/requestEdit")
    public R requestEdit(@RequestParam Long id, HttpServletRequest request) {
        // 获取当前登录用户ID (请根据你项目实际获取方式调整，这里假设从 Session 取)
        String userId = (String) request.getSession().getAttribute("userId");
        if (userId == null) {
            return R.error("未登录");
        }

        // 调用你现有的 Service，资源类型为 "note"，过期时间 60秒
        String token = redisLockService.acquireEditLock("note", String.valueOf(id), userId, 60);

        Map<String, Object> data = new HashMap<>();
        if (token != null) {
            data.put("status", "success");
            data.put("token", token); // 把钥匙给前端
            return R.ok().put("data", data);
        } else {
            data.put("status", "busy");
            // 看看是谁在占着坑
            String editor = redisLockService.getCurrentEditor("note", String.valueOf(id));
            data.put("editor", editor);
            return R.error("当前笔记正在被 [" + editor + "] 编辑中，请稍后再试").put("data", data);
        }
    }

    /**
     * 2. 释放编辑锁
     * 访问路径: /bijifenxiang/releaseEdit?id=xxx&token=xxx
     */
    @RequestMapping("/releaseEdit")
    public R releaseEdit(@RequestParam Long id, @RequestParam String token) {
        boolean released = redisLockService.releaseEditLock("note", String.valueOf(id), token);
        if (released) {
            return R.ok();
        } else {
            return R.error("释放失败");
        }
    }

    /**
     * 3. 心跳续期
     * 访问路径: /bijifenxiang/heartbeatEdit?id=xxx&token=xxx
     */
    @RequestMapping("/heartbeatEdit")
    public R heartbeatEdit(@RequestParam Long id, @RequestParam String token) {
        boolean renewed = redisLockService.renewEditLock("note", String.valueOf(id), token, 60);
        if (renewed) {
            return R.ok();
        } else {
            return R.error("权限已丢失");
        }
    }
}