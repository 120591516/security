package com.llvision.security.service.storage;


import com.llvision.security.service.dto.UserDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the LocalFaceSet entity.
 */
public class UploadFileDTO implements Serializable {

    private String path;

    private long size;

    private String contentType;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UploadFileDTO localFaceSetDTO = (UploadFileDTO) o;

        if ( ! Objects.equals(path, localFaceSetDTO.path) || size != localFaceSetDTO.size) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(path) * 31 + Objects.hashCode(size);
    }

    @Override
    public String toString() {
        return "LocalFaceSetDTO{" +
            "path=" + path +
            ", size='" + size + "'" +
            ", contentType='" + contentType + "'" +
            '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
