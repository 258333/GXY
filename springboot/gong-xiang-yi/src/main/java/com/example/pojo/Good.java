package com.example.pojo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Good {

    private Integer id;
//    @NotNull(message = "分类不能为空")
    private Integer categoryId;        //分类id
    private Integer userId;            //发布物品的用户id
    @NotEmpty(message = "物品名称不能为空")
    private String name;            //物品名称
    private String color;                //物品颜色
    private String size;                //物品大小
    private String shape;                //物品形状
    private String status;              //物品状态
    private java.sql.Timestamp purchaseTime;    //购买时间
    private String coverImg;        //物品图片
    private String content;         //物品其他信息
    private String phone;           //手机号
    @Email(message = "邮箱格式不正确")
    private String email;           //邮箱
    private String address;         //物品地址

    private java.sql.Timestamp createTime;  //发布时间
    private java.sql.Timestamp updateTime;  //更新时间


}
