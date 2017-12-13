package com.llvision.security.plugin.model;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by llvision on 17/5/5.
 */
public class Person {

    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;
    public static final int GENDER_UNKNOWN = 0;

    private String id;

    private String personId;

    private String name;

    // 1: male, 2: female, 0: unknown
    private int gender;

    private ZonedDateTime birthday;

    private String race;

    private String address;

    private boolean attention;

    private int danger;

    private String imageId;

    private List<ReportRecord> reportRecords;

    public Person() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAttention() {
        return attention;
    }

    public void setAttention(boolean attention) {
        this.attention = attention;
    }

    public int getDanger() {
        return danger;
    }

    public void setDanger(int danger) {
        this.danger = danger;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public List<ReportRecord> getReportRecords() {
        return reportRecords;
    }

    public void setReportRecords(List<ReportRecord> reportRecords) {
        this.reportRecords = reportRecords;
    }
}
