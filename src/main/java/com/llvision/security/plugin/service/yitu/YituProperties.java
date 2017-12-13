package com.llvision.security.plugin.service.yitu;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by llvision on 17/5/20.
 */
@Configuration
@ConfigurationProperties(prefix = "yitu", ignoreUnknownFields = false)
public class YituProperties {

    private String baseUrl = "http://118.190.45.145:11180";

    private String resourceManagerPath = "/resource_manager";

    private String facePath = "/face";

    private String storagePath = "/storage";

    private String username = "";

    private String password = "";

    private List<Integer> availableRepoIds = new ArrayList<>();

    private List<Integer> blackListRepoIds = new ArrayList<>();

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getResourceManagerPath() {
        return resourceManagerPath;
    }

    public void setResourceManagerPath(String resourceManagerPath) {
        this.resourceManagerPath = resourceManagerPath;
    }

    public String getFacePath() {
        return facePath;
    }

    public void setFacePath(String facePath) {
        this.facePath = facePath;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
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

    public List<Integer> getAvailableRepoIds() {
        return availableRepoIds;
    }

    public void setAvailableRepoIds(List<Integer> availableRepoIds) {
        this.availableRepoIds = availableRepoIds;
    }

    public List<Integer> getBlackListRepoIds() {
        return blackListRepoIds;
    }

    public void setBlackListRepoIds(List<Integer> blackListRepoIds) {
        this.blackListRepoIds = blackListRepoIds;
    }
}
