package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilesMapper {

    @Select("SELECT * FROM Files WHERE userid = #{userid}")
    List<Files> getFiles(Integer userid);

    @Insert("INSERT INTO Files (filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    int insertFile(Files file);

    @Delete("DELETE FROM Files WHERE fileid = #{fileid}")
    int deleteFileById(Integer fileid);

    @Select("SELECT * FROM Files WHERE fileid = #{fileid}")
    Files getFileById(Integer fileid);

    @Select("SELECT * FROM Files WHERE filename = #{filename} AND userid = #{userid}")
    Files getFileByName(String filename, Integer userid);

}
