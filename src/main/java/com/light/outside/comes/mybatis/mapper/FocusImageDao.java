package com.light.outside.comes.mybatis.mapper;

import com.light.outside.comes.model.admin.FocusImageModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by b3st9u on 16/10/15.
 */
public interface FocusImageDao {
    @Insert("insert into comes_focus_images(title,`column`,image,link,create_time,`status`) " +
            " values (#{title},#{column},#{image},#{link},#{create_time},#{status})")
    public int insertFocusImage(FocusImageModel focusImageModel);

    @Delete("delete from comes_focus_images where id=#{id}")
    public int deleteFocusImage(@Param("id") int id);

    @Select("select id,title,`column`,image,link,create_time,`status` from comes_focus_images where `status`=1 and `column` =#{column}")
    public List<FocusImageModel> queryFocusImagesByColumn(@Param("column") int column);

    @Update("update comes_focus_images set title=#{title},`column`=#{column},image=#{image},link=#{link} where id=#{id}")
    public int updateFocusImage(FocusImageModel focusImageModel);
}
