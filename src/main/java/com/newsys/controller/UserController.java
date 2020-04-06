package com.newsys.controller;

import com.newsys.pojo.Result;
import com.newsys.service.UserService;
import com.newsys.utils.CheckValue;
import com.newsys.utils.SendSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public Result login(String username, String password, String code, HttpServletRequest request, HttpServletResponse response) {
        Result result = userService.login(username, password, code, request,response);

        return result;
    }

    @RequestMapping("/getTelCode")
    @ResponseBody
    public Result getTelCode(String tel, HttpSession session) {
        Result result = new Result();
        boolean phone = CheckValue.isPhone(tel);
        String telResult = SendSms.getTelResult(tel);
        if (phone) {
            if (telResult.equals("500")) {
                result.setStatus(500);
                result.setMessage("获取验证码失败！");
            } else {
                result.setStatus(200);
                session.setAttribute("telResult",telResult);
                result.setMessage("请注意查收验证码！");
            }
        } else {
            result.setStatus(500);
            result.setMessage("请输入正确手机号码！");
        }
        return result;
    }

    @RequestMapping("/register")
    @ResponseBody
    public Result register(String userPhone, String telCode, String password,HttpSession session) {
        String codeValue = (String) session.getAttribute("telResult");
        Result result = userService.addUser(userPhone, password, telCode,codeValue);
        return result;
    }
}
