package com.llvision.security.service.recognition;

import com.llvision.security.config.ApplicationProperties;
import com.llvision.security.config.Constants;
import com.llvision.security.domain.LogRecord;
import com.llvision.security.domain.RecognitionRecord;
import com.llvision.security.domain.WorkRecord;
import com.llvision.security.plugin.service.PluginServiceFactory;
import com.llvision.security.repository.RecognitionRecordRepository;
import com.llvision.security.repository.WorkRecordRepository;
import com.llvision.security.security.SecurityUtils;
import com.llvision.security.service.mapper.RecognitionRecordMapper;
import com.llvision.security.service.mapper.RecognitionRecordOutputMapper;
import com.llvision.security.service.mapper.WorkRecordMapper;
import com.llvision.security.service.mapper.WorkRecordOutputMapper;
import com.llvision.security.service.storage.StorageService;
import org.springframework.stereotype.Service;

/**
 * Created by llvision on 17/5/3.
 */
@Service
public class RecognitionServiceFactory {

    private final WorkRecordRepository workRecordRepository;
    private final RecognitionRecordRepository recognitionRecordRepository;

    private final CarPlateRecognitionService carPlateRecognitionService;
    private final FaceRecognitionService  faceRecognitionService;

    public RecognitionServiceFactory(WorkRecordRepository workRecordRepository,
                                     RecognitionRecordRepository recognitionRecordRepository,
                                     StorageService storageService,
                                     WorkRecordOutputMapper workRecordOutputMapper,
                                     RecognitionRecordOutputMapper recognitionRecordOutputMapper,
                                     WorkRecordMapper workRecordMapper,
                                     RecognitionRecordMapper recognitionRecordMapper,
                                     ApplicationProperties applicationProperties,
                                     PluginServiceFactory pluginServiceFactory) {
        this.workRecordRepository = workRecordRepository;
        this.recognitionRecordRepository = recognitionRecordRepository;
        this.carPlateRecognitionService = new CarPlateRecognitionService(workRecordRepository,
            recognitionRecordRepository, storageService, workRecordOutputMapper, recognitionRecordOutputMapper,
            workRecordMapper, recognitionRecordMapper, pluginServiceFactory, applicationProperties);
        this.faceRecognitionService = new FaceRecognitionService(workRecordRepository,
            recognitionRecordRepository, storageService, workRecordOutputMapper, recognitionRecordOutputMapper,
            workRecordMapper, recognitionRecordMapper, applicationProperties, pluginServiceFactory);
    }

    public RecognitionService getServiceByType(int type) throws RecognitionException {
        switch (type) {
            case Constants.RECOGNITION_TYPE_CAR_PLATE:
                return carPlateRecognitionService;
            case Constants.RECOGNITION_TYPE_FACE:
                return faceRecognitionService;
            default:
                throw new RecognitionException("Invalid recognition type");
        }
    }

    public boolean validateWorkRecordAccess(WorkRecord workRecord) {
        return (workRecord.isDeleted() == null || !workRecord.isDeleted())
            && (workRecord.getCreatedBy().getLogin().equals(SecurityUtils.getCurrentUserLogin())
                || SecurityUtils.isCurrentUserInRole("ROLE_ADMIN"));
    }

    public boolean validateLogRecordAccess(LogRecord logRecord) {
        return (logRecord.isDeleted() == null || !logRecord.isDeleted())
            && (logRecord.getCreatedBy().getLogin().equals(SecurityUtils.getCurrentUserLogin())
            || SecurityUtils.isCurrentUserInRole("ROLE_ADMIN"));
    }

    public boolean validateRecognitionRecordAccess(RecognitionRecord recognitionRecord) {
        return (recognitionRecord.getWorkRecord() != null && validateWorkRecordAccess(recognitionRecord.getWorkRecord())
            && (recognitionRecord.isDeleted() == null || !recognitionRecord.isDeleted()));
    }

    public WorkRecord getWorkRecord(Long id) {
        WorkRecord workRecord = workRecordRepository.findOne(id);
        if (workRecord == null || !validateWorkRecordAccess(workRecord)) {
            throw new RecognitionNotFoundException();
        }
        return workRecord;
    }

    public RecognitionRecord getRecognitionRecord(Long id) {
        RecognitionRecord recognitionRecord = recognitionRecordRepository.findOne(id);
        if (recognitionRecord == null || !validateRecognitionRecordAccess(recognitionRecord)) {
            throw new RecognitionNotFoundException();
        }
        return recognitionRecord;
    }

    public void deleteWorkRecord(WorkRecord workRecord) {
        workRecord.setDeleted(true);
        workRecordRepository.save(workRecord);
        recognitionRecordRepository.findByWorkRecordId(workRecord.getId()).forEach(recognitionRecord -> {
            recognitionRecord.setDeleted(true);
            recognitionRecordRepository.save(recognitionRecord);
        });
    }
}
