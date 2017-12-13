package com.llvision.security.service.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the LocalFace entity.
 */
public class LocalFaceDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 64)
    private String name;

    @Size(max = 18)
    private String personId;

    private Integer gender;

    private LocalDate birthday;

    @Size(max = 4000)
    private String address;

    private Boolean attention;

    private Integer danger;

    @NotNull
    @Size(max = 500)
    private String image;

    @Size(max = 100)
    private String model;

    @Size(max = 2000)
    private String hash;

    private Boolean deleted;

    private ZonedDateTime createdDate;

    private ZonedDateTime lastModifiedDate;

    private Long faceSetId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public Boolean getAttention() {
        return attention;
    }

    public void setAttention(Boolean attention) {
        this.attention = attention;
    }
    public Integer getDanger() {
        return danger;
    }

    public void setDanger(Integer danger) {
        this.danger = danger;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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

    public Long getFaceSetId() {
        return faceSetId;
    }

    public void setFaceSetId(Long localFaceSetId) {
        this.faceSetId = localFaceSetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocalFaceDTO localFaceDTO = (LocalFaceDTO) o;

        if ( ! Objects.equals(id, localFaceDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LocalFaceDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", personId='" + personId + "'" +
            ", gender='" + gender + "'" +
            ", birthday='" + birthday + "'" +
            ", address='" + address + "'" +
            ", attention='" + attention + "'" +
            ", danger='" + danger + "'" +
            ", image='" + image + "'" +
            ", model='" + model + "'" +
            ", hash='" + hash + "'" +
            ", createdDate='" + createdDate + "'" +
            ", lastModifiedDate='" + lastModifiedDate + "'" +
            '}';
    }
}
