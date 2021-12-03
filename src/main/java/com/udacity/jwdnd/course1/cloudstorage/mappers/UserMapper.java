package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {

    @Select("SELECT * FROM Users WHERE username = #{username}")
    Users getUser(String username);

    @Insert("INSERT INTO Users (username, salt, password, firstname, lastname) VALUES (#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int insertUser(Users user);
}
