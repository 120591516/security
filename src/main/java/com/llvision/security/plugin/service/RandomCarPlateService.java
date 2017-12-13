package com.llvision.security.plugin.service;

import com.llvision.security.plugin.model.CarPlate;
import com.llvision.security.plugin.model.Person;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by llvision on 17/5/5.
 */
@Service
public class RandomCarPlateService implements CarPlateService {

    private final SinglePersonService singlePersonService;

    public RandomCarPlateService(SinglePersonService singlePersonService) {
        this.singlePersonService = singlePersonService;
    }

    @Override
    public CarPlate getCarPlateInfo(String id) {
        CarPlate info = new CarPlate();
        info.setId(id);
        Random r = new Random(id.hashCode());
        int c = r.nextInt() % 3;
        Person person;
        switch (c) {
            case 0:
                info.setBrand("宝马牌");
                info.setModel("318");
                info.setCarColor("白色");
                info.setCarType("小型轿车");
                info.setAttention(r.nextInt() % 2 == 0);

                person = singlePersonService.getPerson(SinglePersonService.PERSON_INFO_ID1);
                info.setOwnerPerson(person);

                info.setOwner(person.getName());
                info.setAddress(person.getAddress());
                break;
            case 1:
                info.setBrand("奔驰牌");
                info.setModel("GLK250");
                info.setCarColor("黑色");
                info.setCarType("中型轿车");
                info.setAttention(r.nextInt() % 2 == 0);

                person = singlePersonService.getPerson(SinglePersonService.PERSON_INFO_ID2);
                info.setOwnerPerson(person);

                info.setOwner(person.getName());
                info.setAddress(person.getAddress());
                break;
            case 2:
            default:
                break;
        }
        return info;
    }
}
