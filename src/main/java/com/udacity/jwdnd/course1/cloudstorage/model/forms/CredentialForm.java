package com.udacity.jwdnd.course1.cloudstorage.model.forms;

public class CredentialForm {
    private Integer credentialid;
    private String url;
    private String username;
    private String password;

    public CredentialForm() {
    }

    public CredentialForm(Integer credentialid, String url, String username, String password) {
        this.credentialid = credentialid;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Integer getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(Integer credentialid) {
        this.credentialid = credentialid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
