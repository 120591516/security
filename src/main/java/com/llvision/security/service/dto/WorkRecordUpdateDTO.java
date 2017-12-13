package com.llvision.security.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for updating the WorkRecord entity.
 */
public class WorkRecordUpdateDTO implements Serializable {

    private Long id;

    private Boolean report;

    private Long reportDelay;

    private Integer danger1;

    private Integer danger2;

    private Integer danger3;

    private Integer danger4;

    private Integer danger5;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkRecordUpdateDTO workRecordDTO = (WorkRecordUpdateDTO) o;

        if ( ! Objects.equals(id, workRecordDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkRecordUpdateDTO{" +
            "id=" + id +
            ", report=" + report +
            ", reportDelay=" + reportDelay +
            ", danger1=" + danger1 +
            ", danger2=" + danger2 +
            ", danger3=" + danger3 +
            ", danger4=" + danger4 +
            ", danger5=" + danger5 +
            '}';
    }
}
