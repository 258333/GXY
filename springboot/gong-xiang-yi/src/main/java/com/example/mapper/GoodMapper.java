package com.example.mapper;

import com.example.pojo.Good;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodMapper {

    //添加无物品
    @Insert("INSERT INTO good (user_id,category_id,name," +
            "color,size,shape,status,purchase_time," +
            "content,cover_img,phone,email,address," +
            "create_time,update_time) VALUES " +
            "(#{userId}, #{categoryId}, #{name},#{color}," +
            "#{size}, #{shape}, #{status}, #{purchaseTime}, #{content},"+
            "#{coverImg}, #{phone}, #{email}, #{address}, now(), now())")
    void add(Good good);

    //查询所有物品
    List<Good> list(Integer userId,Integer categoryId,String input);


    //更新物品信息
    @Update("UPDATE good SET category_id = #{categoryId}, name = #{name}, "+
            "color = #{color}, size = #{size}, shape = #{shape}, status = #{status}, " +
            "purchase_time = #{purchaseTime}, content = #{content}, cover_img = #{coverImg}, " +
            "phone = #{phone}, email = #{email}, address = #{address}, update_time = now() " +
            "WHERE id = #{id}")
    void update(Good good);

    //删除物品
    @Delete("DELETE FROM good WHERE id = #{id}")
    void delete(Integer id);


    //根据物品id查询分类id
    @Select("SELECT category_id FROM good WHERE id = #{id}")
    Integer getCategoryId(Integer id);
}
