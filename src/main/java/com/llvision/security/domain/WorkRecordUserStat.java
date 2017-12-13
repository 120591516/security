package com.llvision.security.domain;


/**
 * Created by llvision on 17/5/10.
 */
public class WorkRecordUserStat {

    private User user;

    private Long numWorkRecords;

    private Long numAttention;

    private Long numReport;

    public WorkRecordUserStat(User user, Long numWorkRecords, Long numAttention, Long numReport) {
        this.user = user;
        this.numWorkRecords = numWorkRecords;
        this.numAttention = numAttention;
        this.numReport = numReport;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
