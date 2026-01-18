package com.java_21_demo.app.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java_21_demo.app.domain.vo.PeopleVO;

public interface PeopleVOMapper extends BaseMapper<PeopleVO> {
    List<PeopleVO> selectList();

    PeopleVO selectById(Long id);
}
