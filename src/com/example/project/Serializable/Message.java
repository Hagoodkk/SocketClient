package com.example.project.Serializable;

import java.io.Serializable;

public class Message implements Serializable{
    private String sender, recipient, message, salt, passwordSaltedHash;
    private boolean error, nullMessage, buddyListUpdate, loginRequest, saltRequest;
    private BuddyList buddyList;

    public String getPasswordSaltedHash() {
        return passwordSaltedHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isSaltRequest() {
        return saltRequest;
    }

    public void setSaltRequest(boolean saltRequest) {
        this.saltRequest = saltRequest;
    }

    public void setPasswordSaltedHash(String passwordSaltedHash) {
        this.passwordSaltedHash = passwordSaltedHash;
    }

    public boolean isLoginRequest() {
        return loginRequest;
    }

    public void setLoginRequest(boolean loginRequest) {
        this.loginRequest = loginRequest;
    }

    public Message() {}

    public Message(boolean nullMessage) {
        if (nullMessage) this.nullMessage = true;
    }

    public Message(String sender, String recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.error = false;
        this.nullMessage = false;
        this.buddyListUpdate = false;
    }

    public boolean isBuddyListUpdate() {
        return buddyListUpdate;
    }

    public void setBuddyListUpdate(boolean buddyListUpdate) {
        this.buddyListUpdate = buddyListUpdate;
    }

    public BuddyList getBuddyList() {
        return buddyList;
    }

    public void setBuddyList(BuddyList buddyList) {
        this.buddyList = buddyList;
    }

    public String getMessage() {
        return message;
    }

    public boolean isNullMessage() {
        return nullMessage;
    }

    public void setNullMessage(boolean nullMessage) {
        this.nullMessage = nullMessage;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
