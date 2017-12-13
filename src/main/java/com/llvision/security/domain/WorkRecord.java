package com.llvision.security.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A WorkRecord.
 */
@Entity
@Table(name = "work_record")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WorkRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private Integer type;

    @Column(name = "target_id", nullable = false)
    private String targetId;

    @Column(name = "target_pic_id")
    private String targetPicId;

    @NotNull
    @Column(name = "source_pic_id", nullable = false)
    private String sourcePicId;

    @Column(name = "similarity")
    private Double similarity;

    @Column(name = "attention")
    private Boolean attention;

    @Column(name = "report")
    private Boolean report;

    @Column(name = "report_delay")
    private Long reportDelay;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "danger_1")
    private Integer danger1;

    @Column(name = "danger_2")
    private Integer danger2;

    @Column(name = "danger_3")
    private Integer danger3;

    @Column(name = "danger_4")
    private Integer danger4;

    @Column(name = "danger_5")
    private Integer danger5;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "last_recognized_date")
    private ZonedDateTime lastRecognizedDate;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "last_modified_date")
    private ZonedDateTime lastModifiedDate;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private User lastModifiedBy;

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

    public WorkRecord type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTargetId() {
        return targetId;
    }

    public WorkRecord targetId(String targetId) {
        this.targetId = targetId;
        return this;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetPicId() {
        return targetPicId;
    }

    public WorkRecord targetPicId(String targetPicId) {
        this.targetPicId = targetPicId;
        return this;
    }

    public void setTargetPicId(String targetPicId) {
        this.targetPicId = targetPicId;
    }

    public String getSourcePicId() {
        return sourcePicId;
    }

    public WorkRecord sourcePicId(String sourcePicId) {
        this.sourcePicId = sourcePicId;
        return this;
    }

    public void setSourcePicId(String sourcePicId) {
        this.sourcePicId = sourcePicId;
    }

    public Double getSimilarity() {
        return similarity;
    }

    public WorkRecord similarity(Double similarity) {
        this.similarity = similarity;
        return this;
    }

    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }

    public Boolean isAttention() {
        return attention;
    }

    public WorkRecord attention(Boolean attention) {
        this.attention = attention;
        return this;
    }

    public void setAttention(Boolean attention) {
        this.attention = attention;
    }

    public Boolean isReport() {
        return report;
    }

    public WorkRecord report(Boolean report) {
        this.report = report;
        return this;
    }

    public void setReport(Boolean report) {
        this.report = report;
    }

    public Long getReportDelay() {
        return reportDelay;
    }

    public WorkRecord reportDelay(Long reportDelay) {
        this.reportDelay = reportDelay;
        return this;
    }

    public void setReportDelay(Long reportDelay) {
        this.reportDelay = reportDelay;
    }

    public Double getLatitude() {
        return latitude;
    }

    public WorkRecord latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public WorkRecord longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getDanger1() {
        return danger1;
    }

    public WorkRecord danger1(Integer danger1) {
        this.danger1 = danger1;
        return this;
    }

    public void setDanger1(Integer danger1) {
        this.danger1 = danger1;
    }

    public Integer getDanger2() {
        return danger2;
    }

    public WorkRecord danger2(Integer danger2) {
        this.danger2 = danger2;
        return this;
    }

    public void setDanger2(Integer danger2) {
        this.danger2 = danger2;
    }

    public Integer getDanger3() {
        return danger3;
    }

    public WorkRecord danger3(Integer danger3) {
        this.danger3 = danger3;
        return this;
    }

    public void setDanger3(Integer danger3) {
        this.danger3 = danger3;
    }

    public Integer getDanger4() {
        return danger4;
    }

    public WorkRecord danger4(Integer danger4) {
        this.danger4 = danger4;
        return this;
    }

    public void setDanger4(Integer danger4) {
        this.danger4 = danger4;
    }

    public Integer getDanger5() {
        return danger5;
    }

    public WorkRecord danger5(Integer danger5) {
        this.danger5 = danger5;
        return this;
    }

    public void setDanger5(Integer danger5) {
        this.danger5 = danger5;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public WorkRecord deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public ZonedDateTime getLastRecognizedDate() {
        return lastRecognizedDate;
    }

    public WorkRecord lastRecognizedDate(ZonedDateTime lastRecognizedDate) {
        this.lastRecognizedDate = lastRecognizedDate;
        return this;
    }

    public void setLastRecognizedDate(ZonedDateTime lastRecognizedDate) {
        this.lastRecognizedDate = lastRecognizedDate;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public WorkRecord createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public WorkRecord lastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public WorkRecord createdBy(User user) {
        this.createdBy = user;
        return this;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public WorkRecord lastModifiedBy(User user) {
        this.lastModifiedBy = user;
        return this;
    }

    public void setLastModifiedBy(User user) {
        this.lastModifiedBy = user;
    }

    public WorkRecord status(Integer status) {
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
        WorkRecord workRecord = (WorkRecord) o;
        if (workRecord.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, workRecord.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkRecord{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", targetId='" + targetId + "'" +
            ", targetPicId='" + targetPicId + "'" +
            ", sourcePicId='" + sourcePicId + "'" +
            ", similarity='" + similarity + "'" +
            ", attention='" + attention + "'" +
            ", report='" + report + "'" +
            ", reportDelay='" + reportDelay + "'" +
            ", latitude='" + latitude + "'" +
            ", longitude='" + longitude + "'" +
            ", danger1='" + danger1 + "'" +
            ", danger2='" + danger2 + "'" +
            ", danger3='" + danger3 + "'" +
            ", danger4='" + danger4 + "'" +
            ", danger5='" + danger5 + "'" +
            ", deleted='" + deleted + "'" +
            ", lastRecognizedDate='" + lastRecognizedDate + "'" +
            ", createdDate='" + createdDate + "'" +
            ", lastModifiedDate='" + lastModifiedDate + "'" +
            ", status='" + status + "'" +
            '}';
    }

}
