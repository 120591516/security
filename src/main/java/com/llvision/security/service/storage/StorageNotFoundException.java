package com.llvision.security.service.storage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by llvision on 17/5/3.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class StorageNotFoundException extends RuntimeException {

    public StorageNotFoundException(String message) {
        super(message);
    }

    public StorageNotFoundException(String message, Exception e) {
        super(message, e);
    }
}
