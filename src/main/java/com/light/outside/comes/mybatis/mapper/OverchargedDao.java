package com.light.outside.comes.mybatis.mapper;

import com.light.outside.comes.model.OverchargedModel;
import com.light.outside.comes.model.OverchargedRecordModel;
import com.light.outside.comes.model.OverchargedRecordViewModel;
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
    public int deleteOvercharged(@Param("id") int id);

    @Select("select * from comes_overcharged_record where aid=#{aid} order by amount asc limit 1")
    public OverchargedRecordModel getWinOverChargedRecordModel(@Param("aid") long aid);

    @Select("select * from comes_overcharged_record where aid=#{aid} order by amount asc")
    public List<OverchargedRecordModel> getOverchargedRecords(@Param("aid") long aid);


    @Select("select * from comes_overcharged_record where aid=#{aid} and phone=#{phone} limit 1")
    public OverchargedRecordModel getOverChargedRecordByPhoneAndAid(@Param("aid") long aid, @Param("phone") String phone);

    @Insert("insert into comes_overcharged_record(aname,aid,uid,phone,createtime,status,amount)values(#{aname},#{aid},#{uid},#{phone},#{createtime},#{status},#{amount})")
    @SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
    public long addOverchargedRecordModel(OverchargedRecordModel overchargedRecordModel);


    @Select("select * from comes_overcharged_record where aid=#{aid} order by amount asc limit #{start},#{size}")
    public List<OverchargedRecordModel> getOverchargedRecordByAid(@Param("aid") long aid, @Param("start") int start, @Param("size") int size);

    @Select("select count(1) from comes_overcharged_record where aid=#{aid}")
    public int getOverchargedRecordPageByAidTotal(@Param("aid") long aid);

    @Select("select * from comes_overcharged_record cor,comes_overcharged o where o.id=cor.aid and uid=#{uid} and cor.`status`=#{status} limit #{start},#{size}")
    public List<OverchargedRecordViewModel> getOverchargedRecordPageByUidAndStatus(@Param("uid") long uid,@Param("status") int status,@Param("start") int start,@Param("size") int size);


    @Select("select * from comes_overcharged_record cor,comes_overcharged o where o.id=cor.aid and uid=#{uid} limit #{start},#{size}")
    public List<OverchargedRecordViewModel> getOverchargedRecordPageByUid(@Param("uid") long uid,@Param("start") int start,@Param("size") int size);

}
