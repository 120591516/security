package com.llvision.security.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.llvision.security.domain.RecognitionRecord;
import com.llvision.security.domain.WorkRecord;
import com.llvision.security.repository.RecognitionRecordRepository;
import com.llvision.security.service.UserService;
import com.llvision.security.service.dto.RecognitionRecordOutputDTO;
import com.llvision.security.service.recognition.RecognitionException;
import com.llvision.security.service.recognition.RecognitionNotFoundException;
import com.llvision.security.service.recognition.RecognitionService;
import com.llvision.security.service.recognition.RecognitionServiceFactory;
import com.llvision.security.service.storage.StorageException;
import com.llvision.security.web.rest.util.HeaderUtil;
import com.llvision.security.web.rest.util.PaginationUtil;
import com.llvision.security.service.dto.RecognitionRecordDTO;
import com.llvision.security.service.mapper.RecognitionRecordMapper;
import com.llvision.security.web.websocket.RealTimeService;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RecognitionRecord.
 */
@RestController
@RequestMapping("/api")
public class RecognitionRecordResource {

    private final Logger log = LoggerFactory.getLogger(RecognitionRecordResource.class);

    private static final String ENTITY_NAME = "recognitionRecord";

    private final RecognitionRecordRepository recognitionRecordRepository;

    private final RecognitionRecordMapper recognitionRecordMapper;

    private final UserService userService;

    private final RecognitionServiceFactory recognitionServiceFactory;

    private RealTimeService realTimeService;


    public RecognitionRecordResource(RecognitionRecordRepository recognitionRecordRepository, RecognitionRecordMapper recognitionRecordMapper, UserService userService, RecognitionServiceFactory recognitionServiceFactory, RealTimeService realTimeService) {
        this.recognitionRecordRepository = recognitionRecordRepository;
        this.recognitionRecordMapper = recognitionRecordMapper;
        this.userService = userService;
        this.recognitionServiceFactory = recognitionServiceFactory;
        this.realTimeService = realTimeService;

    }

    /**
     * POST  /recognition-records : Create a new recognitionRecord.
     *
     * @param recognitionRecordDTO the recognitionRecordDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recognitionRecordDTO, or with status 400 (Bad Request) if the recognitionRecord has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recognition-records")
    @Timed
    public ResponseEntity<RecognitionRecordOutputDTO> createRecognitionRecord(@Valid @RequestBody RecognitionRecordDTO recognitionRecordDTO) throws URISyntaxException {
        log.debug("REST request to save RecognitionRecord : {}", recognitionRecordDTO);
        if (recognitionRecordDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new recognitionRecord cannot already have an ID")).body(null);
        }

        try {
            RecognitionService recognitionService = recognitionServiceFactory.getServiceByType(recognitionRecordDTO.getType());
            return Optional.of(userService.getUserWithAuthorities())
                .map(user -> {
                    try {
                        RecognitionRecordOutputDTO recognitionRecordOutputDTO = recognitionService.insert(recognitionRecordDTO, user);
                        //实时同步到前端
                        realTimeService.sendrecognitionRecordOutputDTO(recognitionRecordOutputDTO);
                        return ResponseEntity.created(new URI("/api/recognition-records/" + recognitionRecordOutputDTO.getId()))
                            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, recognitionRecordOutputDTO.getId().toString()))
                            .body(recognitionRecordOutputDTO);
                    } catch (StorageException e) {
                        return new ResponseEntity<RecognitionRecordOutputDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
                    } catch (RecognitionException e) {
                        return new ResponseEntity<RecognitionRecordOutputDTO>(HttpStatus.NOT_FOUND);
                    } catch (URISyntaxException e) {
                        return new ResponseEntity<RecognitionRecordOutputDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                })
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (RecognitionException e) {
            return new ResponseEntity<RecognitionRecordOutputDTO>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * PUT  /recognition-records : Updates an existing recognitionRecord.
     *
     * @param recognitionRecordDTO the recognitionRecordDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recognitionRecordDTO,
     * or with status 400 (Bad Request) if the recognitionRecordDTO is not valid,
     * or with status 500 (Internal Server Error) if the recognitionRecordDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recognition-records")
    @Timed
    public ResponseEntity<RecognitionRecordDTO> updateRecognitionRecord(@Valid @RequestBody RecognitionRecordDTO recognitionRecordDTO) throws URISyntaxException {
        log.debug("REST request to update RecognitionRecord : {}", recognitionRecordDTO);
        if (recognitionRecordDTO.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idnotexists", "An updated recognitionRecord should have an ID")).body(null);
        }
        findAndValidateRecognitionRecord(recognitionRecordDTO.getId());
        RecognitionRecord recognitionRecord = recognitionRecordMapper.recognitionRecordDTOToRecognitionRecord(recognitionRecordDTO);
        recognitionRecord = recognitionRecordRepository.save(recognitionRecord);
        RecognitionRecordDTO result = recognitionRecordMapper.recognitionRecordToRecognitionRecordDTO(recognitionRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recognitionRecordDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /work-records/:workRecordId/recognition-records : get all the recognitionRecords that belong to the workRecord.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of recognitionRecords in body
     */
    @GetMapping("/work-records/{workRecordId}/recognition-records")
    @Timed
    public ResponseEntity<List<RecognitionRecord>> getAllRecognitionRecords(@PathVariable Long workRecordId, @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RecognitionRecords");
        WorkRecord workRecord = recognitionServiceFactory.getWorkRecord(workRecordId);
        Page<RecognitionRecord> page = recognitionRecordRepository.findByWorkRecordId(workRecordId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/work-records/" + workRecordId + "/recognition-records");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /recognition-records/:id : get the "id" recognitionRecord.
     *
     * @param id the id of the recognitionRecordDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recognitionRecordDTO, or with status 404 (Not Found)
     */
    @GetMapping("/recognition-records/{id}")
    @Timed
    public ResponseEntity<RecognitionRecord> getRecognitionRecord(@PathVariable Long id) {
        log.debug("REST request to get RecognitionRecord : {}", id);
        RecognitionRecord recognitionRecord = findAndValidateRecognitionRecord(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(recognitionRecord));
    }

    /**
     * DELETE  /recognition-records/:id : delete the "id" recognitionRecord.
     *
     * @param id the id of the recognitionRecordDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recognition-records/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecognitionRecord(@PathVariable Long id) {
        log.debug("REST request to delete RecognitionRecord : {}", id);
        findAndValidateRecognitionRecord(id);
        recognitionRecordRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    private RecognitionRecord findAndValidateRecognitionRecord(Long id) {
        RecognitionRecord recognitionRecord = recognitionRecordRepository.findOne(id);
        if (!recognitionServiceFactory.validateRecognitionRecordAccess(recognitionRecord)) {
            throw new RecognitionNotFoundException();
        }
        return recognitionRecord;
    }

}
