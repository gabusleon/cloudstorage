package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Select("SELECT * FROM Notes WHERE userid = #{userid}")
    List<Notes> getNotes(Integer userid);

    @Select("SELECT * FROM Notes WHERE noteid = #{noteid}")
    Notes getNote(Integer noteid);

    @Insert("INSERT INTO Notes (notetitle, notedescription, userid) VALUES (#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insertNote(Notes note);

    @Update("UPDATE Notes SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid}")
    int updateNoteById(Notes note);

    @Delete("DELETE FROM Notes Where noteid = #{noteid}")
    int deleteNoteById(Integer noteid);

}
