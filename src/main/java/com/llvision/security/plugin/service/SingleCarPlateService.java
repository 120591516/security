package com.llvision.security.plugin.service;

import com.llvision.security.plugin.model.CarPlate;
import com.llvision.security.plugin.model.Person;
import org.springframework.stereotype.Service;

/**
 * Created by llvision on 17/5/5.
 */
@Service
public class SingleCarPlateService implements CarPlateService {

    private final SinglePersonService singlePersonService;

    public SingleCarPlateService(SinglePersonService singlePersonService) {
        this.singlePersonService = singlePersonService;
    }

    @Override
    public CarPlate getCarPlateInfo(String id) {
        CarPlate info = new CarPlate();
        info.setId(id);
        info.setBrand("宝马牌");
        info.setModel("318");
        info.setCarColor("白色");
        info.setCarType("小型轿车");
        info.setAttention(false);

        Person person = singlePersonService.getPerson(SinglePersonService.PERSON_INFO_ID1);
        info.setOwnerPerson(person);

        info.setOwner(person.getName());
        info.setAddress(person.getAddress());
        return info;
    }
}
