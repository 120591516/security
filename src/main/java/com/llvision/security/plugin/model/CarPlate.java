package com.llvision.security.plugin.model;

import java.util.List;

/**
 * Created by llvision on 17/5/5.
 */
public class CarPlate {

    private String id;

    private String brand;

    private String model;

    private String carColor;

    private String carType;

    private String owner;

    private String address;

    private boolean attention;

    private Person ownerPerson;

    private List<ReportRecord> reportRecordList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Person getOwnerPerson() {
        return ownerPerson;
    }

    public void setOwnerPerson(Person ownerPerson) {
        this.ownerPerson = ownerPerson;
    }

    public boolean isAttention() {
        return attention;
    }

    public void setAttention(boolean attention) {
        this.attention = attention;
    }

    public List<ReportRecord> getReportRecordList() {
        return reportRecordList;
    }

    public void setReportRecordList(List<ReportRecord> reportRecordList) {
        this.reportRecordList = reportRecordList;
    }
}
