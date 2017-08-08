package com.example.project.Serializable;

import java.io.Serializable;

public class Buddy implements Serializable {
    private String displayName;
    private String groupName;

    public Buddy(String displayName, String groupName) {
        this.displayName = displayName;
        this.groupName = groupName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
