package com.llvision.security.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.llvision.security.plugin.model.CarPlate;
import com.llvision.security.plugin.service.CarPlateService;
import com.llvision.security.plugin.service.PluginServiceFactory;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST controller for managing WorkRecord.
 */
@RestController
@RequestMapping("/api")
public class CarPlateResource {

    private final Logger log = LoggerFactory.getLogger(CarPlateResource.class);

    private static final String ENTITY_NAME = "carPlate";

    private final CarPlateService carPlateService;

    public CarPlateResource(PluginServiceFactory pluginServiceFactory) {
        this.carPlateService = pluginServiceFactory.getCarPlateInfoService();
    }

    /**
     * GET  /car-plates/:id : get the "id" carPlate.
     *
     * @param id the id of the carPlate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the CarPlate, or with status 404 (Not Found)
     */
    @GetMapping("/car-plates/{id}")
    @Timed
    public ResponseEntity<CarPlate> getCarPlate(@PathVariable String id) {
        log.debug("REST request to get CarPlate : {}", id);
        CarPlate carPlate = carPlateService.getCarPlateInfo(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(carPlate));
    }

}
