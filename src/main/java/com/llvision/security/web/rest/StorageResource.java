package com.llvision.security.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.llvision.security.plugin.model.PersonImage;
import com.llvision.security.plugin.service.PersonService;
import com.llvision.security.plugin.service.PluginServiceFactory;
import com.llvision.security.plugin.service.SinglePersonService;
import com.llvision.security.service.storage.StorageException;
import com.llvision.security.service.storage.StorageService;
import com.llvision.security.service.util.StorageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * REST controller for managing WorkRecord.
 */
@RestController
@RequestMapping("/api")
public class StorageResource {

    private final Logger log = LoggerFactory.getLogger(StorageResource.class);

    private static final String ENTITY_NAME = "storage";

    private final StorageService storageService;

    public StorageResource(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * GET  /storage?path=:path : get the "path" of file in storage in raw data.
     *
     * @param path the file path to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the file, or with status 404 (Not Found)
     */
    @GetMapping(value = "/storage")
    @Timed
    public ResponseEntity<Resource> serveFile(@RequestParam String path) {

        log.debug("REST request to serve file : {}", path);
        if (StringUtils.isEmpty(path)) {
            log.error("REST request to serve file Error path not null : {}", path);
        }
        Resource file = storageService.loadAsResource(path);
        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(StorageUtil.getContentType(path)))
            .body(file);
    }
}
