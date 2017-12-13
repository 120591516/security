package com.llvision.security.service.mapper;

import com.llvision.security.domain.LogRecord;
import com.llvision.security.domain.WorkRecord;
import com.llvision.security.service.dto.LogRecordDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper for the entity LogRecord and its DTO LogRecordDTO.
 */
@Mapper(componentModel = "spring", uses = {WorkRecordMapper.class, UserMapper.class, })
public interface LogRecordUpdateMapper {


    void updateLogRecordFromLogRecordDTO(LogRecordDTO logRecordDTO ,@MappingTarget LogRecord logRecord);


    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */

    default LogRecord logRecordFromId(Long id) {
        if (id == null) {
            return null;
        }
        LogRecord logRecord = new LogRecord();
        logRecord.setId(id);
        return logRecord;
    }


}
