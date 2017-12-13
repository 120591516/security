package com.llvision.security.plugin.service;

import com.llvision.security.config.ApplicationProperties;
import com.llvision.security.plugin.service.yitu.YituFaceResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by llvision on 17/5/5.
 */
@Service
public class PluginServiceFactory {

    private final ApplicationProperties applicationProperties;

    private final EmptyCarPlateInfoService emptyCarPlateInfoService;
    private final EmptyFaceResultService emptyFaceResultService;
    private final EmptyPersonService emptyPersonService;

    private final SingleCarPlateService singleCarPlateService;
    private final SingleFaceResultService singleFaceResultService;
    private final SinglePersonService singlePersonService;

    private final RandomCarPlateService randomCarPlateService;
    private final RandomFaceResultService randomFaceResultService;

    private final YituFaceResultService yituFaceResultService;

    private final LocalFaceResultService localFaceResultService;
    private final LocalPersonService localPersonService;

    private List<SystemConfigService> systemConfigServices;

    @Autowired
    private TestWithYituCarPlateService testWithYituCarPlateService;

    public PluginServiceFactory(ApplicationProperties applicationProperties,
                                EmptyCarPlateInfoService emptyCarPlateInfoService,
                                EmptyFaceResultService emptyFaceResultService,
                                EmptyPersonService emptyPersonService,
                                SingleCarPlateService singleCarPlateService,
                                SingleFaceResultService singleFaceResultService,
                                SinglePersonService singlePersonService,
                                RandomCarPlateService randomCarPlateService,
                                RandomFaceResultService randomFaceResultService,
                                YituFaceResultService yituFaceResultService,
                                LocalFaceResultService localFaceResultService,
                                LocalPersonService localPersonService) {
        this.applicationProperties = applicationProperties;
        this.emptyCarPlateInfoService = emptyCarPlateInfoService;
        this.emptyFaceResultService = emptyFaceResultService;
        this.emptyPersonService = emptyPersonService;
        this.singleCarPlateService = singleCarPlateService;
        this.singleFaceResultService = singleFaceResultService;
        this.singlePersonService = singlePersonService;
        this.randomCarPlateService = randomCarPlateService;
        this.randomFaceResultService = randomFaceResultService;
        this.yituFaceResultService = yituFaceResultService;
        this.localFaceResultService = localFaceResultService;
        this.localPersonService = localPersonService;
    }

    public CarPlateService getCarPlateInfoService() {
        return getCarPlateInfoServiceByName(
            applicationProperties.getPlugin().getCarPlate());
    }

    public CarPlateService getCarPlateInfoServiceByName(String name) {
        switch(name) {
            case "test-with-yitu":
                return testWithYituCarPlateService;
            case "random":
                return randomCarPlateService;
            case "single":
                return singleCarPlateService;
            case "empty":
                // fall through
            default:
                return emptyCarPlateInfoService;
        }
    }

    public FaceResultService getFaceResultService() {
        return getFaceResultServiceByName(
            applicationProperties.getPlugin().getFace());
    }

    public FaceResultService getFaceResultServiceByName(String name) {
        switch(name) {
            case "local":
                return localFaceResultService;
            case "yitu":
                return yituFaceResultService;
            case "random":
                return randomFaceResultService;
            case "single":
                return singleFaceResultService;
            case "empty":
                // fall through
            default:
                return emptyFaceResultService;
        }
    }

    public PersonService getPersonInfoService() {
        return getPersonInfoServiceByName(
            applicationProperties.getPlugin().getPerson());
    }

    public PersonService getPersonInfoServiceByName(String name) {
        switch(name) {
            case "local":
                return localPersonService;
            case "yitu":
                return yituFaceResultService;
            case "single":
                return singlePersonService;
            case "empty":
                // fall through
            default:
                return emptyPersonService;
        }
    }

    public List<SystemConfigService> getSystemConfigServices() {
        if (systemConfigServices == null) {
            systemConfigServices = new ArrayList<>();
            for (String name : applicationProperties.getSystemConfig()) {
                SystemConfigService service = getSystemConfigServiceByName(name);
                if (service != null) {
                    systemConfigServices.add(service);
                }
            }
        }
        return systemConfigServices;
    }

    public SystemConfigService getSystemConfigServiceByName(String name) {
        switch(name) {
            case "yitu":
                return yituFaceResultService;
            default:
                return null;
        }
    }
}
