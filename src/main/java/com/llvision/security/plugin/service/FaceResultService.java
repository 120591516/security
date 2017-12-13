package com.llvision.security.plugin.service;

import com.llvision.security.plugin.model.FaceResult;

import java.util.List;

/**
 * Created by llvision on 17/5/5.
 */
public interface FaceResultService {

    List<FaceResult> recognizeFaces(byte[] sourcePic, int limit) throws NoTargetException;

    FaceResult getFaceResult(String faceResultId);
}
