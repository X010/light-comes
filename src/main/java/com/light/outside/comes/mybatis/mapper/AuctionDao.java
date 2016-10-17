package com.light.outside.comes.mybatis.mapper;

import com.light.outside.comes.model.AuctionModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by b3st9u on 16/10/18.
 */
public interface AuctionDao {
    @Select("select * from comes_auction where status<>9 and id=#{id}")
    public AuctionModel getAuctionsById(@Param("id") int id);

}
