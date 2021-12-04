package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Insert("INSERT INTO Credentials (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userid}")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insertCredentials(Credentials credential);

    @Update("UPDATE Credentials SET url = #{url}, username = #{username}, password = #{password} WHERE credentialid = #{credentialid} AND userid = #{userid} ")
    int updateCredentialById(Integer credentialid, Integer userid);

    @Delete("DELETE FROM Credentials WHERE credentialid = #{credentialid} AND userid = #{userid}")
    int deleteCredentialById(Integer credentialid, Integer userid);

    @Select("SELECT * FROM Credentials WHERE userid = #{userid}")
    List<Credentials> getCredentials(Integer userid);
}
