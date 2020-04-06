package com.newsys.controller;

import com.newsys.pojo.Result;
import com.newsys.pojo.Sort;
import com.newsys.pojo.Userinfo;
import com.newsys.service.NewsService;
import com.newsys.service.SortService;
import com.newsys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/main")
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private SortService sortService;

    @RequestMapping("/memberlist")
    public String memberlist() {
        return "member-list";
    }

    //成员表信息
    @RequestMapping("/getusers")
    @ResponseBody
    public Result memberlist(Integer page, Integer limit) {
        Result result = userService.getusers(page, limit);
        return result;
    }

    //表单元格编辑
    @RequestMapping("/update")
    @ResponseBody
    public Result updateTable(Integer uid, String field, String value) {
        Result result = userService.updateById(uid, field, value);
        return result;
    }

    //表tool编辑
    @RequestMapping("/editUser")
    public String edit(Integer uid, Model model) {
        Userinfo userinfo = userService.selectUserById(uid);
        model.addAttribute("UserInfo", userinfo);
        return "member-edit";
    }

    //表tool删除
    @RequestMapping("/delByIds")
    @ResponseBody
    public Result delByIds(Integer[] ids) {
        Result result = userService.delByIds(ids);
        return result;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Result toedit(Integer userId, String userName, String userPhone, String userEmail) {
        Result result = userService.updateUser(userId, userName, userPhone, userEmail);
        return result;
    }

    @RequestMapping("/delByUid")
    @ResponseBody
    public Result delById(Integer uid) {
        Result result = userService.delById(uid);
        return result;
    }

    //模糊查询操作
    @RequestMapping("/search")
    @ResponseBody
    public Result search(Integer uid, String un, String up, Integer stu) {
        Result result = userService.search(uid, un, up, stu);
        return result;
    }

    //news相关操作
    @RequestMapping("/getNews")
    @ResponseBody
    public Result getNews(Integer page, Integer limit, String timerange) {
        Result result = newsService.selectNews(page, limit, timerange);
        return result;
    }

    //编辑删除
    @RequestMapping("/delByNid")
    @ResponseBody
    public Result delByNid(Integer nid) {
        Result result = newsService.delByNid(nid);
        return result;
    }

    //批量删除
    @RequestMapping("/delByNids")
    @ResponseBody
    public Result delByNids(Integer[] nids) {
        Result result = newsService.delByNids(nids);
        return result;
    }

    @RequestMapping("/searchnews")
    @ResponseBody
    public Result searchnews(Integer page, Integer limit, String title, String auther, String content, Integer stu) {
        Result result = newsService.searchnews(page, limit, title, auther, content, stu);
        return result;
    }

    //show_sort
    @RequestMapping("/allSort")
    @ResponseBody
    public Result allSort(Integer page, Integer limit) {
        Result result = sortService.allSort(page, limit);
        return result;
    }

    @RequestMapping("/searchSort")
    @ResponseBody
    public Result searchSort(Integer page, Integer limit, Integer sid, String sname, Integer stu) {
        Result result = sortService.searchSort(page, limit, sid, sname, stu);
        return result;
    }

    @RequestMapping("/linkSort")
    @ResponseBody
    public List<Sort> linkSort(Integer id, Model model) {
        List<Sort> getbarsort = sortService.getbarsortandstu(id);
        return getbarsort;
    }


    @RequestMapping("/delBySid")
    @ResponseBody
    public Result delBySid(Integer sid) {
        Result result = sortService.delBySid(sid);
        return result;
    }

    //add_sort
    @RequestMapping("/toAddSort")
    public String toAddSort(Model model) {
        List<Sort> navsort = sortService.getnavsort();
        model.addAttribute("sortList", navsort);
        return "sort-add";
    }

    @RequestMapping("/addSort")
    @ResponseBody
    public Result addSort(Integer sid, String sname) {
        Result result = sortService.addSort(sid, sname);
        return result;
    }

    @RequestMapping("/beforeNews")
    @ResponseBody
    public void beforeNews(String newTitle){
        newsService.insertTitle(newTitle);
    }

    @RequestMapping("/uploadImg")
    @ResponseBody
    public Result uploadImg(MultipartFile file, HttpServletRequest request) {
        Result result = newsService.uploadImg(file, request);
        return result;
    }

    @RequestMapping("/addnews")
    @ResponseBody
    public Result addnews(String newTitle, Integer mainid, Integer depid, String content, HttpSession session) {
        String auther = (String) session.getAttribute("username");
        Result result = newsService.addNews(newTitle, mainid, depid, content, auther);
        // MainController.this.uploadImg();
        return result;
    }

}
