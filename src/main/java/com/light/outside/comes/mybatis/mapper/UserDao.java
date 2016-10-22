package com.light.outside.comes.mybatis.mapper;

import com.light.outside.comes.model.admin.UsersModel;
import com.light.outside.comes.qbkl.model.UserModel;
import org.apache.ibatis.annotations.*;

/**
 * Created by b3st9u on 16/10/16.
 */
public interface UserDao {
    @Select("select id,real_name,head_img,user_name,`password`,`status` from comes_admin_users where user_name=#{user_name} and `password`=#{password}")
    public UsersModel queryUserByPwd(@Param("user_name") String user_name, @Param("password") String password);

    @Insert("insert into comes_admin_users(real_name,user_name,password,status)values(#{real_name},#{user_name},#{password},#{status})")
    @SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
    public long addUser(UsersModel userModel);

    @Update("update comes_admin_users set real_name=#{real_name},user_name=#{user_name},password=#{password},status=#{status}")
    public void updateUser(UsersModel userModel);

    @Select("select * from comes_admin_users where user_name=#{user_name} limit 1")
    public UsersModel getUserByName(@Param("user_name") String user_name);

    @Select("select * from comes_admin_users where id=#{id}")
    public UserModel getUserById(@Param("id") long id);

    @Select("delete  from comes_admin_users where id=#{id}")
    public void deletUser(@Param("id") long id);
}
