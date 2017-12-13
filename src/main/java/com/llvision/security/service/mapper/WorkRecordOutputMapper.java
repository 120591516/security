package com.llvision.security.service.mapper;

import com.llvision.security.domain.WorkRecord;
import com.llvision.security.service.dto.WorkRecordOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity WorkRecord and its DTO WorkRecordDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface WorkRecordOutputMapper {

    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy.login", target = "createdByLogin")
    @Mapping(source = "createdBy.firstName", target = "createdByFirstName")
    @Mapping(source = "createdBy.lastName", target = "createdByLastName")
    @Mapping(source = "lastModifiedBy.id", target = "lastModifiedById")
    @Mapping(source = "lastModifiedBy.login", target = "lastModifiedByLogin")
    WorkRecordOutputDTO workRecordToWorkRecordOutputDTO(WorkRecord workRecord);

    List<WorkRecordOutputDTO> workRecordsToWorkRecordOutputDTOs(List<WorkRecord> workRecords);

    @Mapping(source = "createdById", target = "createdBy")
    @Mapping(source = "lastModifiedById", target = "lastModifiedBy")
    WorkRecord workRecordOutputDTOToWorkRecord(WorkRecordOutputDTO workRecordDTO);

    List<WorkRecord> workRecordOutputDTOsToWorkRecords(List<WorkRecordOutputDTO> workRecorOutputDTOs);

    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */

    default WorkRecord workRecordFromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkRecord workRecord = new WorkRecord();
        workRecord.setId(id);
        return workRecord;
    }


}
