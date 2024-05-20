package com.example.service;

import com.example.pojo.Category;

import java.util.List;

public interface CategoryService {

    //查询所有物品分类
    List<Category> list();

    //根据id查询物品分类名称
    String getById(Integer id);
}
