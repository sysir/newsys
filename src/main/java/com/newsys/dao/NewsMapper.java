package com.newsys.dao;

import com.newsys.pojo.News;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NewsMapper {
    int deleteByPrimaryKey(Integer newId);

    int insert(News record);

    News selectByPrimaryKey(Integer newId);

    List<News> selectAll();

    int updateByPrimaryKey(News record);

    List<News> selectBySort(Integer sid);

    List<News> search(String keyWords);

    Integer getCount();

    List<News> searchnews(Integer start, Integer limit, @Param("title") String title, @Param("auther") String auther, @Param("content") String content, @Param("stu") Integer stu);


    List<News> selectNews(@Param("start") Integer start,@Param("limit") Integer limit,@Param("pretime") String pretime,@Param("endtime") String endtime);

    Integer searchCount(String title, String auther, String content, Integer stu);

    Integer selectByTitle(String newTitle);
}