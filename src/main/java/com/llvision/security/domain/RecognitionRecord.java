package com.llvision.security.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A RecognitionRecord.
 */
@Entity
@Table(name = "recognition_record")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RecognitionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private Integer type;

    @NotNull
    @Column(name = "source_pic_id", nullable = false)
    private String sourcePicId;

    @Column(name = "similarity")
    private Double similarity;

    @Column(name = "attention")
    private Boolean attention;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Lob
    @Column(name = "info")
    private byte[] info;

    @Column(name = "info_content_type")
    private String infoContentType;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "last_modified_date")
    private ZonedDateTime lastModifiedDate;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private User lastModifiedBy;

    @ManyToOne
    private WorkRecord workRecord;

    @Column(name = "status")
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public RecognitionRecord type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSourcePicId() {
        return sourcePicId;
    }

    public RecognitionRecord sourcePicId(String sourcePicId) {
        this.sourcePicId = sourcePicId;
        return this;
    }

    public void setSourcePicId(String sourcePicId) {
        this.sourcePicId = sourcePicId;
    }

    public Double getSimilarity() {
        return similarity;
    }

    public RecognitionRecord similarity(Double similarity) {
        this.similarity = similarity;
        return this;
    }

    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }

    public Boolean isAttention() {
        return attention;
    }

    public RecognitionRecord attention(Boolean attention) {
        this.attention = attention;
        return this;
    }

    public void setAttention(Boolean attention) {
        this.attention = attention;
    }

    public Double getLatitude() {
        return latitude;
    }

    public RecognitionRecord latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public RecognitionRecord longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public byte[] getInfo() {
        return info;
    }

    public RecognitionRecord info(byte[] info) {
        this.info = info;
        return this;
    }

    public void setInfo(byte[] info) {
        this.info = info;
    }

    public String getInfoContentType() {
        return infoContentType;
    }

    public RecognitionRecord infoContentType(String infoContentType) {
        this.infoContentType = infoContentType;
        return this;
    }

    public void setInfoContentType(String infoContentType) {
        this.infoContentType = infoContentType;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public RecognitionRecord deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public RecognitionRecord createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public RecognitionRecord lastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public RecognitionRecord createdBy(User user) {
        this.createdBy = user;
        return this;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public RecognitionRecord lastModifiedBy(User user) {
        this.lastModifiedBy = user;
        return this;
    }

    public void setLastModifiedBy(User user) {
        this.lastModifiedBy = user;
    }

    public WorkRecord getWorkRecord() {
        return workRecord;
    }

    public RecognitionRecord workRecord(WorkRecord workRecord) {
        this.workRecord = workRecord;
        return this;
    }

    public void setWorkRecord(WorkRecord workRecord) {
        this.workRecord = workRecord;
    }

    public RecognitionRecord status(Integer status) {
        this.status = status;
        return this;
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
        RecognitionRecord recognitionRecord = (RecognitionRecord) o;
        if (recognitionRecord.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, recognitionRecord.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RecognitionRecord{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", sourcePicId='" + sourcePicId + "'" +
            ", similarity='" + similarity + "'" +
            ", attention='" + attention + "'" +
            ", latitude='" + latitude + "'" +
            ", longitude='" + longitude + "'" +
            ", info='" + info + "'" +
            ", infoContentType='" + infoContentType + "'" +
            ", deleted='" + deleted + "'" +
            ", createdDate='" + createdDate + "'" +
            ", lastModifiedDate='" + lastModifiedDate + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
