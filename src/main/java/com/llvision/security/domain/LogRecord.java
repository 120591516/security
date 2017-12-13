package com.llvision.security.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A LogRecord.
 */
@Entity
@Table(name = "log_record")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LogRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private Integer type;

    @Lob
    @Column(name = "text")
    private byte[] text;

    @Column(name = "text_content_type")
    private String textContentType;

    @Column(name = "pic_id")
    private String picId;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @ManyToOne
    private WorkRecord workRecord;

    @ManyToOne
    private User createdBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public LogRecord type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public byte[] getText() {
        return text;
    }

    public LogRecord text(byte[] text) {
        this.text = text;
        return this;
    }

    public void setText(byte[] text) {
        this.text = text;
    }

    public String getTextContentType() {
        return textContentType;
    }

    public LogRecord textContentType(String textContentType) {
        this.textContentType = textContentType;
        return this;
    }

    public void setTextContentType(String textContentType) {
        this.textContentType = textContentType;
    }

    public String getPicId() {
        return picId;
    }

    public LogRecord picId(String picId) {
        this.picId = picId;
        return this;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public LogRecord deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public LogRecord createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public WorkRecord getWorkRecord() {
        return workRecord;
    }

    public LogRecord workRecord(WorkRecord workRecord) {
        this.workRecord = workRecord;
        return this;
    }

    public void setWorkRecord(WorkRecord workRecord) {
        this.workRecord = workRecord;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public LogRecord createdBy(User user) {
        this.createdBy = user;
        return this;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LogRecord logRecord = (LogRecord) o;
        if (logRecord.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, logRecord.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LogRecord{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", text='" + text + "'" +
            ", textContentType='" + textContentType + "'" +
            ", picId='" + picId + "'" +
            ", deleted='" + deleted + "'" +
            ", createdDate='" + createdDate + "'" +
            '}';
    }
}
