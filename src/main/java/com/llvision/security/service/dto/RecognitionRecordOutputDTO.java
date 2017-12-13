package com.llvision.security.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the RecognitionRecord entity.
 */
public class RecognitionRecordOutputDTO implements Serializable {

    private Long id;

    private Integer type;

    private Integer status;

    private String sourcePicId;

    private Double similarity;

    private Boolean attention;

    private Double latitude;

    private Double longitude;

    @Lob
    private byte[] info;
    private String infoContentType;

    private Boolean deleted;

    private ZonedDateTime createdDate;

    private ZonedDateTime lastModifiedDate;

    private Long createdById;

    private String createdByLogin;

    private Long lastModifiedById;

    private String lastModifiedByLogin;

    private Long workRecordId;

    private String createdByFirstName;

    private String createdByLastName;

    private WorkRecordOutputDTO workRecord;

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
    public String getSourcePicId() {
        return sourcePicId;
    }

    public void setSourcePicId(String sourcePicId) {
        this.sourcePicId = sourcePicId;
    }
    public Double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }
    public Boolean getAttention() {
        return attention;
    }

    public void setAttention(Boolean attention) {
        this.attention = attention;
    }
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public byte[] getInfo() {
        return info;
    }

    public void setInfo(byte[] info) {
        this.info = info;
    }

    public String getInfoContentType() {
        return infoContentType;
    }

    public void setInfoContentType(String infoContentType) {
        this.infoContentType = infoContentType;
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
    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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

    public Long getLastModifiedById() {
        return lastModifiedById;
    }

    public void setLastModifiedById(Long userId) {
        this.lastModifiedById = userId;
    }

    public String getLastModifiedByLogin() {
        return lastModifiedByLogin;
    }

    public void setLastModifiedByLogin(String userLogin) {
        this.lastModifiedByLogin = userLogin;
    }

    public Long getWorkRecordId() {
        return workRecordId;
    }

    public void setWorkRecordId(Long workRecordId) {
        this.workRecordId = workRecordId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecognitionRecordOutputDTO recognitionRecordOutputDTO = (RecognitionRecordOutputDTO) o;

        if ( ! Objects.equals(id, recognitionRecordOutputDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RecognitionRecordDTO{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", status='" + status + "'" +
            ", sourcePicId='" + sourcePicId + "'" +
            ", similarity='" + similarity + "'" +
            ", attention='" + attention + "'" +
            ", latitude='" + latitude + "'" +
            ", longitude='" + longitude + "'" +
            ", info='" + info + "'" +
            ", deleted='" + deleted + "'" +
            ", createdDate='" + createdDate + "'" +
            ", lastModifiedDate='" + lastModifiedDate + "'" +
            '}';
    }

    public String getCreatedByFirstName() {
        return createdByFirstName;
    }

    public void setCreatedByFirstName(String createdByFirstName) {
        this.createdByFirstName = createdByFirstName;
    }

    public String getCreatedByLastName() {
        return createdByLastName;
    }

    public void setCreatedByLastName(String createdByLastName) {
        this.createdByLastName = createdByLastName;
    }

    public WorkRecordOutputDTO getWorkRecord() {
        return workRecord;
    }

    public void setWorkRecord(WorkRecordOutputDTO workRecord) {
        this.workRecord = workRecord;
    }
}
