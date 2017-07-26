package com.example.project.SessionManager;

import com.example.project.Serializable.BuddyList;

public class SessionManager {
    private static SessionManager sessionManager;

    private String username;
    private BuddyList buddyList;

    private SessionManager() {}

    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }
    public void setBuddyList(BuddyList buddyList) { this.buddyList = buddyList; }
    public BuddyList getBuddyList() { return this.buddyList; }

    public static SessionManager getInstance() {
        if (sessionManager == null) {
            sessionManager = new SessionManager();
        }
        return sessionManager;
    }
}
