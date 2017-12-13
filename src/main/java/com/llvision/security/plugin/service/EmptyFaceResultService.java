package com.llvision.security.plugin.service;

import com.llvision.security.plugin.model.FaceResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by llvision on 17/5/5.
 */
@Service
public class EmptyFaceResultService implements FaceResultService {
    @Override
    public List<FaceResult> recognizeFaces(byte[] sourcePic, int limit) {
        return null;
    }

    @Override
    public FaceResult getFaceResult(String faceResultId) {
        return null;
    }
}
