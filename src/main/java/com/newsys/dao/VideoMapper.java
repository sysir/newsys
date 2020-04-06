package com.newsys.dao;

import com.newsys.pojo.Video;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VideoMapper {
    int deleteByPrimaryKey(@Param("videoId") Integer videoId, @Param("newId") Integer newId);

    int insert(Video record);

    Video selectByPrimaryKey(@Param("videoId") Integer videoId, @Param("newId") Integer newId);

    List<Video> selectAll();

    int updateByPrimaryKey(Video record);
}