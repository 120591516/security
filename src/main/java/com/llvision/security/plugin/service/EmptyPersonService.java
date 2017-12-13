package com.llvision.security.plugin.service;

import com.llvision.security.plugin.model.Person;
import com.llvision.security.plugin.model.PersonImage;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by llvision on 17/5/5.
 */
@Service
public class EmptyPersonService implements PersonService {
    @Override
    public Person getPerson(String id) {
        return null;
    }

    @Override
    public PersonImage getImage(String imageId) throws IOException {
        throw new FileNotFoundException();
    }
}
