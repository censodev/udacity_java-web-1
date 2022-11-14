package com.udacity.jwdnd.course1.cloudstorage.repos;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileRepo {
    @Select("select * from FILES where userid = #{userid}")
    List<File> findByUserId(@Param("userid") int userId);

    @Select("select * from FILES where fileId = #{id}")
    File findById(@Param("id") int id);

    @Select("select * from FILES where filename = #{filename}")
    File findByFilename(@Param("filename") String filename);

    @Insert("insert into FILES(filename, contenttype, filesize, userid) values (#{filename}, #{contenttype}, #{filesize}, #{userid})")
    void insert(File file);

    @Delete("delete from FILES where fileId = #{id}")
    void delete(@Param("id") int id);
}
