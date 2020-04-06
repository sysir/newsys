package com.newsys.controller;

import cn.dsna.util.images.ValidateCode;
import com.newsys.pojo.Sort;
import com.newsys.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/sys")
public class SystemController {
    @Autowired
    private SortService sortService;
    @RequestMapping("/memberlist")
    public String tomemberlist() {
        return "member-list";
    }
    @RequestMapping("/allnews")
    public String toNewList(){
        return "allnews";
    }
    @RequestMapping("/cate")
    public String toCate(){
        return "cate";
    }

    @RequestMapping("/pubnews")
    public String pubnews(Integer id,Model model){
        List<Sort> getnavsort = sortService.getnavsort();
        model.addAttribute("navsort",getnavsort);

        return "news-edit";
    }
    @RequestMapping("/towel")
    public String towel() {
        return "welcome";
    }

    @RequestMapping("/login")
    public String toLogin(){
        return "login";
    }
    @RequestMapping("/getCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response){
        ValidateCode validateCode = new ValidateCode(120,36,5,6);
        String code = validateCode.getCode();
        request.getSession().setAttribute("code",code);
        try {
            validateCode.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/register")
    public String toRegister(){
        return "register";
    }


    @RequestMapping("/logout")
    public  void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("msg","已退出账户，将返回新闻首页！");
        request.getSession().invalidate();
        response.sendRedirect("/home/index");
    }
}
