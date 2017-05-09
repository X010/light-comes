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
    @Insert("insert into comes_overcharged(sku_id,create_time,amount,deposit,subtract_price,title,status,deal_time,remain_count) " +
            " values(#{sku_id},#{create_time},#{amount},#{deposit},#{subtract_price},#{title},#{status},#{deal_time},#{remain_count}) ")
    public int addOvercharged(OverchargedModel overchargedModel);

    @Select("select id,sku_id,create_time,amount,deposit,subtract_price,title,status,deal_time from comes_overcharged ")
    public List<OverchargedModel> queryOverchargedModelList();

    @Update("update comes_overcharged set amount=#{amount},subtract_price=#{subtract_price}," +
            "title=#{title},status=#{status},remain_count=#{remain_count}" +
            " where id=#{id}")
    public int updateOvercharged(OverchargedModel overchargedModel);

    @Delete("delete from comes_overcharged where id=#{id}")
    public int deleteOvercharged(@Param("id") int id);

    @Select("select * from comes_overcharged_record where aid=#{aid} order by amount asc limit 1")
    public OverchargedRecordModel getWinOverChargedRecordModel(@Param("aid") long aid);

    /**
     * 查询砍价记录
     * @param aid 活动ID
     * @param sponsor 发起人ID
     * @return
     */
    @Select("select * from comes_overcharged_record where aid=#{aid} and sponsor=#{sponsor} order by createtime desc ")
    public List<OverchargedRecordModel> getOverchargeRecordHistory(@Param("aid") long aid,@Param("sponsor") long sponsor);

    /**
     * 查询已砍价金额
     * @param aid 活动id
     * @param sponsor 发起人ID
     * @return
     */
    @Select("select IFNULL(sum(amount),0) as oTotal from comes_overcharged_record where aid=#{aid} and sponsor=#{sponsor}")
    public double getOverchargeSubtractPrice(@Param("aid") long aid,@Param("sponsor") long sponsor);

    @Select("select * from comes_overcharged_record where aid=#{aid} order by amount asc")
    public List<OverchargedRecordModel> getOverchargedRecords(@Param("aid") long aid);

    @Select("select * from comes_overcharged_record where aid=#{aid} and sponsor=#{sponsor} order by amount asc")
    public List<OverchargedRecordModel> getOverchargedRecordsByAidUid(@Param("aid") long aid,@Param("sponsor") long sponsor);

    /**
     * 获取已经看到底价
     * @param aid
     * @return
     */
    @Select("select * from comes_overcharged_record where aid=#{aid} and status=5 limit 1")
    public OverchargedRecordModel getOVerchargedRecordsByAid(@Param("aid") long aid);


    @Select("select * from comes_overcharged_record where aid=#{aid} and phone=#{phone} limit 1")
    public OverchargedRecordModel getOverChargedRecordByPhoneAndAid(@Param("aid") long aid, @Param("phone") String phone);


    @Select("select * from comes_overcharged_record where aid=#{aid} and sponsor=#{sponsor} limit 1")
    public OverchargedRecordModel getOverChargedRecordByUidAndAid(@Param("aid") long aid, @Param("sponsor") long sponsor);


    @Select("select * from comes_overcharged_record where aid=#{aid} and uid=#{uid} and sponsor=#{sponsor} limit 1")
    public OverchargedRecordModel getOverChargedRecordByUidAndAidAndSponsor(@Param("aid") long aid,@Param("uid") long uid, @Param("sponsor") long sponsor);

    @Select("select * from comes_overcharged_record where aid=#{aid} and phone=#{phone} sponsor=#{sponsor}  limit 1")
    public OverchargedRecordModel getOverChargedRecordByPhoneAndAidAndSponsor(@Param("aid") long aid,@Param("sponsor") long sponsor, @Param("phone") String phone);

    @Insert("insert into comes_overcharged_record(aname,aid,uid,phone,createtime,status,amount,sponsor) values(#{aname},#{aid},#{uid},#{phone},#{createtime},#{status},#{amount},#{sponsor})")
    @SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
    public long addOverchargedRecordModel(OverchargedRecordModel overchargedRecordModel);


    @Select("select * from comes_overcharged_record where aid=#{aid} order by amount asc limit #{start},#{size}")
    public List<OverchargedRecordModel> getOverchargedRecordByAid(@Param("aid") long aid, @Param("start") int start, @Param("size") int size);

    @Select("select count(1) from comes_overcharged_record where aid=#{aid}")
    public int getOverchargedRecordPageByAidTotal(@Param("aid") long aid);

    @Select("select * from comes_overcharged_record cor,comes_overcharged o where o.id=cor.aid and uid=#{uid} and cor.`status`=#{status} " +
            "order by cor.id desc limit #{start},#{size}")
    public List<OverchargedRecordViewModel> getOverchargedRecordPageByUidAndStatus(@Param("uid") long uid,@Param("status") int status,@Param("start") int start,@Param("size") int size);

    @Select("select *,ROUND(o.amount-sum(cor.amount),2) as now_price from comes_overcharged_record cor,comes_overcharged o" +
            " where o.id=cor.aid and sponsor=#{uid} and cor.`status`=#{status} " +
            " group by sponsor,o.id " +
            " order by cor.id desc limit #{start},#{size}")
    public List<OverchargedRecordViewModel> getOverchargedRecordPricePageByUidAndStatus(@Param("uid") long uid,@Param("status") int status,@Param("start") int start,@Param("size") int size);


    @Select("select *,ROUND(o.amount-sum(cor.amount),2) as now_price from comes_overcharged_record cor,comes_overcharged o" +
            " where o.id=cor.aid and sponsor=#{uid} " +
            " group by sponsor,o.id " +
            " order by cor.id desc limit #{start},#{size}")
    public List<OverchargedRecordViewModel> getOverchargedRecordPricePageByUid(@Param("uid") long uid,@Param("start") int start,@Param("size") int size);

    @Select("select * from comes_overcharged_record cor,comes_overcharged o" +
            " where o.id=cor.aid and uid=#{uid} " +
            " order by cor.id desc limit #{start},#{size}")
    public List<OverchargedRecordViewModel> getOverchargedRecordPageByUid(@Param("uid") long uid,@Param("start") int start,@Param("size") int size);

    @Select("select co.id,co.amount,co.title,co.goodsid,co.good_photo,co.good_name,co.over_amount,co.start_time,co.end_time,co.remain_count from comes_overcharged co,comes_overcharged_record cor\n" +
            "where co.id=cor.aid " +
            "and cor.status=5 " +
            "and cor.sponsor=#{uid} " +
            "and co.goodsid=#{goodsid} ")
    public OverchargedModel queryOverchargedModel(@Param("uid") long uid,@Param("goodsid") long goodsid);

}
