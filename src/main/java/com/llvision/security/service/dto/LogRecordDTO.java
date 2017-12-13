package com.llvision.security.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the LogRecord entity.
 */
public class LogRecordDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer type;

    @Lob
    private byte[] text;
    private String textContentType;

    private String picId;

    private Boolean deleted;

    private ZonedDateTime createdDate;

    private Long workRecordId;

    private Long createdById;

    private String createdByLogin;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public byte[] getText() {
        return text;
    }

    public void setText(byte[] text) {
        this.text = text;
    }

    public String getTextContentType() {
        return textContentType;
    }

    public void setTextContentType(String textContentType) {
        this.textContentType = textContentType;
    }
    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long userId) {
        this.createdById = userId;
    }

    public String getCreatedByLogin() {
        return createdByLogin;
    }

    public void setCreatedByLogin(String userLogin) {
        this.createdByLogin = userLogin;
    }

    public Long getWorkRecordId() {
        return workRecordId;
    }

    public void setWorkRecordId(Long workRecordId) {
        this.workRecordId = workRecordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LogRecordDTO logRecordDTO = (LogRecordDTO) o;

        if ( ! Objects.equals(id, logRecordDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LogRecordDTO{" +
            "id=" + id +
            ", type=" + type +
            ", text=" + Arrays.toString(text) +
            ", textContentType='" + textContentType + '\'' +
            ", picId='" + picId + '\'' +
            ", deleted=" + deleted +
            ", createdDate=" + createdDate +
            ", workRecordId=" + workRecordId +
            ", createdById=" + createdById +
            ", createdByLogin='" + createdByLogin + '\'' +
            '}';
    }
}
