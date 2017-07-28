package com.example.project.SessionManager;

import com.example.project.Serializable.BuddyList;
import com.example.project.Serializable.Message;

import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class SessionManager {
    private static SessionManager sessionManager;

    private String username;
    private Socket clientSocket;
    private BuddyList buddyList;
    private String messageRecipient;
    private Queue<Message> outgoingQueue;

    public String getMessageRecipient() {
        return messageRecipient;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void setMessageRecipient(String messageRecipient) {
        this.messageRecipient = messageRecipient;
    }

    public Queue<Message> getOutgoingQueue() {
        return outgoingQueue;
    }

    public void addOutgoingMessage(Message message) {
        outgoingQueue.add(message);
    }

    private SessionManager() {}

    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }
    public void setBuddyList(BuddyList buddyList) { this.buddyList = buddyList; }
    public BuddyList getBuddyList() { return this.buddyList; }

    public static SessionManager getInstance() {
        if (sessionManager == null) {
            sessionManager = new SessionManager();
            sessionManager.outgoingQueue = new LinkedList<>();
        }
        return sessionManager;
    }
}
