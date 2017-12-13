package com.llvision.security.plugin.service;

import com.llvision.security.config.ApplicationProperties;
import com.llvision.security.domain.LocalFace;
import com.llvision.security.plugin.model.Person;
import com.llvision.security.plugin.model.PersonImage;
import com.llvision.security.repository.LocalFaceRepository;
import com.llvision.security.service.storage.StorageNotFoundException;
import com.llvision.security.service.storage.StorageService;
import com.llvision.security.service.util.StorageUtil;
import com.llvision.security.web.rest.PersonResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Created by llvision on 17/5/5.
 */
@Service
public class LocalPersonService implements PersonService {

    private final Logger log = LoggerFactory.getLogger(LocalPersonService.class);

    @Autowired
    private LocalFaceRepository localFaceRepository;

    @Autowired
    private StorageService storageService;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private PluginServiceFactory pluginServiceFactory;

    public LocalPersonService() {}

    @Override
    public Person getPerson(String id) {
        LocalFace face = null;
        try {
            face = localFaceRepository.findOne(Long.parseLong(id));
        } catch (NumberFormatException e) {
            // Do nothing
        }
        if (face == null) {
            return pluginServiceFactory.getPersonInfoServiceByName(
                applicationProperties.getLocalFace().getUpstreamPlugin().getPerson())
                .getPerson(id);
        }
        final LocalFace finalFace = face;
        Person person = new Person();
        person.setId(id);
        person.setName(face.getName());
        Optional.ofNullable(face.getPersonId()).ifPresent(person::setPersonId);
        Optional.ofNullable(face.getGender()).ifPresent(person::setGender);
        Optional.ofNullable(face.getBirthday()).ifPresent(birthday -> person.setBirthday(ZonedDateTime.of(finalFace.getBirthday(), LocalTime.MIDNIGHT, ZoneId.systemDefault())));
        Optional.ofNullable(face.getAddress()).ifPresent(person::setAddress);
        Optional.ofNullable(face.isAttention()).ifPresent(person::setAttention);
        Optional.ofNullable(face.getDanger()).ifPresent(person::setDanger);
        person.setImageId(face.getImage());
        return person;
    }

    @Override
    public PersonImage getImage(String imageId) throws IOException {
        try {
            Resource resource = null;
            try{
                resource = storageService.loadAsResource(imageId);
            }catch (InvalidPathException e){
                log.error("imageId error {}",imageId);
                log.error("图片加载失败，尝试用依图加载...");
                return pluginServiceFactory.getPersonInfoServiceByName(
                    applicationProperties.getLocalFace().getUpstreamPlugin().getPerson())
                    .getImage(imageId);
            }
            if (!resource.exists() || !resource.isReadable()) {
                return pluginServiceFactory.getPersonInfoServiceByName(
                    applicationProperties.getLocalFace().getUpstreamPlugin().getPerson())
                    .getImage(imageId);
            }
            byte[] buf = new byte[(int) resource.contentLength()];
            resource.getInputStream().read(buf);
            PersonImage image = new PersonImage();
            image.setId(imageId);
            image.setContentType(StorageUtil.getContentType(imageId));
            image.setData(buf);
            return image;
        } catch (StorageNotFoundException e) {
            return pluginServiceFactory.getPersonInfoServiceByName(
                applicationProperties.getLocalFace().getUpstreamPlugin().getPerson())
                .getImage(imageId);
        }
    }
}
