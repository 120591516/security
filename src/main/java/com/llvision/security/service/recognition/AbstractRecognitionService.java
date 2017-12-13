package com.llvision.security.service.recognition;

import com.llvision.security.config.ApplicationProperties;
import com.llvision.security.domain.RecognitionRecord;
import com.llvision.security.domain.User;
import com.llvision.security.domain.WorkRecord;
import com.llvision.security.repository.RecognitionRecordRepository;
import com.llvision.security.repository.WorkRecordRepository;
import com.llvision.security.service.dto.RecognitionRecordDTO;
import com.llvision.security.service.dto.RecognitionRecordOutputDTO;
import com.llvision.security.service.dto.WorkRecordDTO;
import com.llvision.security.service.dto.WorkRecordOutputDTO;
import com.llvision.security.service.mapper.RecognitionRecordMapper;
import com.llvision.security.service.mapper.RecognitionRecordOutputMapper;
import com.llvision.security.service.mapper.WorkRecordMapper;
import com.llvision.security.service.mapper.WorkRecordOutputMapper;
import com.llvision.security.service.storage.StorageException;
import com.llvision.security.service.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Optional;

/**
 * Created by llvision on 17/5/3.
 */
public abstract class AbstractRecognitionService implements RecognitionService {

    private final Logger log = LoggerFactory.getLogger(AbstractRecognitionService.class);

    private static final long WORK_RECORD_EXPIRE_MINUTES = 5;

    protected final WorkRecordRepository workRecordRepository;
    protected final RecognitionRecordRepository recognitionRecordRepository;
    protected final StorageService storageService;

    protected final WorkRecordOutputMapper workRecordOutputMapper;
    protected final RecognitionRecordOutputMapper recognitionRecordOutputMapper;
    protected final RecognitionRecordMapper recognitionRecordMapper;
    protected final WorkRecordMapper workRecordMapper;

    protected final ApplicationProperties applicationProperties;

    public AbstractRecognitionService(WorkRecordRepository workRecordRepository,
                                      RecognitionRecordRepository recognitionRecordRepository,
                                      StorageService storageService,
                                      WorkRecordOutputMapper workRecordOutputMapper,
                                      RecognitionRecordOutputMapper recognitionRecordOutputMapper,
                                      WorkRecordMapper workRecordMapper,
                                      RecognitionRecordMapper recognitionRecordMapper,
                                      ApplicationProperties applicationProperties) {
        this.workRecordRepository = workRecordRepository;
        this.recognitionRecordRepository = recognitionRecordRepository;
        this.storageService = storageService;
        this.workRecordOutputMapper = workRecordOutputMapper;
        this.recognitionRecordOutputMapper = recognitionRecordOutputMapper;
        this.workRecordMapper = workRecordMapper;
        this.recognitionRecordMapper = recognitionRecordMapper;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public RecognitionRecordOutputDTO insert(RecognitionRecordDTO recognitionRecordDTO, User currentUser) throws StorageException, RecognitionException {
        ZonedDateTime currentTime = ZonedDateTime.now();

        RecognitionRecord recognitionRecord = recognitionRecordMapper.recognitionRecordDTOToRecognitionRecord(recognitionRecordDTO);

        String sourcePicId = generateSourcePictureFileName(recognitionRecordDTO, currentTime, currentUser);

        byte[] data = Base64.getDecoder().decode(recognitionRecordDTO.getSourcePicBase64());
        storageService.store(data, sourcePicId);


        recognitionRecord.setSourcePicId(sourcePicId);
        recognitionRecord.setCreatedBy(currentUser);
        recognitionRecord.setCreatedDate(currentTime);
        recognitionRecord.setLastModifiedBy(currentUser);
        recognitionRecord.setLastModifiedDate(currentTime);

        WorkRecordDTO workRecordDTO = new WorkRecordDTO();
        performRecognition(workRecordDTO, recognitionRecord, recognitionRecordDTO);

        Pageable pageable = new PageRequest(0, 1, Sort.Direction.DESC, "id");

        Page<WorkRecord> workRecordPage = workRecordRepository.findByCreatedByIsCurrentUser(getType(), pageable);
        WorkRecord lastWorkRecord = null;
        if (!workRecordPage.getContent().isEmpty()) {
            lastWorkRecord = workRecordPage.getContent().get(0);
        }
        if (lastWorkRecord == null ||
            lastWorkRecord.getTargetId() == null ||  // For face not found
            !lastWorkRecord.getTargetId().equals(workRecordDTO.getTargetId()) ||
            currentTime.isAfter(lastWorkRecord.getCreatedDate().plusMinutes(WORK_RECORD_EXPIRE_MINUTES))) {
            lastWorkRecord = workRecordMapper.workRecordDTOToWorkRecord(workRecordDTO);
            lastWorkRecord.setAttention(false);
            lastWorkRecord.setCreatedBy(currentUser);
            lastWorkRecord.setCreatedDate(currentTime);
            lastWorkRecord.setLastModifiedBy(currentUser);
            lastWorkRecord.setLastModifiedDate(currentTime);
        }

        lastWorkRecord.setLastRecognizedDate(currentTime);
        if (!Optional.ofNullable(lastWorkRecord.isAttention()).orElse(false) &&
                Optional.ofNullable(workRecordDTO.getAttention()).orElse(false)) {
            lastWorkRecord.setAttention(true);
        }
        lastWorkRecord.setDanger1(
            Optional.ofNullable(lastWorkRecord.getDanger1())
                .orElse(workRecordDTO.getDanger1()));
        lastWorkRecord.setDanger2(
            Optional.ofNullable(lastWorkRecord.getDanger2())
                .orElse(workRecordDTO.getDanger2()));
        lastWorkRecord.setDanger3(
            Optional.ofNullable(lastWorkRecord.getDanger3())
                .orElse(workRecordDTO.getDanger3()));
        lastWorkRecord.setDanger4(
            Optional.ofNullable(lastWorkRecord.getDanger4())
                .orElse(workRecordDTO.getDanger4()));
        lastWorkRecord.setDanger5(
            Optional.ofNullable(lastWorkRecord.getDanger5())
                .orElse(workRecordDTO.getDanger5()));

        workRecordMapper.updateWorkRecordFromWorkRecordDTO(workRecordDTO, lastWorkRecord);

        lastWorkRecord = workRecordRepository.save(lastWorkRecord);
        WorkRecordOutputDTO workRecordOutputDTO = workRecordOutputMapper.workRecordToWorkRecordOutputDTO(lastWorkRecord);
        workRecordOutputDTO.setCarPlate(workRecordDTO.getCarPlate());
        workRecordOutputDTO.setFaceResult(workRecordDTO.getFaceResult());

        recognitionRecord.setWorkRecord(lastWorkRecord);
        recognitionRecord = recognitionRecordRepository.save(recognitionRecord);
        RecognitionRecordOutputDTO recognitionRecordOutputDTO = recognitionRecordOutputMapper.recognitionRecordToRecognitionRecordOutputDTO(recognitionRecord);
        recognitionRecordOutputDTO.setWorkRecord(workRecordOutputDTO);
        return recognitionRecordOutputDTO;
    }

    abstract public int getType();

    abstract public String generateSourcePictureFileName(RecognitionRecordDTO recognitionRecordDTO, ZonedDateTime currentTime, User currentUser);

    // recognitionRecord.getSourcePicId() should contain the source pic url
    abstract public void performRecognition(
        WorkRecordDTO workRecordDTO,
        RecognitionRecord recognitionRecord,
        RecognitionRecordDTO recognitionRecordDTO) throws RecognitionException;
}
