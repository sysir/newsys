package com.newsys.service;

import com.newsys.pojo.Result;
import com.newsys.pojo.Userinfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
    Result getusers(Integer page, Integer limit);

    Result updateById(Integer uid, String field, String value);

    Userinfo selectUserById(Integer uid);

    Result delById(Integer uid);

    Result updateUser(Integer userId, String userName,String userPhone, String userEmail);


    Result search(Integer uid, String un, String up, Integer stu);

    Result delByIds(Integer[] ids);

    Result login(String username, String password, String code, HttpServletRequest request, HttpServletResponse response);


    Result addUser(String userPhone, String password, String telCode, String codeValue);
}
