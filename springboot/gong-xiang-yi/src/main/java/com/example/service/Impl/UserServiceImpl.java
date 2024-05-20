package com.example.service.Impl;

import com.example.mapper.UserMapper;
import com.example.pojo.User;
import com.example.service.UserService;
import com.example.utils.Md5Util;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //根据用户名查询用户
    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    //注册
    @Override
    public void register(String username, String password) {
        //加密
        String md5String = Md5Util.getMD5String(password);
        //添加
        userMapper.add(username, md5String);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl, id);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd), id);
    }

    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }


    //查询积分最高的前10名
    @Override
    public List<User> getTop10() {
        return userMapper.getTop10();
    }

    //根据积分获取我的排名
    @Override
    public Integer getRank(Integer points) {
        return userMapper.getRank(points);
    }

    //获取用户总数
    @Override
    public Integer getCount() {
        return userMapper.getCount();
    }

    //根据id查询用户的昵称 头像
    @Override
    public Map<String,Object> getPicNick(Integer id) {
        return userMapper.getPicNick(id);
    }

    //邮箱验证 根据用户名修改密码
    @Override
    public void updatePwdByEmail(String password, String username) {
        userMapper.updatePwdByUsername(Md5Util.getMD5String(password),username);
        stringRedisTemplate.delete("verify");
    }
}
