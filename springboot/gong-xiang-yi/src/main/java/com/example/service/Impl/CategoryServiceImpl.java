package com.example.service.Impl;

import com.example.mapper.CategoryMapper;
import com.example.mapper.UserMapper;
import com.example.pojo.Category;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    //查询所有物品分类
    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }

    //根据id查询分类名称
    @Override
    public String getById(Integer id) {
        return categoryMapper.getById(id);
    }
}
