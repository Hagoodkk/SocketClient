package com.example.project.Serializable;

import java.io.Serializable;

public class UserCredentials implements Serializable{
    private String username;
    private String passwordHash;
    private String passwordSalt;

    public UserCredentials(String username, String passwordSalt) {
        this.username = username;
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setUsername(String username) { this.username = username; }
    public String getUsername() { return this.username; }
    public void setPasswordSalt(String passwordSalt) { this.passwordSalt = passwordSalt; }
    public String getPasswordSalt() { return this.passwordSalt; }
}
