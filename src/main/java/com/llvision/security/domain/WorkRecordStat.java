package com.llvision.security.domain;

import java.time.ZonedDateTime;

/**
 * Created by llvision on 17/5/10.
 */
public class WorkRecordStat {
    private ZonedDateTime date;
    private Long numWorkRecords;
    private Long numAttention;
    private Long numReport;

    public WorkRecordStat(Long numWorkRecords, Long numAttention, Long numReport) {
        this.numWorkRecords = numWorkRecords;
        this.numAttention = numAttention;
        this.numReport = numReport;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Long getNumWorkRecords() {
        return numWorkRecords;
    }

    public void setNumWorkRecords(Long numWorkRecords) {
        this.numWorkRecords = numWorkRecords;
    }

    public Long getNumAttention() {
        return numAttention;
    }

    public void setNumAttention(Long numAttention) {
        this.numAttention = numAttention;
    }

    public Long getNumReport() {
        return numReport;
    }

    public void setNumReport(Long numReport) {
        this.numReport = numReport;
    }
}
