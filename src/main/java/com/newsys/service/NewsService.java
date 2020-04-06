package com.newsys.service;

import com.newsys.pojo.News;
import com.newsys.pojo.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface NewsService {
    List<News> selectAll();

    News selectById(Integer nid);

    List<News> getnewsbyid(Integer nid);

    List<News> search(String keyWords);

    Result selectNews(Integer page, Integer limit, String timerange);

    Result delByNid(Integer nid);

    Result delByNids(Integer[] nids);

    Result searchnews(Integer page, Integer limit, String title, String auther, String content, Integer stu);

    Result uploadImg(MultipartFile file, HttpServletRequest request);


    Result addNews(String newTitle, Integer mainid, Integer depid, String content, String auther);

    void insertTitle(String newTitle);
}
