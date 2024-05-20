package com.example.service.Impl;

import com.example.mapper.CategoryMapper;
import com.example.mapper.GoodMapper;
import com.example.mapper.UserMapper;
import com.example.pojo.Good;
import com.example.pojo.PageBean;
import com.example.service.GoodService;
import com.example.service.UserService;
import com.example.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GoodServiceImpl implements GoodService {

    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategoryMapper categoryMapper;
    //添加物品 并且用户积分加5 并且此分类数量加1
    @Override
    public void add(Good good) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        good.setUserId(userId);
        goodMapper.add(good);
        //增加用户的积分
        userMapper.addPoints(5,userId);
        categoryMapper.updateNumById(good.getCategoryId(),1);

    }

    //查询所有物品
    @Override
    public PageBean<Good> list(Integer pageNum, Integer pageSize,Integer userId,Integer categoryId,String input) {
        //创建pageBean对象
        PageBean<Good> pb = new PageBean<>();
        //开启分页查询 PageHelper
        PageHelper.startPage(pageNum, pageSize);
        //调用mapper
        List<Good> goods = goodMapper.list(userId,categoryId,input);
        //Page中提供了方法，可以获取PageHelper
        //获取到当前页和总页数分页查询后   得到的总记录条数和当前页数据
        Page<Good> page = (Page<Good>) goods;

        //把数据填充到PageBean对象中
        pb.setTotal(page.getTotal());
        pb.setItems(page.getResult());
        return pb;
    }


    //更新物品信息
    @Override
    public void update(Good good) {
        goodMapper.update(good);
    }

    //删除物品
    @Override
    public void delete(Integer id) {
        categoryMapper.updateNumById(goodMapper.getCategoryId(id),-1);
        goodMapper.delete(id);

    }
}
