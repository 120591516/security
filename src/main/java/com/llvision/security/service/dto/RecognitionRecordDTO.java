package com.llvision.security.service.dto;


import com.llvision.security.plugin.model.FaceResult;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the RecognitionRecord entity.
 */
public class RecognitionRecordDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer type;

    private Integer status;

    private String sourcePicBase64;

    private String sourcePicSuffix;

    private String targetId;

    private Double similarity;

    private Boolean attention;

    private Double latitude;

    private Double longitude;

    @Lob
    private byte[] info;
    private String infoContentType;

    private Boolean deleted;

    private List<RecognitionResultDTO> recognitionResults;

    private ZonedDateTime createdDate;

    private ZonedDateTime lastModifiedDate;

    private Long createdById;

    private String createdByLogin;

    private Long lastModifiedById;

    private String lastModifiedByLogin;

    private Long workRecordId;

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
    public String getSourcePicBase64() {
        return sourcePicBase64;
    }

    public void setSourcePicBase64(String sourcePicBase64) {
        this.sourcePicBase64 = sourcePicBase64;
    }
    public String getSourcePicSuffix() {
        return sourcePicSuffix;
    }

    public void setSourcePicSuffix(String sourcePicSuffix) {
        this.sourcePicSuffix = sourcePicSuffix;
    }
    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
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

    public List<RecognitionResultDTO> getRecognitionResults() {
        return recognitionResults;
    }

    public void setRecognitionResults(List<RecognitionResultDTO> recognitionResults) {
        this.recognitionResults = recognitionResults;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecognitionRecordDTO recognitionRecordDTO = (RecognitionRecordDTO) o;

        if ( ! Objects.equals(id, recognitionRecordDTO.id)) { return false; }

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
}
