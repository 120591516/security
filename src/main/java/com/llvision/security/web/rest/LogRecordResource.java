package com.llvision.security.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.llvision.security.domain.LogRecord;
import com.llvision.security.domain.WorkRecord;
import com.llvision.security.repository.LogRecordRepository;
import com.llvision.security.service.UserService;
import com.llvision.security.service.log.record.LogRecordNotFoundException;
import com.llvision.security.service.mapper.LogRecordUpdateMapper;
import com.llvision.security.service.recognition.RecognitionServiceFactory;
import com.llvision.security.web.rest.util.HeaderUtil;
import com.llvision.security.web.rest.util.PaginationUtil;
import com.llvision.security.service.dto.LogRecordDTO;
import com.llvision.security.service.mapper.LogRecordMapper;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LogRecord.
 */
@RestController
@RequestMapping("/api")
public class LogRecordResource {

    private final Logger log = LoggerFactory.getLogger(LogRecordResource.class);

    private static final String ENTITY_NAME = "logRecord";

    private final LogRecordRepository logRecordRepository;

    private final LogRecordMapper logRecordMapper;

    private final UserService userService;

    private final LogRecordUpdateMapper logRecordUpdateMapper;

    private final RecognitionServiceFactory recognitionServiceFactory;

    public LogRecordResource(LogRecordRepository logRecordRepository,
                             LogRecordMapper logRecordMapper,
                             RecognitionServiceFactory recognitionServiceFactory,
                             UserService userService,
                             LogRecordUpdateMapper logRecordUpdateMapper) {
        this.logRecordRepository = logRecordRepository;
        this.logRecordMapper = logRecordMapper;
        this.recognitionServiceFactory = recognitionServiceFactory;
        this.userService = userService;
        this.logRecordUpdateMapper = logRecordUpdateMapper;
    }

    /**
     * POST  /log-records : Create a new logRecord.
     *
     * @param logRecordDTO the logRecordDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new logRecordDTO, or with status 400 (Bad Request) if the logRecord has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/log-records")
    @Timed
    public ResponseEntity<LogRecordDTO> createLogRecord(@Valid @RequestBody LogRecordDTO logRecordDTO) throws URISyntaxException, UnsupportedEncodingException {
        log.debug("REST request to save LogRecord : {}", logRecordDTO);
        if (logRecordDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new logRecord cannot already have an ID")).body(null);
        }
        if (logRecordDTO.getText() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "TextNotNull", "Text must not be empty")).body(null);
        }
        logRecordDTO.setCreatedDate(ZonedDateTime.now());
        logRecordDTO.setCreatedById(userService.getUserWithAuthorities().getId());
        LogRecord logRecord = logRecordMapper.logRecordDTOToLogRecord(logRecordDTO);
        logRecord.setDeleted(Boolean.FALSE);
        logRecord = logRecordRepository.save(logRecord);
        LogRecordDTO result = logRecordMapper.logRecordToLogRecordDTO(logRecord);
        log.debug("LogRecordDTO : {}", result);
        return ResponseEntity.created(new URI("/api/log-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /log-records : Updates an existing logRecord.
     *
     * @param logRecordDTO the logRecordDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated logRecordDTO,
     * or with status 400 (Bad Request) if the logRecordDTO is not valid,
     * or with status 500 (Internal Server Error) if the logRecordDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/log-records")
    @Timed
    public ResponseEntity<LogRecordDTO> updateLogRecord(@Valid @RequestBody LogRecordDTO logRecordDTO) throws URISyntaxException, UnsupportedEncodingException {
        log.debug("REST request to update LogRecord : {}", logRecordDTO);
        if (logRecordDTO.getId() == null) {
            return createLogRecord(logRecordDTO);
        }
        LogRecord logRecord = logRecordRepository.findOne(logRecordDTO.getId());
        logRecordUpdateMapper.updateLogRecordFromLogRecordDTO(logRecordDTO, logRecord);
        if(!recognitionServiceFactory.validateLogRecordAccess(logRecord)){
            throw new LogRecordNotFoundException();
        }
        logRecord = logRecordRepository.save(logRecord);
        LogRecordDTO result = logRecordMapper.logRecordToLogRecordDTO(logRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, logRecordDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /log-records : get all the logRecords.
     *
     * @param pageable the pagination information,workRecordId the WorkRecord id
     * @return the ResponseEntity with status 200 (OK) and the list of logRecords in body
     */
    @GetMapping("/log-records")
    @Timed
    public ResponseEntity<List<LogRecordDTO>> getAllLogRecords(@RequestParam Long workRecordId,@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of LogRecords and workRecordId {}",workRecordId);
        WorkRecord workRecord = recognitionServiceFactory.getWorkRecord(workRecordId);
        Page<LogRecord> page = logRecordRepository.findAllByWorkRecordId(workRecordId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/log-records");
        return new ResponseEntity<>(logRecordMapper.logRecordsToLogRecordDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /log-records/:id : get the "id" logRecord.
     *
     * @param id the id of the logRecordDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the logRecordDTO, or with status 404 (Not Found)
     */
    @GetMapping("/log-records/{id}")
    @Timed
    public ResponseEntity<LogRecordDTO> getLogRecord(@PathVariable Long id) {
        log.debug("REST request to get LogRecord : {}", id);
        LogRecord logRecord = logRecordRepository.findOne(id);
        LogRecordDTO logRecordDTO = logRecordMapper.logRecordToLogRecordDTO(logRecord);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(logRecordDTO));
    }

    /**
     * DELETE  /log-records/:id : delete the "id" logRecord.
     *
     * @param id the id of the logRecordDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/log-records/{id}")
    @Timed
    public ResponseEntity<Void> deleteLogRecord(@PathVariable Long id) {
        log.debug("REST request to delete LogRecord : {}", id);
        LogRecord logRecord = logRecordRepository.findOne(id);
        if(!recognitionServiceFactory.validateLogRecordAccess(logRecord)){
            return  ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "delete error")).body(null);
        }
        logRecord.setDeleted(Boolean.TRUE);
        logRecord = logRecordRepository.save(logRecord);
        logRecordMapper.logRecordToLogRecordDTO(logRecord);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
