package com.light.outside.comes.mybatis.mapper;

import com.light.outside.comes.model.OverchargedModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by b3st9u on 16/10/15.
 */
public interface OverchargedDao {
    @Insert("insert into comes_overcharged(sku_id,create_time,amount,deposit,subtract_price,title,status,deal_time) " +
            " values(#{sku_id},#{create_time},#{amount},#{deposit},#{subtract_price},#{title},#{status},#{deal_time}) ")
    public int addOvercharged(OverchargedModel overchargedModel);

    @Select("select id,sku_id,create_time,amount,deposit,subtract_price,title,status,deal_time from comes_overcharged ")
    public List<OverchargedModel> queryOverchargedModelList();

    @Update("update comes_overcharged set sku_id=#{sku_id},amount=#{amount},deposit=#{deposit},subtract_price=#{subtract_price},title=#{title},status=#{status},deal_time=#{deal_time}" +
            " where id=#{id}")
    public int updateOvercharged(OverchargedModel overchargedModel);

    @Delete("delete from comes_overcharged where id=#{id}")
    public int deleteOvercharged(@Param("id")int id);

}
