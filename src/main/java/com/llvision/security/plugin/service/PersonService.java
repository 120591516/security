package com.llvision.security.plugin.service;

import com.llvision.security.plugin.model.Person;
import com.llvision.security.plugin.model.PersonImage;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by llvision on 17/5/5.
 */
public interface PersonService {

    Person getPerson(String id);

    PersonImage getImage(String imageId) throws IOException;
}
