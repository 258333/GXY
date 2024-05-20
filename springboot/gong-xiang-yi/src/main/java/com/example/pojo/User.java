package com.example.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    @NotNull
    private Integer id;//主键ID

    private String username;//用户名

    @JsonIgnore//让SpringMvc把当前对象转换成JSON格式的数据时忽略password，最终的JSON就没有password这个属性
    private String password;//密码

    private  Integer role;//角色

    private String name; //用户姓名
    //    @NotEmpty
//    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;//昵称

    //    @NotEmpty
    @Email(message = "邮箱格式不正确")
    private String email;//邮箱

    private String phone;//手机号

    private String userPic;//用户头像地址

    private Integer points;//公益积分

    private LocalDateTime createTime;//创建时间

    private LocalDateTime updateTime;//更新时间
}
