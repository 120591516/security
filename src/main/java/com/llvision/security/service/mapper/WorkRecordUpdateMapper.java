package com.llvision.security.service.mapper;

import com.llvision.security.domain.WorkRecord;
import com.llvision.security.service.dto.WorkRecordUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper for the entity WorkRecord and its DTO WorkRecordDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface WorkRecordUpdateMapper {

    void updateWorkRecordFromWorkRecordUpdateDTO(WorkRecordUpdateDTO workRecordUpdateDTO, @MappingTarget WorkRecord workRecord);

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
