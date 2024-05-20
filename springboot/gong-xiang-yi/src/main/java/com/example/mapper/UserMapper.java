package com.example.mapper;

import com.example.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    //根据用户名查询用户
    @Select("SELECT * FROM user WHERE username=#{username}")
    User findByUsername(String username);

    //添加用户
    @Insert("INSERT INTO user(username, password,create_time,update_time) " +
            "VALUES(#{username}, #{password},now(),now())")
    void add(String username, String password);

    @Update("update user set nickname=#{nickname},name=#{name},phone=#{phone},email=#{email} ,update_time=#{updateTime} where id=#{id}")
    void update(User user);

    @Update("update user set user_pic=#{avatarUrl},update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl, Integer id);

    @Update("update user set password=#{newPwd},update_time=now() where id=#{id}")
    void updatePwd(String newPwd, Integer id);

    //根据id查询用户详细信息
    @Select("SELECT * FROM user WHERE id=#{id}")
    User getById(Integer id);

    //更新用户积分
    @Update("update user set points=points+#{i} where id=#{userId}")
    void addPoints(int i, Integer userId);

    //查询积分最高的前10名并从高到低排序
    @Select("SELECT * FROM user ORDER BY points DESC LIMIT 5")
    List<User> getTop10();

    //根据积分获取我的排名
    @Select("SELECT COUNT(*) + 1 FROM user WHERE points > #{points}")
    int getRank(int points);

    //查询用户总数
    @Select("SELECT COUNT(*) FROM user")
    Integer getCount();

    //根据id查询所有用户的id 昵称 和头像 返回map
    //不知道该怎么将id列表 写到sql语句中
//    List<Map<String, Object>> getUserInfoById(List<Integer> id);


    //根据id查询用户的id 昵称 和头像 返回map
    @Select("SELECT id,nickname,user_pic as userPic FROM user WHERE id=#{id}")
    Map<String, Object> getPicNick(Integer id);

    //根据用户id查询用户邮箱
    @Select("SELECT email FROM user WHERE id=#{id}")
    String getEmail(Integer id);

    //根据用户名查询用户邮箱
    @Select("SELECT email FROM user WHERE username=#{username}")
    String getEmailByUsername(String username);

    //根据用户名修改密码
    @Update("UPDATE user SET password=#{password} WHERE username=#{username}")
    void updatePwdByUsername(String password, String username);
}
