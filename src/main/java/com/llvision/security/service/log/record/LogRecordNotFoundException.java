package com.llvision.security.service.log.record;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by cxj on 2017/5/15.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class LogRecordNotFoundException extends RuntimeException {
}
