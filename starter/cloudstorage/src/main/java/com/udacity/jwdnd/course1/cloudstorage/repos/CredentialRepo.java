package com.udacity.jwdnd.course1.cloudstorage.repos;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CredentialRepo {
    @Select("select * from CREDENTIALS where userid = #{userid}")
    List<Credential> findByUserId(@Param("userid") int userId);

    @Insert("insert into CREDENTIALS(url, username, password, userid) values (#{url}, #{username}, #{password}, #{userid})")
    void insert(Credential note);

    @Delete("delete from CREDENTIALS where credentialid = #{id}")
    void delete(@Param("id") int id);

    @Insert("update CREDENTIALS set url = #{url}, username = #{username}, password = #{password} where credentialid = #{credentialid}")
    void update(Credential note);

    @Select("select * from CREDENTIALS where credentialid = #{id}")
    Credential findById(@Param("id") int id);
}
