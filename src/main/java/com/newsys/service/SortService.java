package com.newsys.service;

import com.newsys.pojo.Result;
import com.newsys.pojo.Sort;

import java.util.List;

public interface SortService {

    List<Sort> getnavsort();

    List<Sort> getbarsort(Integer nid);

    List<Sort> getbarsortbyid(Integer bid);


    Result allSort(Integer page, Integer limit);

    Result searchSort(Integer page, Integer limit, Integer sid, String sname, Integer stu);

    Result delBySid(Integer sid);

    Result addSort(Integer sid, String sname);

    List<Sort> getbarsortandstu(Integer id);
}
