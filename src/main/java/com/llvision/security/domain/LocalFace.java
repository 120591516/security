package com.llvision.security.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A LocalFace.
 */
@Entity
@Table(name = "local_face")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LocalFace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 64)
    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Size(max = 18)
    @Column(name = "person_id", length = 18)
    private String personId;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Size(max = 4000)
    @Column(name = "address", length = 4000)
    private String address;

    @Column(name = "attention")
    private Boolean attention;

    @Column(name = "danger")
    private Integer danger;

    @NotNull
    @Size(max = 500)
    @Column(name = "image", length = 500, nullable = false)
    private String image;

    @Size(max = 100)
    @Column(name = "model", length = 100)
    private String model;

    @Size(max = 2000)
    @Column(name = "jhi_hash", length = 2000)
    private String hash;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "last_modified_date")
    private ZonedDateTime lastModifiedDate;

    @ManyToOne
    private LocalFaceSet faceSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public LocalFace name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonId() {
        return personId;
    }

    public LocalFace personId(String personId) {
        this.personId = personId;
        return this;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Integer getGender() {
        return gender;
    }

    public LocalFace gender(Integer gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public LocalFace birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public LocalFace address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean isAttention() {
        return attention;
    }

    public LocalFace attention(Boolean attention) {
        this.attention = attention;
        return this;
    }

    public void setAttention(Boolean attention) {
        this.attention = attention;
    }

    public Integer getDanger() {
        return danger;
    }

    public LocalFace danger(Integer danger) {
        this.danger = danger;
        return this;
    }

    public void setDanger(Integer danger) {
        this.danger = danger;
    }

    public String getImage() {
        return image;
    }

    public LocalFace image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getModel() {
        return model;
    }

    public LocalFace model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getHash() {
        return hash;
    }

    public LocalFace hash(String hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public LocalFace deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalFace createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public LocalFace lastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LocalFaceSet getFaceSet() {
        return faceSet;
    }

    public LocalFace faceSet(LocalFaceSet localFaceSet) {
        this.faceSet = localFaceSet;
        return this;
    }

    public void setFaceSet(LocalFaceSet localFaceSet) {
        this.faceSet = localFaceSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocalFace localFace = (LocalFace) o;
        if (localFace.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, localFace.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LocalFace{" +
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
