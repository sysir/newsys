package com.newsys.service.serviceImpl;

import com.newsys.dao.NewsMapper;
import com.newsys.pojo.News;
import com.newsys.pojo.Result;
import com.newsys.service.NewsService;
import com.newsys.utils.UpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapper newsMapper;



    @Override
    public List<News> selectAll() {
        List<News> newsList = newsMapper.selectAll();
        return newsList;
    }

    @Override
    public News selectById(Integer nid) {
        Integer newId = nid;
        News newsinfo = newsMapper.selectByPrimaryKey(newId);
        return newsinfo;
    }

    //通过点击nav显示news
    @Override
    public List<News> getnewsbyid(Integer nid) {
        List<News> newsList = newsMapper.selectBySort(nid);
        return newsList;
    }

    @Override
    public List<News> search(String keyWords) {
        if (keyWords.equals("")) {
            keyWords = null;
        }
        List<News> newsList = newsMapper.search(keyWords);
        return newsList;
    }

    @Override
    public Result selectNews(Integer page, Integer limit, String timerange) {
        Result result = new Result();
        List<News> list = null;
        Integer start = (page - 1) * limit;
        String[] split = null;
        if (timerange != null && timerange != "") {
            split = timerange.split("~");
            list = newsMapper.selectNews(start, limit, split[0], split[1]);
        } else {
            list = newsMapper.selectNews(start, limit, null, null);
        }
        Integer count = newsMapper.getCount();
        result.setItem(list);
        result.setStatus(0);
        result.setMessage("查询到" + count + "条数据");
        result.setTotal(count);
        return result;
    }

    @Transactional
    @Override
    public Result delByNid(Integer nid) {
        Result result = new Result();
        try {
            int i = newsMapper.deleteByPrimaryKey(nid);
            result.setStatus(0);
            result.setMessage("删除成功");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setMessage("未知异常!");
            result.setStatus(500);
            return result;
        }
    }

    @Override
    public Result delByNids(Integer[] nids) {
        Result result = new Result();
        try {
            for (int i = 0; i < nids.length; i++) {
                //进行删除操作
                int res = newsMapper.deleteByPrimaryKey(nids[i]);
            }
            result.setStatus(0);
            result.setMessage("删除成功");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setMessage("未知异常!");
            result.setStatus(500);
            return result;
        }
    }

    @Override
    public Result searchnews(Integer page, Integer limit, String title, String auther, String content, Integer stu) {
        Result result = new Result();
        Integer start = (page - 1) * limit;
        List<News> newsList = newsMapper.searchnews(start, limit, title, auther, content, stu);
        if (title == "" && auther == "" && content == "" && stu == null) {
            result.setTotal(newsMapper.getCount());
        } else {
            result.setTotal(newsMapper.searchCount(title, auther, content, stu));
        }
        result.setStatus(0);
        result.setItem(newsList);
        result.setMessage("");
        return result;
    }
    Integer addId = null;
    boolean flag=true;
    @Override
    public void insertTitle(String newTitle) {
        News news = new News();
        if (flag){
            news.setNewTitle(newTitle);
            newsMapper.insert(news);
            addId = newsMapper.selectByTitle(newTitle);
            flag=false;
        }else{
            news.setNewTitle(newTitle);
            news.setNewId(addId);
            newsMapper.updateByPrimaryKey(news);
        }




    }

    @Override
    public Result uploadImg(MultipartFile file, HttpServletRequest request) {
        Result result = new Result();
        try {
            UpUtils.upfile(file, request);


            result.setMessage(file.getOriginalFilename());
            result.setStatus(0);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(500);
            result.setMessage("网路异常!");
            return result;

        }
    }
    @Transactional
    @Override
    public Result addNews(String newTitle, Integer mainid, Integer depid, String content, String auther) {
        Result result = new Result();
        News news = new News();
        try {
            news.setAuther(auther);
            news.setNewTitle(newTitle);
            news.setSortId(depid);
            news.setCreatedate(new Date());
            news.setNewStatus(0);
            news.setContent(content);
            int i = newsMapper.insert(news);
            result.setStatus(200);
            result.setMessage("编辑成功");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setMessage("未知异常!");
            result.setStatus(500);
            return result;
        }
    }
}
