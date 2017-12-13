package com.llvision.security.service.storage;

/**
 * Created by llvision on 17/5/3.
 */
public class StorageException extends Exception {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Exception e) {
        super(message, e);
    }
}
