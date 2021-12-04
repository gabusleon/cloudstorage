package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FilesService {

    private FilesMapper filesMapper;

    public FilesService(FilesMapper filesMapper) {
        this.filesMapper = filesMapper;
    }

    public int uploadUserFile(MultipartFile file, Files dataFile) {
        return filesMapper.insertFile(dataFile);
    }

    public boolean isUserFilenameAvailable(String filename, Integer userid){
        return filesMapper.getFileByName(filename, userid) == null;
    }

    public Files downloadUserFile(Integer fileid) {
        return filesMapper.getFileById(fileid);
    }

    public List<Files> getUserFiles(Integer userid){
        return filesMapper.getFiles(userid);
    }

    public int deleteUserFile(Integer fileid){
        return filesMapper.deleteFileById(fileid);
    }
}
