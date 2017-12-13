package com.llvision.security.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by llvision on 17/6/7.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class ErrorForbidden extends RuntimeException {
}
