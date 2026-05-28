package com.dao;

import com.entity.BijifenxiangEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.BijifenxiangVO;
import com.entity.view.BijifenxiangView;
import org.apache.ibatis.annotations.Select;

/**
 * 笔记分享
 * 
 * @author 
 * @email 
 * @date 2023-02-26
 */
public interface BijifenxiangDao extends BaseMapper<BijifenxiangEntity> {
	
	List<BijifenxiangVO> selectListVO(@Param("ew") Wrapper<BijifenxiangEntity> wrapper);
	
	BijifenxiangVO selectVO(@Param("ew") Wrapper<BijifenxiangEntity> wrapper);
	
	List<BijifenxiangView> selectListView(@Param("ew") Wrapper<BijifenxiangEntity> wrapper);

	List<BijifenxiangView> selectListView(Pagination page,@Param("ew") Wrapper<BijifenxiangEntity> wrapper);
	
	BijifenxiangView selectView(@Param("ew") Wrapper<BijifenxiangEntity> wrapper);

	@Select("${sql}")
	long selectCountBySql(@Param("sql") String sql);

	@Select("${sql}")
	List<BijifenxiangEntity> selectListBySql(@Param("sql") String sql);
}