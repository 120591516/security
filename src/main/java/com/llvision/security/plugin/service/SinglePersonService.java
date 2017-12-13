package com.llvision.security.plugin.service;

import com.llvision.security.plugin.model.Person;
import com.llvision.security.plugin.model.PersonImage;
import com.llvision.security.service.util.StorageUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;

/**
 * Created by llvision on 17/5/5.
 */
@Service
public class SinglePersonService implements PersonService {

    // Package visible
    static final String PERSON_INFO_ID1 = "hxm";
    static final String PERSON_INFO_ID2 = "baby";
    static final String IMAGE_ID1 = "hxm.jpg";
    static final String IMAGE_ID2 = "baby.jpg";

    private final HashMap<String, Person> personMap;

    private final ResourceLoader resourceLoader;

    public SinglePersonService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        personMap = new HashMap<>();

        Person person = new Person();
        person.setId(PERSON_INFO_ID1);
        person.setName("小黄");
        person.setPersonId("111011199303033523");
        person.setAddress("郑州市金水区文化路XX号院X号楼XX号");
        person.setBirthday(ZonedDateTime.of(1993, 3, 3, 0, 0, 0, 0, ZoneId.systemDefault()));
        person.setGender(Person.GENDER_MALE);
        person.setAttention(true);
        person.setDanger(3);
        person.setImageId(IMAGE_ID1);
        personMap.put(PERSON_INFO_ID1, person);

        person = new Person();
        person.setId(PERSON_INFO_ID2);
        person.setName("小杨");
        person.setPersonId("111011199406063523");
        person.setAddress("郑州市金水区文化路XX号院X号楼XX号");
        person.setBirthday(ZonedDateTime.of(1994, 6, 6, 0, 0, 0, 0, ZoneId.systemDefault()));
        person.setGender(Person.GENDER_FEMALE);
        person.setAttention(false);
        person.setImageId(IMAGE_ID2);
        personMap.put(PERSON_INFO_ID2, person);
    }

    @Override
    public Person getPerson(String id) {
        return personMap.get(id);
    }

    @Override
    public PersonImage getImage(String imageId) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:person/" + imageId);
        if (!resource.exists() || !resource.isReadable()) {
            throw new FileNotFoundException();
        }
        byte[] buf = new byte[(int) resource.contentLength()];
        resource.getInputStream().read(buf);
        PersonImage image = new PersonImage();
        image.setId(imageId);
        image.setContentType(StorageUtil.getContentType(imageId));
        image.setData(buf);
        return image;
    }
}
