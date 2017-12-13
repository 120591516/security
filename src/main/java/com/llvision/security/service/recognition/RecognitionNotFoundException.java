package com.llvision.security.service.recognition;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by llvision on 17/5/4.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class RecognitionNotFoundException extends RuntimeException {
}
