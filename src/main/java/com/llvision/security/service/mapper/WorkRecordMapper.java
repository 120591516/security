package com.llvision.security.service.mapper;

import com.llvision.security.domain.*;
import com.llvision.security.service.dto.WorkRecordDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity WorkRecord and its DTO WorkRecordDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface WorkRecordMapper {

    WorkRecordDTO workRecordToWorkRecordDTO(WorkRecord workRecord);

    List<WorkRecordDTO> workRecordsToWorkRecordDTOs(List<WorkRecord> workRecords);

    WorkRecord workRecordDTOToWorkRecord(WorkRecordDTO workRecordDTO);

    List<WorkRecord> workRecordDTOsToWorkRecords(List<WorkRecordDTO> workRecordDTOs);

    @Mapping(target="attention", ignore=true)
    @Mapping(target="danger1", ignore=true)
    @Mapping(target="danger2", ignore=true)
    @Mapping(target="danger3", ignore=true)
    @Mapping(target="danger4", ignore=true)
    @Mapping(target="danger5", ignore=true)
    void updateWorkRecordFromWorkRecordDTO(WorkRecordDTO workRecordDTO, @MappingTarget WorkRecord workRecord);
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
