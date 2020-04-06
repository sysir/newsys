package com.newsys.controller;

import com.newsys.pojo.News;
import com.newsys.pojo.Sort;
import com.newsys.service.NewsService;
import com.newsys.service.SortService;
import com.newsys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private SortService sortService;
    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String tonewslist(Integer sid, Model model) {
        //nav_list
        List<Sort> navsort = sortService.getnavsort();
        model.addAttribute("navsort", navsort);
        //bar_list
        List<Sort> barsort = sortService.getbarsort(sid);
        model.addAttribute("barsort", barsort);
        List<News> newsList = newsService.selectAll();
        model.addAttribute("newsList", newsList);

        return "newslist";
    }

    @RequestMapping("/sortNavId")
    public String navClick(Integer nid, Model model) {
        //nav_sort_id_click
        List<News> sortnews = newsService.getnewsbyid(nid);
        model.addAttribute("newsList", sortnews);

        List<Sort> navsort = sortService.getnavsort();
        model.addAttribute("navsort", navsort);

        List<Sort> barsort = sortService.getbarsort(nid);
        model.addAttribute("barsort", barsort);
        return "newslist";
    }

    @RequestMapping("/sortBarId")
    public String barClick(Integer bid, Model model) {
        //nav_sort_id_click
        List<News> sortnews = newsService.getnewsbyid(bid);
        model.addAttribute("newsList", sortnews);

        List<Sort> navsort = sortService.getnavsort();
        model.addAttribute("navsort", navsort);

        List<Sort> barsort = sortService.getbarsortbyid(bid);
        model.addAttribute("barsort", barsort);
        return "newslist";
    }

    @RequestMapping("/toindex")
    public String toindex() {
        return "index";
    }

    @RequestMapping("/toNewById")
    public String tonews(Integer nid, Model model) {
        News newsinfo = newsService.selectById(nid);
        model.addAttribute("newsinfo", newsinfo);
        //navlist
        List<Sort> navsort = sortService.getnavsort();
        model.addAttribute("navsort", navsort);
        return "article";
    }

    @RequestMapping("/search")
    public String search(String keyWords,Model model){
        //nav_list
        List<Sort> navsort = sortService.getnavsort();
        model.addAttribute("navsort", navsort);

        List<News> newsList=newsService.search(keyWords);
        model.addAttribute("newsList", newsList);
        return "search";
    }

}
