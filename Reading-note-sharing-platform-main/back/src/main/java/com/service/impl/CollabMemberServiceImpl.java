package com.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.CollabMemberDao;
import com.entity.CollabMemberEntity;
import com.service.CollabMemberService;

@Service
public class CollabMemberServiceImpl extends ServiceImpl<CollabMemberDao, CollabMemberEntity> implements CollabMemberService {
}