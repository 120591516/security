package com.llvision.security.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.llvision.security.config.ApplicationProperties;
import com.llvision.security.service.dto.UserManageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserManageResource {

    private final Logger log = LoggerFactory.getLogger(UserManageResource.class);

    private final ApplicationProperties applicationProperties;

    public UserManageResource(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @GetMapping(value = "/selfService")
    @Timed
    public ResponseEntity<UserManageDTO> getSelfService() {
        log.debug("REST request selfService");
        UserManageDTO userManageDTO=new UserManageDTO();
        userManageDTO.setSelfService(applicationProperties.getUserManage().getSelfService());
        return ResponseEntity.ok().body(userManageDTO);
    }

    @GetMapping(value = "/recognitionFaceLimit")
    @Timed
    public ResponseEntity<ApplicationProperties.Recognition> getRecognitionFaceLimit() {
        log.debug("REST request RecognitionFaceLimit");
        return ResponseEntity.ok().body(applicationProperties.getRecognition());
    }
}

