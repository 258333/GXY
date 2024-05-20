package com.example.controller;

import com.example.pojo.Good;
import com.example.pojo.PageBean;
import com.example.pojo.Result;
import com.example.pojo.User;
import com.example.service.GoodService;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("good")
@Validated
public class GoodController {

    @Autowired
    private GoodService goodService;

    //添加物品
    @PostMapping
    public Result add(@RequestBody @Validated Good good) {
        if (good.getCategoryId() == 0)
            return Result.error("物品分类不能为空");
        goodService.add(good);
        return Result.success();
    }

    //查询所有物品
    @GetMapping
    public Result<PageBean<Good>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String input) {

        PageBean<Good> pb = goodService.list(pageNum, pageSize,userId,categoryId, input);
        return Result.success(pb);
    }

    //跟新物品信息
    @PutMapping
    public Result list(@RequestBody @Validated Good good){
        goodService.update(good);
        return Result.success();
    }

    //删除物品
    @DeleteMapping("{id}")
    public Result delete(@PathVariable Integer id){
        goodService.delete(id);
        return Result.success();
    }

}
