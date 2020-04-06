package com.newsys.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

public class testCon {
    public static void main(String[] args) {
        /*Date now = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = s.format(now);//格式化日期转换为字符串
        System.out.println(strDate);
        System.out.println(s.format(now));*/

        Md5Hash md5Hash = new Md5Hash("123456","18875280631",96);
        System.out.println(md5Hash);
    }
}
