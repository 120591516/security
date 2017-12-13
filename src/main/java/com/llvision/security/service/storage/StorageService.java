package com.llvision.security.service.storage;

import org.springframework.core.io.Resource;

/**
 * Created by llvision on 17/5/3.
 */
public interface StorageService {

    void init();

    void store(byte[] data, String path) throws StorageException;

    Resource loadAsResource(String path);

}
