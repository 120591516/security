package com.llvision.security.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.llvision.security.domain.WorkRecord;

import com.llvision.security.domain.WorkRecordStat;
import com.llvision.security.domain.WorkRecordUserStat;
import com.llvision.security.repository.RecognitionRecordRepository;
import com.llvision.security.repository.WorkRecordRepository;
import com.llvision.security.security.SecurityUtils;
import com.llvision.security.service.dto.WorkRecordOutputDTO;
import com.llvision.security.service.dto.WorkRecordUpdateDTO;
import com.llvision.security.service.mapper.WorkRecordOutputMapper;
import com.llvision.security.service.mapper.WorkRecordUpdateMapper;
import com.llvision.security.service.recognition.RecognitionServiceFactory;
import com.llvision.security.web.rest.util.HeaderUtil;
import com.llvision.security.web.rest.util.PaginationUtil;
import com.llvision.security.service.mapper.WorkRecordMapper;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WorkRecord.
 */
@RestController
@RequestMapping("/api")
public class WorkRecordResource {

    private final Logger log = LoggerFactory.getLogger(WorkRecordResource.class);

    private static final String ENTITY_NAME = "workRecord";

    private final WorkRecordRepository workRecordRepository;

    private final WorkRecordMapper workRecordMapper;

    private final WorkRecordOutputMapper workRecordOutputMapper;

    private final WorkRecordUpdateMapper workRecordUpdateMapper;

    private final RecognitionRecordRepository recognitionRecordRepository;

    private final RecognitionServiceFactory recognitionServiceFactory;

    public WorkRecordResource(WorkRecordRepository workRecordRepository,
                              WorkRecordMapper workRecordMapper,
                              WorkRecordOutputMapper workRecordOutputMapper,
                              WorkRecordUpdateMapper workRecordUpdateMapper,
                              RecognitionRecordRepository recognitionRecordRepository,
                              RecognitionServiceFactory recognitionServiceFactory) {
        this.workRecordRepository = workRecordRepository;
        this.workRecordMapper = workRecordMapper;
        this.workRecordOutputMapper = workRecordOutputMapper;
        this.workRecordUpdateMapper = workRecordUpdateMapper;
        this.recognitionRecordRepository = recognitionRecordRepository;
        this.recognitionServiceFactory = recognitionServiceFactory;
    }

    /**
     * PUT  /work-records : Updates an existing workRecord.
     *
     * @param workRecordUpdateDTO the workRecordUpdateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workRecordDTO,
     * or with status 400 (Bad Request) if the workRecordDTO is not valid,
     * or with status 500 (Internal Server Error) if the workRecordDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/work-records")
    @Timed
    public ResponseEntity<WorkRecordOutputDTO> updateWorkRecord(@Valid @RequestBody WorkRecordUpdateDTO workRecordUpdateDTO) throws URISyntaxException {
        log.debug("REST request to update WorkRecord : {}", workRecordUpdateDTO);
        if (workRecordUpdateDTO.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idnotexists", "An updated workRecord should have an ID")).body(null);
        }
        WorkRecord workRecord = workRecordRepository.findOne(workRecordUpdateDTO.getId());
        workRecordUpdateMapper.updateWorkRecordFromWorkRecordUpdateDTO(workRecordUpdateDTO, workRecord);
        workRecord = workRecordRepository.save(workRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workRecord.getId().toString()))
            .body(workRecordOutputMapper.workRecordToWorkRecordOutputDTO(workRecord));
    }

    /**
     * GET  /work-records : get all the workRecords.
     *
     * @param pageable the pagination information
     * @param type type for work records
     * @return the ResponseEntity with status 200 (OK) and the list of workRecords in body
     */
    @GetMapping("/work-records")
    @Timed
    public ResponseEntity<List<WorkRecordOutputDTO>> getAllWorkRecords(@RequestParam int type, @ApiParam Pageable pageable) {
        // TODO: Use another DTO to represent workRecord without car plate
        log.debug("REST request to get a page of WorkRecords");
        Page<WorkRecord> page;
        if ( SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
            page = workRecordRepository.findAll(type, pageable);
        } else {
            page = workRecordRepository.findByCreatedByIsCurrentUser(type, pageable);
        }
        List<WorkRecordOutputDTO> results = workRecordOutputMapper.workRecordsToWorkRecordOutputDTOs(page.getContent());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/work-records");
        return new ResponseEntity<>(results, headers, HttpStatus.OK);
    }

    /**
     * GET  /work-records/:id : get the "id" workRecord.
     *
     * @param id the id of the workRecordOutputDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workRecordDTO, or with status 404 (Not Found)
     */
    @GetMapping("/work-records/{id}")
    @Timed
    public ResponseEntity<WorkRecordOutputDTO> getWorkRecord(@PathVariable Long id) {
        // TODO: Use another DTO to represent workRecord without car plate
        log.debug("REST request to get WorkRecord : {}", id);
        WorkRecord workRecord = recognitionServiceFactory.getWorkRecord(id);
        WorkRecordOutputDTO workRecordOutputDTO = workRecordOutputMapper.workRecordToWorkRecordOutputDTO(workRecord);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(workRecordOutputDTO));
    }

    /**
     * DELETE  /work-records/:id : delete the "id" workRecord.
     *
     * @param id the id of the workRecordDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/work-records/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkRecord(@PathVariable Long id) {
        log.debug("REST request to delete WorkRecord : {}", id);
        WorkRecord workRecord = recognitionServiceFactory.getWorkRecord(id);
        recognitionServiceFactory.deleteWorkRecord(workRecord);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /work-records/stats : get the stats for workRecord for last given days.
     *
     * @param type type for work records
     * @param days number of days prior to today for the stats
     * @return the ResponseEntity with status 200 (OK) and with body the workRecordDTO, or with status 404 (Not Found)
     */
    @GetMapping("/work-records/stats")
    @Timed
    public ResponseEntity<List<WorkRecordStat>> getWorkRecordStats(@RequestParam int type, @RequestParam int days) {
        log.debug("REST request to get WorkRecord stats for type : {}", type);
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime startOfToday = now.truncatedTo(ChronoUnit.DAYS);
        List<WorkRecordStat> stats = new ArrayList<>();
        for (int i = days - 1; i >= 0; i--) {
            ZonedDateTime startDate = startOfToday.minusDays(i);
            ZonedDateTime endDate = startDate.plusDays(1);
            List<WorkRecordStat> results;
            if ( SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
                results = workRecordRepository.getAllWorkRecordStats(type, startDate, endDate);
            } else {
                results = workRecordRepository.getWorkRecordStatsCreatedByCurrentUser(type, startDate, endDate);
            }
            if (results != null && !results.isEmpty()) {
                results.get(0).setDate(startDate);
                stats.add(results.get(0));
            }
        }
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

    /**
     * GET  /work-records/user-stats : get the user stats for workRecord for last given days.
     *
     * @param type type for work records
     * @param days number of days prior to today for the stats
     * @return the ResponseEntity with status 200 (OK) and with body the workRecordDTO, or with status 404 (Not Found)
     */
    @GetMapping("/work-records/user-stats")
    @Timed
    public ResponseEntity<Page<WorkRecordUserStat>> getWorkRecordUserStats(
            @RequestParam int type, @RequestParam int days, Pageable pageable) {
        log.debug("REST request to get WorkRecord user stats for type : {}", type);
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime startDate = now.truncatedTo(ChronoUnit.DAYS).minusDays(days - 1);
        pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize());

        Page<WorkRecordUserStat> page = workRecordRepository.getTopPerformingUserStats(type, startDate, now, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/work-records/user-stats");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }
}
