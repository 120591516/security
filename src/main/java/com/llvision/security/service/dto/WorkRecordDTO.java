package com.llvision.security.service.dto;


import com.llvision.security.plugin.model.CarPlate;
import com.llvision.security.plugin.model.FaceResult;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the WorkRecord entity.
 */
public class WorkRecordDTO implements Serializable {

    @NotNull
    private Integer type;

    @NotNull
    private String targetId;

    private String targetPicId;

    @NotNull
    private String sourcePicId;

    private Double similarity;

    private Double latitude;

    private Double longitude;

    private Boolean attention;

    private Integer danger1;

    private Integer danger2;

    private Integer danger3;

    private Integer danger4;

    private Integer danger5;

    private CarPlate carPlate;

    private FaceResult faceResult;

    private Integer status;

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

        WorkRecordDTO workRecordDTO = (WorkRecordDTO) o;

        if ( ! Objects.equals(targetId, workRecordDTO.targetId)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(targetId);
    }

    @Override
    public String toString() {
        return "WorkRecordDTO{" +
            ", type='" + type + "'" +
            ", status='" + status + "'" +
            ", targetId='" + targetId + "'" +
            ", targetPicId='" + targetPicId + "'" +
            ", sourcePicId='" + sourcePicId + "'" +
            ", similarity='" + similarity + "'" +
            ", latitude='" + latitude + "'" +
            ", longitude='" + longitude + "'" +
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

    public Boolean getAttention() {
        return attention;
    }

    public void setAttention(Boolean attention) {
        this.attention = attention;
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
}
