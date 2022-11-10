package com.udacity.jwdnd.course1.cloudstorage.repos;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NoteRepo {
    @Select("select * from NOTES where userid = #{userid}")
    List<Note> findByUserId(@Param("userid") int userId);

    @Insert("insert into NOTES(notetitle, notedescription, userid) values (#{notetitle}, #{notedescription}, #{userid})")
    void insert(Note note);

    @Delete("delete from NOTES where noteid = #{id}")
    void delete(@Param("id") int id);

    @Insert("update NOTES set notetitle = #{notetitle}, notedescription = #{notedescription} where noteid = #{noteid}")
    void update(Note note);

    @Select("select * from NOTES where noteid = #{id}")
    Note findById(@Param("id") int id);
}
