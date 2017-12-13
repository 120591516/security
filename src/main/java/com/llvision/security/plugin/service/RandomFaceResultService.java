package com.llvision.security.plugin.service;

import com.llvision.security.plugin.model.FaceResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by llvision on 17/5/5.
 */
@Service
public class RandomFaceResultService implements FaceResultService {

    private static final String FACE_ID1 = "hxm";
    private static final String FACE_ID2 = "baby";

    private final HashMap<String, FaceResult> faceResultMap;

    private final SinglePersonService singlePersonService;

    public RandomFaceResultService(SinglePersonService singlePersonService) {
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
    public List<FaceResult> recognizeFaces(byte[] sourcePic, int limit) throws NoTargetException {
        ArrayList<FaceResult> faceResults = new ArrayList<>();
        long t = System.currentTimeMillis();
        Random r = new Random(t / 1000 / 30); // Rotate every 30s
        int c = r.nextInt() % 5;
        FaceResult f1 = faceResultMap.get(FACE_ID1);
        FaceResult f2 = faceResultMap.get(FACE_ID2);
        Random r2 = new Random(t);
        double s1 = r2.nextDouble() * 0.3 + 0.7;
        double s2 = r2.nextDouble() * 0.3 + 0.6;
        switch (c) {
            case 0:
                f1.setSimilarity(s1 > s2 ? s1 : s2);
                faceResults.add(f1);
                f2.setSimilarity(s1 > s2 ? s2 : s1);
                faceResults.add(f2);
                break;
            case 1:
                f2.setSimilarity(s1 > s2 ? s1 : s2);
                faceResults.add(f2);
                f1.setSimilarity(s1 > s2 ? s2 : s1);
                faceResults.add(f1);
                break;
            case 2:
                return null;  // Collect only
            case 3:
                break;  // No face
            case 4:
                throw new NoTargetException();
        }
        return faceResults.subList(0, Math.min(faceResults.size(), limit));
    }

    @Override
    public FaceResult getFaceResult(String faceResultId) {
        return faceResultMap.get(faceResultId);
    }
}
