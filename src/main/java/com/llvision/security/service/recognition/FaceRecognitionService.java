package com.llvision.security.service.recognition;

import com.llvision.security.config.ApplicationProperties;
import com.llvision.security.config.Constants;
import com.llvision.security.domain.RecognitionRecord;
import com.llvision.security.domain.User;
import com.llvision.security.plugin.model.FaceResult;
import com.llvision.security.plugin.service.*;
import com.llvision.security.repository.RecognitionRecordRepository;
import com.llvision.security.repository.WorkRecordRepository;
import com.llvision.security.service.dto.RecognitionRecordDTO;
import com.llvision.security.service.dto.RecognitionResultDTO;
import com.llvision.security.service.dto.WorkRecordDTO;
import com.llvision.security.service.dto.WorkRecordOutputDTO;
import com.llvision.security.service.mapper.RecognitionRecordMapper;
import com.llvision.security.service.mapper.RecognitionRecordOutputMapper;
import com.llvision.security.service.mapper.WorkRecordMapper;
import com.llvision.security.service.mapper.WorkRecordOutputMapper;
import com.llvision.security.service.storage.StorageService;
import com.llvision.security.service.util.StatusEnum;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Created by llvision on 17/5/3.
 */
public class FaceRecognitionService extends AbstractRecognitionService {

    private static final String BASE_DIRECTORY = "face";
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    private final FaceResultService faceResultService;
    private final PersonService personService;
    private final ApplicationProperties.Recognition.Face face;

    public FaceRecognitionService(WorkRecordRepository workRecordRepository,
                                  RecognitionRecordRepository recognitionRecordRepository,
                                  StorageService storageService,
                                  WorkRecordOutputMapper workRecordOutputMapper,
                                  RecognitionRecordOutputMapper recognitionRecordOutputMapper,
                                  WorkRecordMapper workRecordMapper,
                                  RecognitionRecordMapper recognitionRecordMapper,
                                  ApplicationProperties applicationProperties,
                                  PluginServiceFactory pluginServiceFactory) {
        super(workRecordRepository, recognitionRecordRepository, storageService, workRecordOutputMapper,
            recognitionRecordOutputMapper, workRecordMapper, recognitionRecordMapper,applicationProperties);
        faceResultService = pluginServiceFactory.getFaceResultService();
        face = applicationProperties.getRecognition().getFace();
        personService = pluginServiceFactory.getPersonInfoService();
    }

    @Override
    public int getType() {
        return Constants.RECOGNITION_TYPE_FACE;
    }

    @Override
    public String generateSourcePictureFileName(RecognitionRecordDTO recognitionRecordDTO, ZonedDateTime currentTime, User currentUser) {
        return Paths.get(BASE_DIRECTORY,
            "IMG_" + currentTime.format(DATETIME_FORMATTER) + "_" + currentUser.getLogin()
                + "." + recognitionRecordDTO.getSourcePicSuffix()).toString();
    }

    @Override
    public void performRecognition(WorkRecordDTO workRecordDTO, RecognitionRecord recognitionRecord, RecognitionRecordDTO recognitionRecordDTO) throws RecognitionException {
        // TODO: share source pic generated in byte[]
        List<FaceResult> faceResultList = null;
        FaceResult mostSimilarFaceResult = null;
        if (recognitionRecordDTO.getRecognitionResults() != null) {
            faceResultList = new ArrayList<>();
            for (RecognitionResultDTO recognitionResultDTO : recognitionRecordDTO.getRecognitionResults()) {
                FaceResult faceResult = faceResultService.getFaceResult(recognitionResultDTO.getTargetId());
                if (faceResult == null) {
                    continue;
                }
                faceResult.setSimilarity(recognitionResultDTO.getSimilarity());
                faceResultList.add(faceResult);
                if (mostSimilarFaceResult == null || mostSimilarFaceResult.getSimilarity() < faceResult.getSimilarity()) {
                    mostSimilarFaceResult = faceResult;
                }
            }
        }

        if (mostSimilarFaceResult == null || mostSimilarFaceResult.getSimilarity() < face.getThreshold()) {
            try {
                List<FaceResult> faceResultListYiTu = faceResultService.recognizeFaces(
                    Base64.getDecoder().decode(recognitionRecordDTO.getSourcePicBase64()), face.getLimit());
                if (faceResultListYiTu !=null && faceResultListYiTu.size() > 0){
                    faceResultList = faceResultListYiTu;
                }
            } catch (NoTargetException e) {
                throw new RecognitionException(StatusEnum.NO_MATCH.getMsg());
            }
        }

        if(faceResultList != null && faceResultList.size() > face.getFaces()){
             faceResultList = new ArrayList<>(faceResultList.subList(0,face.getFaces()));
        }

        if (faceResultList == null || faceResultList.isEmpty()) {
            // No result is found
            // Just record
            if (faceResultList == null) {
                recognitionRecord.setStatus(StatusEnum.COLLECT_ONLY.getCode());
            } else {
                recognitionRecord.setStatus(StatusEnum.NO_MATCH.getCode());
            }
            recognitionRecord.setAttention(false);
            recognitionRecord.setSimilarity(0.0);

            workRecordDTO.setTargetId(null);
            workRecordDTO.setTargetPicId(null);
            workRecordDTO.setSimilarity(null);
            workRecordDTO.setFaceResult(null);
        } else {
            // Take first result
            FaceResult mostSimilarResult = faceResultList.get(0);

            if (mostSimilarResult.getPerson() != null) {
                recognitionRecord.setAttention(mostSimilarResult.getPerson().isAttention());
                workRecordDTO.setAttention(mostSimilarResult.getPerson().isAttention());
                workRecordDTO.setDanger1(mostSimilarResult.getPerson().getDanger());
            }

            if( mostSimilarResult.getSimilarity() < applicationProperties.getRecognition().getFace().getThreshold()){
                mostSimilarResult.setId(null);
                recognitionRecord.setAttention(false);
                workRecordDTO.setAttention(false);
                recognitionRecord.setStatus(StatusEnum.NO_MATCH.getCode());
            }else {
                recognitionRecord.setStatus(StatusEnum.SUCCESS.getCode());
            }
            recognitionRecord.setSimilarity(mostSimilarResult.getSimilarity());
            workRecordDTO.setTargetId(mostSimilarResult.getId());
            workRecordDTO.setTargetPicId(mostSimilarResult.getImageId());
            workRecordDTO.setSimilarity(mostSimilarResult.getSimilarity());
            workRecordDTO.setFaceResult(mostSimilarResult);

            // Store all results
            try {
                ObjectMapper mapper = new ObjectMapper();
                StringWriter writer = new StringWriter();
                JsonGenerator generator = mapper.getJsonFactory().createJsonGenerator(writer);
                generator.writeObject(faceResultList);
                recognitionRecord.setInfo(writer.toString().getBytes());
                recognitionRecord.setInfoContentType("application/json");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        workRecordDTO.setStatus(recognitionRecord.getStatus());
        workRecordDTO.setType(recognitionRecordDTO.getType());
        workRecordDTO.setSourcePicId(recognitionRecord.getSourcePicId());
        // Note that we should not set workRecord's attention
    }

    @Override
    public WorkRecordOutputDTO fillWorkRecord(WorkRecordOutputDTO workRecordOutputDTO) {
        if (workRecordOutputDTO.getTargetId() != null) {
            workRecordOutputDTO.setFaceResult(faceResultService.getFaceResult(workRecordOutputDTO.getTargetId()));
        }
        return workRecordOutputDTO;
    }
}
