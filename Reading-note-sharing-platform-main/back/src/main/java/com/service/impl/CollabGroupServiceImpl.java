package com.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.CollabGroupDao;
import com.entity.CollabGroupEntity;
import com.service.CollabGroupService;
import org.springframework.stereotype.Service;

@Service
public class CollabGroupServiceImpl extends ServiceImpl<CollabGroupDao, CollabGroupEntity> implements CollabGroupService {
}