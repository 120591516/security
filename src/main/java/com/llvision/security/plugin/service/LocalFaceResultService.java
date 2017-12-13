package com.llvision.security.plugin.service;

import com.llvision.security.config.ApplicationProperties;
import com.llvision.security.domain.LocalFace;
import com.llvision.security.plugin.model.FaceResult;
import com.llvision.security.repository.LocalFaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by llvision on 17/5/5.
 */
@Service
public class LocalFaceResultService implements FaceResultService {

    @Autowired
    private LocalPersonService localPersonService;
    @Autowired
    private LocalFaceRepository localFaceRepository;
    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private PluginServiceFactory pluginServiceFactory;

    public LocalFaceResultService() {}

    @Override
    public List<FaceResult> recognizeFaces(byte[] sourcePic, int limit) throws NoTargetException {
        return pluginServiceFactory.getFaceResultServiceByName(
            applicationProperties.getLocalFace().getUpstreamPlugin().getFace())
            .recognizeFaces(sourcePic, limit);
    }

    @Override
    public FaceResult getFaceResult(String faceResultId) {
        LocalFace face = localFaceRepository.findOne(Long.parseLong(faceResultId));
        if (face == null) {
            return pluginServiceFactory.getFaceResultServiceByName(
                applicationProperties.getLocalFace().getUpstreamPlugin().getFace())
                .getFaceResult(faceResultId);
        }
        FaceResult faceResult = new FaceResult();
        faceResult.setId(faceResultId);
        faceResult.setPersonInfoId(faceResultId);
        faceResult.setImageId(face.getImage());
        faceResult.setPerson(localPersonService.getPerson(faceResultId));
        return faceResult;
    }
}
