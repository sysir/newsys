package com.newsys.dao;

import com.newsys.pojo.Userinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserinfoMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(Userinfo record);

    Userinfo selectByPrimaryKey(Integer userId);

    List<Userinfo> selectAll();

    int updateByPrimaryKey(Userinfo record);

    List<Userinfo> selectUsers(Integer start, Integer limit);

    Integer getCount();

    int updateById(@Param("uid") Integer uid, @Param("field") String field, @Param("value") String value);

    int updateUesr(@Param("userId") Integer userId, @Param("userName") String userName, @Param("userPhone") String userPhone, @Param("userEmail") String userEmail);


    List<Userinfo> search(@Param("uid") Integer uid,@Param("un") String un,@Param("up") String up,@Param("stu") Integer stu);

    Integer getLastUid();

    Userinfo selectByPhone(String userPhone);
}