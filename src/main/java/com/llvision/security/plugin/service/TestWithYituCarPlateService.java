package com.llvision.security.plugin.service;

import com.llvision.security.plugin.model.CarPlate;
import com.llvision.security.plugin.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by llvision on 17/5/5.
 */
@Service
public class TestWithYituCarPlateService implements CarPlateService {

    private static final String CAR_PLATE_ID1 = "豫B66666_蓝";
    private static final String CAR_PLATE_ID2 = "豫C63801_黄";

    // 谢娜
    private static final String PERSON_INFO_ID1 = "1407374883553304";
    // 黄岩
    private static final String PERSON_INFO_ID2 = "27866022694355063";

    @Autowired
    private PluginServiceFactory pluginServiceFactory;

    @Override
    public CarPlate getCarPlateInfo(String id) {
        if (CAR_PLATE_ID1.equals(id)) {
            CarPlate info = new CarPlate();
            info.setId(id);
            info.setBrand("宝马牌");
            info.setModel("318");
            info.setCarColor("白");
            info.setCarType("小型轿车");
            info.setAttention(true);

            Person person = pluginServiceFactory.getPersonInfoService().getPerson(PERSON_INFO_ID1);
            if (person != null) {
                info.setOwnerPerson(person);

                info.setOwner(person.getName());
                info.setAddress(person.getAddress());
            }
            return info;
        } else if (CAR_PLATE_ID2.equals(id)) {
            CarPlate info = new CarPlate();
            info.setId(id);
            info.setBrand("东风牌");
            info.setModel("GLK250");
            info.setCarColor("黑绿");
            info.setCarType("重型卡车");
            info.setAttention(false);

            Person person = pluginServiceFactory.getPersonInfoService().getPerson(PERSON_INFO_ID2);
            if (person != null) {
                info.setOwnerPerson(person);

                info.setOwner(person.getName());
                info.setAddress(person.getAddress());
            }
            return info;
        }
        return null;
    }
}
