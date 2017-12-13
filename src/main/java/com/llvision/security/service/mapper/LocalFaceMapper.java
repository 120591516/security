package com.llvision.security.service.mapper;

import com.llvision.security.domain.*;
import com.llvision.security.service.dto.LocalFaceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LocalFace and its DTO LocalFaceDTO.
 */
@Mapper(componentModel = "spring", uses = {LocalFaceSetMapper.class, })
public interface LocalFaceMapper {

    @Mapping(source = "faceSet.id", target = "faceSetId")
    LocalFaceDTO localFaceToLocalFaceDTO(LocalFace localFace);

    List<LocalFaceDTO> localFacesToLocalFaceDTOs(List<LocalFace> localFaces);

    @Mapping(source = "faceSetId", target = "faceSet")
    LocalFace localFaceDTOToLocalFace(LocalFaceDTO localFaceDTO);

    List<LocalFace> localFaceDTOsToLocalFaces(List<LocalFaceDTO> localFaceDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default LocalFace localFaceFromId(Long id) {
        if (id == null) {
            return null;
        }
        LocalFace localFace = new LocalFace();
        localFace.setId(id);
        return localFace;
    }
    

}
