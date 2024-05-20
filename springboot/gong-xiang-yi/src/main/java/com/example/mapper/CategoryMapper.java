package com.example.mapper;

import com.example.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CategoryMapper {


    @Select("select * from category")
    List<Category> list();

    @Select("select category_name from category where id = #{id}")
    String getById(Integer id);

    //修改此类物品数量
    @Update("update category set category_num = category_num + #{i} where id = #{id} or id=0")
    void updateNumById(Integer id,int i);
}
