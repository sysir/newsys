package com.newsys.dao;

import com.newsys.pojo.Sort;

import java.util.List;

public interface SortMapper {
    int deleteByPrimaryKey(Integer sortId);

    int insert(Sort record);

    Sort selectByPrimaryKey(Integer sortId);

    List<Sort> selectAll(Integer start, Integer limit);

    int updateByPrimaryKey(Sort record);

    List<Sort> getnavsort();

    List<Sort> getbarsort(Integer nid);

    List<Sort> getbarsortbyid(Integer sortId);

    Integer sortByFid(Integer bid);

    List<Sort> searchSort(Integer start, Integer limit, Integer sid, String sname, Integer stu);

    Integer getCount();

    Integer searchCount(Integer sid, String sname, Integer stu);

    Integer selectIdByFid(String s);

    List<Sort> getbarsortandstu(Integer id);
}