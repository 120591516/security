package com.llvision.security.service.recognition;

import com.llvision.security.config.ApplicationProperties;
import com.llvision.security.config.Constants;
import com.llvision.security.domain.RecognitionRecord;
import com.llvision.security.domain.User;
import com.llvision.security.plugin.model.CarPlate;
import com.llvision.security.plugin.service.CarPlateService;
import com.llvision.security.plugin.service.PluginServiceFactory;
import com.llvision.security.repository.RecognitionRecordRepository;
import com.llvision.security.repository.WorkRecordRepository;
import com.llvision.security.service.dto.RecognitionRecordDTO;
import com.llvision.security.service.dto.WorkRecordDTO;
import com.llvision.security.service.dto.WorkRecordOutputDTO;
import com.llvision.security.service.mapper.RecognitionRecordMapper;
import com.llvision.security.service.mapper.RecognitionRecordOutputMapper;
import com.llvision.security.service.mapper.WorkRecordMapper;
import com.llvision.security.service.mapper.WorkRecordOutputMapper;
import com.llvision.security.service.storage.StorageService;
import com.llvision.security.service.util.StatusEnum;

import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by llvision on 17/5/3.
 */
public class CarPlateRecognitionService extends AbstractRecognitionService {

    private static final String BASE_DIRECTORY = "car_plate";
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    private final CarPlateService carPlateService;

    public CarPlateRecognitionService(WorkRecordRepository workRecordRepository,
                                      RecognitionRecordRepository recognitionRecordRepository,
                                      StorageService storageService,
                                      WorkRecordOutputMapper workRecordOutputMapper,
                                      RecognitionRecordOutputMapper recognitionRecordOutputMapper,
                                      WorkRecordMapper workRecordMapper,
                                      RecognitionRecordMapper recognitionRecordMapper,
                                      PluginServiceFactory pluginServiceFactory,
                                      ApplicationProperties applicationProperties) {
        super(workRecordRepository, recognitionRecordRepository, storageService, workRecordOutputMapper,
            recognitionRecordOutputMapper, workRecordMapper, recognitionRecordMapper, applicationProperties);
        carPlateService = pluginServiceFactory.getCarPlateInfoService();
    }

    @Override
    public int getType() {
        return Constants.RECOGNITION_TYPE_CAR_PLATE;
    }

    @Override
    public String generateSourcePictureFileName(RecognitionRecordDTO recognitionRecordDTO, ZonedDateTime currentTime, User currentUser) {
        return Paths.get(BASE_DIRECTORY,
            "IMG_" + currentTime.format(DATETIME_FORMATTER) + "_" + currentUser.getLogin()
                + "_" + recognitionRecordDTO.getTargetId() + "." + recognitionRecordDTO.getSourcePicSuffix()).toString();
    }

    @Override
    public void performRecognition(WorkRecordDTO workRecordDTO, RecognitionRecord recognitionRecord, RecognitionRecordDTO recognitionRecordDTO) throws RecognitionException {
        if (recognitionRecordDTO.getTargetId() == null) {
            throw new RecognitionException("Car plate recognition failed, target id is null");
        }

        CarPlate carPlate = carPlateService.getCarPlateInfo(recognitionRecordDTO.getTargetId());
        recognitionRecord.setStatus(StatusEnum.SUCCESS.getCode());
        recognitionRecord.setSimilarity(1.0);
        if (carPlate != null) {
            if (carPlate.isAttention()) {
                workRecordDTO.setDanger2(1);  // 违章
                recognitionRecord.setAttention(carPlate.isAttention());
                workRecordDTO.setAttention(carPlate.isAttention());
            }
            if (carPlate.getOwnerPerson() != null && carPlate.getOwnerPerson().isAttention()) {
                workRecordDTO.setDanger3(1);  // 其他违法
                recognitionRecord.setAttention(carPlate.getOwnerPerson().isAttention());
                workRecordDTO.setAttention(carPlate.getOwnerPerson().isAttention());
            }
        }

        workRecordDTO.setTargetId(recognitionRecordDTO.getTargetId());
        workRecordDTO.setTargetPicId(null);
        workRecordDTO.setSimilarity(1.0);
        workRecordDTO.setType(recognitionRecordDTO.getType());
        workRecordDTO.setSourcePicId(recognitionRecord.getSourcePicId());
        workRecordDTO.setStatus(recognitionRecord.getStatus());
        workRecordDTO.setCarPlate(carPlate);
    }

    @Override
    public WorkRecordOutputDTO fillWorkRecord(WorkRecordOutputDTO workRecordOutputDTO) {
        workRecordOutputDTO.setCarPlate(carPlateService.getCarPlateInfo(workRecordOutputDTO.getTargetId()));
        return workRecordOutputDTO;
    }
}
