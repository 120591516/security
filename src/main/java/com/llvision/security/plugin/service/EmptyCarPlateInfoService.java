package com.llvision.security.plugin.service;

import com.llvision.security.plugin.model.CarPlate;
import org.springframework.stereotype.Service;

/**
 * Created by llvision on 17/5/5.
 */
@Service
public class EmptyCarPlateInfoService implements CarPlateService {
    @Override
    public CarPlate getCarPlateInfo(String id) {
        return null;
    }
}
