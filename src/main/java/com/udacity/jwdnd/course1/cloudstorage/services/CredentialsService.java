package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {

    private CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public int createCredential(Credentials credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        return this.credentialsMapper.insertCredentials(new Credentials(null, credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, credential.getUserid()));
    }

    public int updateCredential(Credentials credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        return this.credentialsMapper.updateCredential(new Credentials(credential.getCredentialid(), credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, credential.getUserid()));
    }

    public List<Credentials> readCredentials(Integer userid){
        return this.credentialsMapper.getCredentials(userid);
    }

    public int deleteCredential(Integer credentialid){
        return  this.credentialsMapper.deleteCredentialById(credentialid);
    }
}
