package com.llvision.security.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.llvision.security.plugin.model.Person;
import com.llvision.security.plugin.model.PersonImage;
import com.llvision.security.plugin.service.PersonService;
import com.llvision.security.plugin.service.PluginServiceFactory;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

/**
 * REST controller for managing WorkRecord.
 */
@RestController
@RequestMapping("/api")
public class PersonResource {

    private final Logger log = LoggerFactory.getLogger(PersonResource.class);

    private static final String ENTITY_NAME = "Person";

    private final PersonService personService;

    public PersonResource(PluginServiceFactory pluginServiceFactory) {
        this.personService = pluginServiceFactory.getPersonInfoService();
    }

    /**
     * GET  /persons/:id : get the "id" person.
     *
     * @param id the id of the person to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the Person, or with status 404 (Not Found)
     */
    @GetMapping("/persons/{id}")
    @Timed
    public ResponseEntity<Person> getPerson(@PathVariable String id) {
        log.debug("REST request to get Person : {}", id);
        Person result = personService.getPerson(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * GET  /person-image?path=:path : get the "path" of file in person image in raw data.
     *
     * @param path the file path to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the file, or with status 404 (Not Found)
     */
    @GetMapping(value = "/person-image")
    @Timed
    public ResponseEntity<Resource> getPersonImage(@RequestParam String path) {

        log.debug("REST request to serve plugin file : {}", path);
        if (StringUtils.isEmpty(path)) {
            log.error("REST request to serve plugin file Error path not null : {}", path);
        }
        try {
            PersonImage image = personService.getImage(path);
            ByteArrayResource resource = new ByteArrayResource(image.getData());
            return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .body(resource);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
