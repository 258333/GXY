package com.example.controller;

import com.example.pojo.Category;
import com.example.pojo.Result;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
@Validated
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //查询所有类别
    @GetMapping
    public Result list(){
        List<Category> categorys = categoryService.list();
        return Result.success(categorys);
    }

    //根据id查询类别
    @GetMapping("{id}")
    public Result getById(@PathVariable Integer id){
        String categoryName = categoryService.getById(id);
        return Result.success(categoryName);
    }
}
