package com.example.project.Serializable;

import java.io.Serializable;

public class UserCredentials implements Serializable{
    private String username;
    private String passwordSaltedHash;
    private String passwordSalt;

    private boolean requestAccepted;

    public UserCredentials(String username, String passwordSaltedHash, String passwordSalt) {
        this.username = username;
        this.passwordSaltedHash = passwordSaltedHash;
        this.passwordSalt = passwordSalt;
    }

    public boolean isRequestAccepted() {
        return requestAccepted;
    }

    public void setRequestAccepted(boolean requestAccepted) {
        this.requestAccepted = requestAccepted;
    }

    public String getPasswordSaltedHash() {
        return passwordSaltedHash;
    }

    public void setPasswordSaltedHash(String passwordHash) {
        this.passwordSaltedHash = passwordHash;
    }

    public void setUsername(String username) { this.username = username; }
    public String getUsername() { return this.username; }
    public void setPasswordSalt(String passwordSalt) { this.passwordSalt = passwordSalt; }
    public String getPasswordSalt() { return this.passwordSalt; }
}
