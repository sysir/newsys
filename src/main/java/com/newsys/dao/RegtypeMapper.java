package com.newsys.dao;

import com.newsys.pojo.Regtype;

import java.util.List;

public interface RegtypeMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(Regtype record);

    Regtype selectByPrimaryKey(Integer userId);

    List<Regtype> selectAll();

    int updateByPrimaryKey(Regtype record);
}