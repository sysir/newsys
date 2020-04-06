package com.newsys.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

public class Md5Util {

    public static  String  encryption(String password,String username){
        Md5Hash md5Hash = new Md5Hash(password,username,96);
        return  md5Hash.toString();
    }
}
