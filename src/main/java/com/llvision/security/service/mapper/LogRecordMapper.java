package com.llvision.security.service.mapper;

import com.llvision.security.domain.*;
import com.llvision.security.service.dto.LogRecordDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LogRecord and its DTO LogRecordDTO.
 */
@Mapper(componentModel = "spring", uses = {WorkRecordMapper.class, UserMapper.class, })
public interface LogRecordMapper {

    @Mapping(source = "workRecord.id", target = "workRecordId")
    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy.login", target = "createdByLogin")
    LogRecordDTO logRecordToLogRecordDTO(LogRecord logRecord);

    List<LogRecordDTO> logRecordsToLogRecordDTOs(List<LogRecord> logRecords);

    @Mapping(source = "workRecordId", target = "workRecord")
    @Mapping(source = "createdById", target = "createdBy")
    LogRecord logRecordDTOToLogRecord(LogRecordDTO logRecordDTO);

    List<LogRecord> logRecordDTOsToLogRecords(List<LogRecordDTO> logRecordDTOs);
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
