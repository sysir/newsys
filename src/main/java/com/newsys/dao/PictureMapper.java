package com.newsys.dao;

import com.newsys.pojo.Picture;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PictureMapper {
    int deleteByPrimaryKey(@Param("picId") Integer picId, @Param("newId") Integer newId);

    int insert(Picture record);

    Picture selectByPrimaryKey(@Param("picId") Integer picId, @Param("newId") Integer newId);

    List<Picture> selectAll();

    int updateByPrimaryKey(Picture record);
}