package com.llvision.security.service.mapper;

import com.llvision.security.domain.*;
import com.llvision.security.service.dto.LocalFaceSetDTO;

import org.mapstruct.*;
import java.util.List;
import java.util.Set;

/**
 * Mapper for the entity LocalFaceSet and its DTO LocalFaceSetDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface LocalFaceSetMapper {

    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy.login", target = "createdByLogin")
    @Mapping(source = "lastModifiedBy.id", target = "lastModifiedById")
    @Mapping(source = "lastModifiedBy.login", target = "lastModifiedByLogin")
    @Mapping(target = "users", source = "users")
    LocalFaceSetDTO localFaceSetToLocalFaceSetDTO(LocalFaceSet localFaceSet);

    List<LocalFaceSetDTO> localFaceSetsToLocalFaceSetDTOs(List<LocalFaceSet> localFaceSets);

    List<LocalFaceSetDTO> localFaceSetsToLocalFaceSetDTOs(Set<LocalFaceSet> localFaceSets);

    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "users", ignore = true)
    void updateLocalFaceSetFromLocalFaceSetDTO(LocalFaceSetDTO localFaceSetDTO, @MappingTarget LocalFaceSet localFaceSet);

    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "users", ignore = true)
    LocalFaceSet localFaceSetDTOToLocalFaceSet(LocalFaceSetDTO localFaceSetDTO);

    List<LocalFaceSet> localFaceSetDTOsToLocalFaceSets(List<LocalFaceSetDTO> localFaceSetDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */

    default LocalFaceSet localFaceSetFromId(Long id) {
        if (id == null) {
            return null;
        }
        LocalFaceSet localFaceSet = new LocalFaceSet();
        localFaceSet.setId(id);
        return localFaceSet;
    }


}
