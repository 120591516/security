package com.llvision.security.service.dto;


import com.llvision.security.plugin.model.FaceResult;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the RecognitionRecord entity.
 */
public class RecognitionResultDTO implements Serializable {

    @NotNull
    private String targetId;

    @NotNull
    private Double similarity;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecognitionResultDTO recognitionRecordDTO = (RecognitionResultDTO) o;

        if ( ! Objects.equals(targetId, recognitionRecordDTO.targetId)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(targetId);
    }

    @Override
    public String toString() {
        return "RecognitionRecordDTO{" +
            "targetId=" + targetId +
            ", similarity='" + similarity + "'" +
            '}';
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
}
