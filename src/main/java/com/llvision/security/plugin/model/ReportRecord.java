package com.llvision.security.plugin.model;

import java.time.ZonedDateTime;

/**
 * Created by llvision on 17/5/5.
 */
public class ReportRecord {

    private String id;

    private String subject;

    private String reporter;

    private String content;

    private boolean resolved;

    private ZonedDateTime reportDate;

    private ZonedDateTime resolvedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public ZonedDateTime getReportDate() {
        return reportDate;
    }

    public void setReportDate(ZonedDateTime reportDate) {
        this.reportDate = reportDate;
    }

    public ZonedDateTime getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(ZonedDateTime resolvedDate) {
        this.resolvedDate = resolvedDate;
    }
}
