package com.newsys.dao;

import com.newsys.pojo.Comment;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper {
    int deleteByPrimaryKey(@Param("commentId") Integer commentId, @Param("newId") Integer newId);

    int insert(Comment record);

    Comment selectByPrimaryKey(@Param("commentId") Integer commentId, @Param("newId") Integer newId);

    List<Comment> selectAll();

    int updateByPrimaryKey(Comment record);
}