package com.example.service;

import com.example.pojo.Good;
import com.example.pojo.PageBean;

import java.util.List;

public interface GoodService {

    //添加物品
    void add(Good good);

    //查询所有物品
    PageBean<Good> list(Integer pageNum, Integer pageSize,Integer userId,Integer categoryId,String input);


    //跟新物品信息
    void update(Good good);

    //删除物品
    void delete(Integer id);
}
