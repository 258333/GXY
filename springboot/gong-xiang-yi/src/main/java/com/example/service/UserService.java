package com.example.service;

import com.example.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    //根据用户名查找用户
    User findByUsername(String username);

    //注册
    void register(String username, String password);

    //更新
    void update(User user);

    //更新头像
    void updateAvatar(String avatarUrl);

    //修改密码
    void updatePwd(String newPwd);

    //根据id查询用户
    User getById(Integer id);

    //查询积分最高的前10名
    List<User> getTop10();

    //根据积分获取我的排名
    Integer getRank(Integer points);

    //获取用户总数
    Integer getCount();

    //根据id查询用户的昵称 头像
    Map<String,Object> getPicNick(Integer id);

    //修改密码 通过邮箱验证 根据用户名修改
    void updatePwdByEmail(String password, String username);
}
