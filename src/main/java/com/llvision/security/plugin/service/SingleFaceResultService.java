package com.llvision.security.plugin.service;

import com.llvision.security.plugin.model.FaceResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by llvision on 17/5/5.
 */
@Service
public class SingleFaceResultService implements FaceResultService {

    private static final String FACE_ID1 = "hxm";
    private static final String FACE_ID2 = "baby";

    private final HashMap<String, FaceResult> faceResultMap;

    private final SinglePersonService singlePersonService;

    public SingleFaceResultService(SinglePersonService singlePersonService) {
        this.singlePersonService = singlePersonService;
        faceResultMap = new HashMap<>();

        FaceResult faceResult = new FaceResult();
        faceResult.setSimilarity(0.8975);
        faceResult.setId(FACE_ID1);
        faceResult.setImageId(SinglePersonService.IMAGE_ID1);
        faceResult.setPersonInfoId(SinglePersonService.PERSON_INFO_ID1);
        faceResult.setPerson(singlePersonService.getPerson(faceResult.getPersonInfoId()));
        faceResultMap.put(FACE_ID1, faceResult);

        faceResult = new FaceResult();
        faceResult.setSimilarity(0.7276);
        faceResult.setId(FACE_ID2);
        faceResult.setImageId(SinglePersonService.IMAGE_ID2);
        faceResult.setPersonInfoId(SinglePersonService.PERSON_INFO_ID2);
        faceResult.setPerson(singlePersonService.getPerson(faceResult.getPersonInfoId()));
        faceResultMap.put(FACE_ID2, faceResult);
    }

    @Override
    public List<FaceResult> recognizeFaces(byte[] sourcePic, int limit) {
        ArrayList<FaceResult> faceResults = new ArrayList<>();
        if (limit > 0) {
            faceResults.add(faceResultMap.get(FACE_ID1));
        }

        if (limit > 1) {
            faceResults.add(faceResultMap.get(FACE_ID2));
        }
        return faceResults;
    }

    @Override
    public FaceResult getFaceResult(String faceResultId) {
        return faceResultMap.get(faceResultId);
    }
}
