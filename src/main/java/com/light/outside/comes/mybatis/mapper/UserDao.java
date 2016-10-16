package com.light.outside.comes.mybatis.mapper;

import com.light.outside.comes.model.admin.UsersModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by b3st9u on 16/10/16.
 */
public interface UserDao {
    @Select("select id,user_name,`password`,`status` from comes_admin_users where user_name=#{user_name} and `password`=#{password}")
    public UsersModel queryUserByPwd(@Param("user_name") String user_name, @Param("password") String password);


}
