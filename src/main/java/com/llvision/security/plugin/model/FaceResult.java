package com.llvision.security.plugin.model;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by llvision on 17/5/5.
 */
public class FaceResult {

    private String id;

    private String imageId;

    private double similarity;

    private String personInfoId;

    private Person person;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public String getPersonInfoId() {
        return personInfoId;
    }

    public void setPersonInfoId(String personInfoId) {
        this.personInfoId = personInfoId;
    }

    @JsonIgnore
    public Person getPerson() {
        return person;
    }

    @JsonIgnore
    public void setPerson(Person person) {
        this.person = person;
    }
}
