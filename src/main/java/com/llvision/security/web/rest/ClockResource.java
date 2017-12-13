package com.llvision.security.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * Created by abel-sun on 2017/6/14.
 */
@RestController
@RequestMapping("/api/clock")
public class ClockResource {
    private final Logger log = LoggerFactory.getLogger(ClockResource.class);

    /**
     * GET  : get the "path" of file in storage in raw data.
     *
     * @return the syncrecource with status 200 (OK) and with body the string , or with status 404 (Not Found)
     */
    @GetMapping(value = "/sync")
    public LocalDateTime SyncTime() {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
//        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.now(), ZoneId.of(ZoneId.SHORT_IDS.get("CTT")));
        return localDateTime;
    }
}
