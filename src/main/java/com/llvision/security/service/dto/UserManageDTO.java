package com.llvision.security.service.dto;

import java.io.Serializable;

public class UserManageDTO implements Serializable{

    private Boolean isSelfService;

    public Boolean getSelfService() {
        return isSelfService;
    }

    public void setSelfService(Boolean selfService) {
        isSelfService = selfService;
    }

    @Override
    public String toString() {
        return "UserManageDTO{" +
            "isSelfService=" + isSelfService +
            '}';
    }
}

