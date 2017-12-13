package com.llvision.security.service.dto;


import com.llvision.security.plugin.model.CarPlate;
import com.llvision.security.plugin.model.FaceResult;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the WorkRecord entity.
 */
public class WorkRecordOutputDTO implements Serializable {

    private Long id;

    private Integer type;

    private Integer status;

    private String targetId;

    private String targetPicId;

    private String sourcePicId;

    private Double similarity;

    private Boolean attention;

    private Boolean report;

    private Long reportDelay;

    private Double latitude;

    private Double longitude;

    private Integer danger1;

    private Integer danger2;

    private Integer danger3;

    private Integer danger4;

    private Integer danger5;

    private Boolean deleted;

    private ZonedDateTime lastRecognizedDate;

    private ZonedDateTime createdDate;

    private ZonedDateTime lastModifiedDate;

    private Long createdById;

    private String createdByLogin;

    private Long lastModifiedById;

    private String lastModifiedByLogin;

    private CarPlate carPlate;

    private FaceResult faceResult;

    private String createdByFirstName;

    private String createdByLastName;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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
    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
    public String getTargetPicId() {
        return targetPicId;
    }

    public void setTargetPicId(String targetPicId) {
        this.targetPicId = targetPicId;
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
    public Boolean getReport() {
        return report;
    }

    public void setReport(Boolean report) {
        this.report = report;
    }
    public Long getReportDelay() {
        return reportDelay;
    }

    public void setReportDelay(Long reportDelay) {
        this.reportDelay = reportDelay;
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
    public Integer getDanger1() {
        return danger1;
    }

    public void setDanger1(Integer danger1) {
        this.danger1 = danger1;
    }
    public Integer getDanger2() {
        return danger2;
    }

    public void setDanger2(Integer danger2) {
        this.danger2 = danger2;
    }
    public Integer getDanger3() {
        return danger3;
    }

    public void setDanger3(Integer danger3) {
        this.danger3 = danger3;
    }
    public Integer getDanger4() {
        return danger4;
    }

    public void setDanger4(Integer danger4) {
        this.danger4 = danger4;
    }
    public Integer getDanger5() {
        return danger5;
    }

    public void setDanger5(Integer danger5) {
        this.danger5 = danger5;
    }
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    public ZonedDateTime getLastRecognizedDate() {
        return lastRecognizedDate;
    }

    public void setLastRecognizedDate(ZonedDateTime lastRecognizedDate) {
        this.lastRecognizedDate = lastRecognizedDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkRecordOutputDTO workRecordOutputDTO = (WorkRecordOutputDTO) o;

        if ( ! Objects.equals(id, workRecordOutputDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkRecordDTO{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", status='" + status + "'" +
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
            '}';
    }

    public CarPlate getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(CarPlate carPlate) {
        this.carPlate = carPlate;
    }

    public FaceResult getFaceResult() {
        return faceResult;
    }

    public void setFaceResult(FaceResult faceResult) {
        this.faceResult = faceResult;
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
}
