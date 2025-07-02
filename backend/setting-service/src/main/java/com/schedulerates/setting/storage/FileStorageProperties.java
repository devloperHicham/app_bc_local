package com.schedulerates.setting.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class FileStorageProperties {
    private String location = "storage-images"; // Default value

    // Proper getter/setter pair
    public String getLocation() { 
        return location; 
    }
    public void setLocation(String location) { 
        this.location = location; 
    }
}