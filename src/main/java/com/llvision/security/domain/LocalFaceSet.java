package com.llvision.security.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A LocalFaceSet.
 */
@Entity
@Table(name = "local_face_set")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LocalFaceSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "last_modified_date")
    private ZonedDateTime lastModifiedDate;

    @Column(name = "activated", nullable = false)
    private Boolean activated;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private User lastModifiedBy;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "local_face_set_user",
               joinColumns = @JoinColumn(name="local_face_sets_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="users_id", referencedColumnName="id"))
    private Set<User> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public LocalFaceSet name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public LocalFaceSet deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalFaceSet createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public LocalFaceSet lastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public Boolean getActivated() {
        return activated;
    }

    public LocalFaceSet activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public LocalFaceSet createdBy(User user) {
        this.createdBy = user;
        return this;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public LocalFaceSet lastModifiedBy(User user) {
        this.lastModifiedBy = user;
        return this;
    }

    public void setLastModifiedBy(User user) {
        this.lastModifiedBy = user;
    }

    public Set<User> getUsers() {
        return users;
    }

    public LocalFaceSet users(Set<User> users) {
        this.users = users;
        return this;
    }

    public LocalFaceSet addUser(User user) {
        this.users.add(user);
        user.getLocalFaceSets().add(this);
        return this;
    }

    public LocalFaceSet removeUser(User user) {
        this.users.remove(user);
        user.getLocalFaceSets().remove(this);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocalFaceSet localFaceSet = (LocalFaceSet) o;
        if (localFaceSet.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, localFaceSet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LocalFaceSet{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", deleted='" + deleted + "'" +
            ", createdDate='" + createdDate + "'" +
            ", lastModifiedDate='" + lastModifiedDate + "'" +
            '}';
    }
}
