package com.udacity.jwdnd.course1.cloudstorage.repos;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRepo {
    @Select("select * from USERS where username = #{usn}")
    User findByUsername(@Param("usn") String username);

    @Insert("insert into USERS(username, password, firstname, lastname) values (#{username}, #{password}, #{firstname}, #{lastname})")
    void insert(User user);
}
