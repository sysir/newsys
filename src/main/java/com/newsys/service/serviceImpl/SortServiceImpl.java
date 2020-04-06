package com.newsys.service.serviceImpl;

import com.newsys.dao.SortMapper;
import com.newsys.pojo.Result;
import com.newsys.pojo.Sort;
import com.newsys.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class SortServiceImpl implements SortService {
    @Autowired
    private SortMapper sortMapper;

    @Override
    public List<Sort> getnavsort() {
        List<Sort> navsort = sortMapper.getnavsort();
        return navsort;
    }

    @Override
    public List<Sort> getbarsort(Integer nid) {
        if (nid == null) {
            nid = 1;
        }
        List<Sort> barsort = sortMapper.getbarsort(nid);
        return barsort;
    }

    @Override
    public List<Sort> getbarsortbyid(Integer bid) {
        Integer sortId = sortMapper.sortByFid(bid);
        List<Sort> barsort = sortMapper.getbarsortbyid(sortId);
        return barsort;
    }

    @Override
    public Result allSort(Integer page, Integer limit) {
        Result result = new Result();
        Integer start = (page - 1) * limit;
        List<Sort> sortList = sortMapper.selectAll(start, limit);
        Integer count = sortMapper.getCount();
        result.setStatus(0);
        result.setItem(sortList);
        result.setMessage("success");
        result.setTotal(count);
        return result;
    }

    @Override
    public Result searchSort(Integer page, Integer limit, Integer sid, String sname, Integer stu) {
        Result result = new Result();
        Integer start = (page - 1) * limit;
        List<Sort> sortList = sortMapper.searchSort(start, limit, sid, sname, stu);
        if (sid==null&&sname==""&&stu==null){
            result.setTotal(sortMapper.getCount());
        }else{
            result.setTotal(sortMapper.searchCount(sid,sname,stu));
        }
        result.setItem(sortList);
        result.setStatus(0);
        result.setMessage("");
        return result;
    }

    @Transactional
    @Override
    public Result delBySid(Integer sid) {
        Result result = new Result();
        try {
            int i = sortMapper.deleteByPrimaryKey(sid);
            result.setStatus(200);
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
    public Result addSort(Integer sid, String sname) {
        Result result = new Result();
        if (sid==null){
            if (sname==""){
                result.setStatus(400);
                result.setMessage("请输入分类的详细信息");
                return result;
            }else {
                Integer osid = sortMapper.selectIdByFid(null);
                Sort sort = new Sort();
                sort.setSortName(sname);
                sort.setSortStatus(1);
                sort.setSortFid(null);
                sort.setSortId(osid);
                sortMapper.insert(sort);
                result.setStatus(200);
                result.setMessage("success");
                return result;
            }
        }else {
            if (sname==""){
                result.setStatus(400);
                result.setMessage("请输入分类的详细信息");
                return result;
            }else {
                Integer osid = sortMapper.selectIdByFid(sid.toString());
                Sort sort = new Sort();
                sort.setSortName(sname);
                sort.setSortFid(sid);
                sort.setSortStatus(1);
                sort.setSortId(osid+1);
                sortMapper.insert(sort);
                result.setStatus(200);
                result.setMessage("success");
                return result;
            }

        }
    }

    @Override
    public List getbarsortandstu(Integer id) {
        List<Sort> getbarsortandstu = sortMapper.getbarsortandstu(id);
        return getbarsortandstu;
    }
}
