package com.llvision.security.service.mapper;

import com.llvision.security.domain.RecognitionRecord;
import com.llvision.security.service.dto.RecognitionRecordOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity RecognitionRecord and its DTO RecognitionRecordDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, WorkRecordOutputMapper.class, })
public interface RecognitionRecordOutputMapper {

    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy.login", target = "createdByLogin")
    @Mapping(source = "createdBy.firstName", target = "createdByFirstName")
    @Mapping(source = "createdBy.lastName", target = "createdByLastName")
    @Mapping(source = "lastModifiedBy.id", target = "lastModifiedById")
    @Mapping(source = "lastModifiedBy.login", target = "lastModifiedByLogin")
    @Mapping(source = "workRecord.id", target = "workRecordId")
    RecognitionRecordOutputDTO recognitionRecordToRecognitionRecordOutputDTO(RecognitionRecord recognitionRecord);

    List<RecognitionRecordOutputDTO> recognitionRecordsToRecognitionRecordOutputDTOs(List<RecognitionRecord> recognitionRecords);

    @Mapping(source = "createdById", target = "createdBy")
    @Mapping(source = "lastModifiedById", target = "lastModifiedBy")
    @Mapping(source = "workRecordId", target = "workRecord")
    RecognitionRecord recognitionRecordOutputDTOToRecognitionRecord(RecognitionRecordOutputDTO recognitionRecordOutputDTO);

    List<RecognitionRecord> recognitionRecordOutputDTOsToRecognitionRecords(List<RecognitionRecordOutputDTO> recognitionRecordOutputDTOs);

    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */

    default RecognitionRecord recognitionRecordFromId(Long id) {
        if (id == null) {
            return null;
        }
        RecognitionRecord recognitionRecord = new RecognitionRecord();
        recognitionRecord.setId(id);
        return recognitionRecord;
    }


}
