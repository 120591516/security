package com.llvision.security.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.llvision.security.domain.LocalFaceSet;
import com.llvision.security.domain.User;
import com.llvision.security.repository.LocalFaceRepository;
import com.llvision.security.repository.LocalFaceSetRepository;
import com.llvision.security.repository.UserRepository;
import com.llvision.security.security.SecurityUtils;
import com.llvision.security.service.PushLocalFaceSet;
import com.llvision.security.service.UserService;
import com.llvision.security.service.dto.LocalFaceSetDTO;
import com.llvision.security.service.dto.UserDTO;
import com.llvision.security.service.mapper.LocalFaceSetMapper;
import com.llvision.security.web.rest.errors.ErrorForbidden;
import com.llvision.security.web.rest.util.HeaderUtil;
import com.llvision.security.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * REST controller for managing LocalFaceSet.
 */
@RestController
@RequestMapping("/api")
public class LocalFaceSetResource {

    private final Logger log = LoggerFactory.getLogger(LocalFaceSetResource.class);

    private static final String ENTITY_NAME = "localFaceSet";

    private final LocalFaceSetRepository localFaceSetRepository;

    private final LocalFaceSetMapper localFaceSetMapper;

    private final UserRepository userRepository;

    private final UserService userService;

    private final PushLocalFaceSet pushLocalFaceSet;

    private final LocalFaceRepository localFaceRepository;


    public LocalFaceSetResource(LocalFaceSetRepository localFaceSetRepository,
                                LocalFaceSetMapper localFaceSetMapper,
                                UserRepository userRepository,
                                UserService userService,
                                PushLocalFaceSet pushLocalFaceSet,
                                LocalFaceRepository localFaceRepository) {
        this.localFaceSetRepository = localFaceSetRepository;
        this.localFaceSetMapper = localFaceSetMapper;
        this.userRepository = userRepository;
        this.userService = userService;
        this.pushLocalFaceSet = pushLocalFaceSet;
        this.localFaceRepository = localFaceRepository;
    }

    /**
     * POST  /local-face-sets : Create a new localFaceSet.
     *
     * @param localFaceSetDTO the localFaceSetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new localFaceSetDTO, or with status 400 (Bad Request) if the localFaceSet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/local-face-sets")
    @Timed
    public ResponseEntity<LocalFaceSetDTO> createLocalFaceSet(@Valid @RequestBody LocalFaceSetDTO localFaceSetDTO) throws URISyntaxException {
        log.debug("REST request to save LocalFaceSet : {}", localFaceSetDTO);
        if (localFaceSetDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new localFaceSet cannot already have an ID")).body(null);
        }
        if (localFaceSetDTO.getName() == null || "".equals(localFaceSetDTO.getName())) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "notNull", "notNull")).body(null);
        }
        if (localFaceSetRepository.findOneWithEagerRelationships(localFaceSetDTO.getName()) != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "offlineLibraryExists", "offlineLibraryExists")).body(null);
        }
        final LocalFaceSet localFaceSet = localFaceSetMapper.localFaceSetDTOToLocalFaceSet(localFaceSetDTO);
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            localFaceSet.setCreatedBy(user);
            localFaceSet.setLastModifiedBy(user);
        });
        ZonedDateTime now = ZonedDateTime.now();
        localFaceSet.setCreatedDate(now);
        localFaceSet.setLastModifiedDate(now);
        LocalFaceSet localFaceSet2 = localFaceSetRepository.save(localFaceSet);
        LocalFaceSetDTO result = localFaceSetMapper.localFaceSetToLocalFaceSetDTO(localFaceSet2);
        result.setLocalFaces(localFaceRepository.findSizeByFaceSetId(localFaceSetDTO.getId()));
        return ResponseEntity.created(new URI("/api/local-face-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /local-face-sets : Updates an existing localFaceSet.
     *
     * @param localFaceSetDTO the localFaceSetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated localFaceSetDTO,
     * or with status 400 (Bad Request) if the localFaceSetDTO is not valid,
     * or with status 500 (Internal Server Error) if the localFaceSetDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/local-face-sets")
    @Timed
    public ResponseEntity<LocalFaceSetDTO> updateLocalFaceSet(@Valid @RequestBody LocalFaceSetDTO localFaceSetDTO) throws URISyntaxException {
        log.debug("REST request to update LocalFaceSet : {}", localFaceSetDTO);
        if (localFaceSetDTO.getId() == null) {
            return createLocalFaceSet(localFaceSetDTO);
        }
        final LocalFaceSet localFaceSet = findAndValidateOwner(localFaceSetDTO.getId());
        localFaceSetMapper.updateLocalFaceSetFromLocalFaceSetDTO(localFaceSetDTO, localFaceSet);
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            localFaceSet.setLastModifiedBy(user);
        });
        ZonedDateTime now = ZonedDateTime.now();
        localFaceSet.setLastModifiedDate(now);
        LocalFaceSet localFaceSet2 = localFaceSetRepository.save(localFaceSet);
        LocalFaceSetDTO result = localFaceSetMapper.localFaceSetToLocalFaceSetDTO(localFaceSet2);
        result.setLocalFaces(localFaceRepository.findSizeByFaceSetId(localFaceSetDTO.getId()));
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, localFaceSetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /local-face-sets : get all the localFaceSets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of localFaceSets in body
     */
    @GetMapping("/local-face-sets")
    @Timed
    public ResponseEntity<List<LocalFaceSetDTO>> getAllLocalFaceSets(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of LocalFaceSets");
        Page<LocalFaceSet> page = localFaceSetRepository.findByCreatedByIsCurrentUser(pageable);
        List<LocalFaceSetDTO> localFaceSetDTOs = localFaceSetMapper.localFaceSetsToLocalFaceSetDTOs(page.getContent());
        localFaceSetDTOs.forEach(localFaceSetDTO -> {
            localFaceSetDTO.setLocalFaces(localFaceRepository.findSizeByFaceSetId(localFaceSetDTO.getId()));
        });
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/local-face-sets");
        return new ResponseEntity<>(localFaceSetDTOs, headers, HttpStatus.OK);
    }

    /**
     * GET  /local-face-sets/activated : get all activated localFaceSets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of localFaceSets in body
     */
    @GetMapping("/local-face-sets/activated")
    @Timed
    public ResponseEntity<List<LocalFaceSetDTO>> getAllActivatedLocalFaceSets(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of LocalFaceSets");
        Page<LocalFaceSet> page = localFaceSetRepository.findActivatedByCreatedByIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/local-face-sets/activated");
        List<LocalFaceSetDTO> localFaceSetDTOs = localFaceSetMapper.localFaceSetsToLocalFaceSetDTOs(page.getContent());
        localFaceSetDTOs.forEach(localFaceSetDTO -> {
            localFaceSetDTO.setLocalFaces(localFaceRepository.findSizeByFaceSetId(localFaceSetDTO.getId()));
        });
        return new ResponseEntity<>(localFaceSetDTOs, headers, HttpStatus.OK);
    }

    /**
     * GET  /local-face-sets/:id : get the "id" localFaceSet.
     *
     * @param id the id of the localFaceSetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the localFaceSetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/local-face-sets/{id}")
    @Timed
    public ResponseEntity<LocalFaceSetDTO> getLocalFaceSet(@PathVariable Long id) {
        log.debug("REST request to get LocalFaceSet : {}", id);
        LocalFaceSet localFaceSet = findAndValidate(id);
        LocalFaceSetDTO localFaceSetDTO = localFaceSetMapper.localFaceSetToLocalFaceSetDTO(localFaceSet);
        for(UserDTO userDTO:localFaceSetDTO.getUsers()){
            userDTO.setLastModifiedDate(pushLocalFaceSet.getTime(localFaceSetDTO.getId().toString(), userDTO.getId().toString()));
        }
        localFaceSetDTO.setLocalFaces(localFaceRepository.findSizeByFaceSetId(localFaceSetDTO.getId()));
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(localFaceSetDTO));
    }

    /**
     * DELETE  /local-face-sets/:id : delete the "id" localFaceSet.
     *
     * @param id the id of the localFaceSetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/local-face-sets/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocalFaceSet(@PathVariable Long id) {
        log.debug("REST request to delete LocalFaceSet : {}", id);
        LocalFaceSet localFaceSet = findAndValidateOwner(id);
        localFaceSetRepository.save(localFaceSet.deleted(true));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    private LocalFaceSet findAndValidate(Long id) {
        LocalFaceSet localFaceSet = localFaceSetRepository.findOneWithEagerRelationships(id);
        if (localFaceSet == null ||
            !(Objects.equals(localFaceSet.getCreatedBy().getLogin(), SecurityUtils.getCurrentUserLogin()) ||
            localFaceSet.getUsers().contains(
                userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get()))) {
            throw new ErrorForbidden();
        }
        return localFaceSet;
    }

    private LocalFaceSet findAndValidateOwner(Long id) {
        LocalFaceSet localFaceSet = localFaceSetRepository.findOneWithEagerRelationships(id);
        if (localFaceSet == null ||
            !Objects.equals(localFaceSet.getCreatedBy().getLogin(), SecurityUtils.getCurrentUserLogin())) {
            throw new ErrorForbidden();
        }
        return localFaceSet;
    }

    /**
     * GET  /local-face-set-share/users : get Shared user list
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of localFaceSets in body
     */
    @GetMapping("/local-face-set/{id}/users")
    @Timed
    public ResponseEntity<List<LocalFaceSetDTO>> getLocalFaceSetShareUser(@PathVariable Long id, @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of LocalFaceSets");
        Page<LocalFaceSet> page = localFaceSetRepository.findByCreatedByIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/local-face-set/{id}/users");
        return new ResponseEntity<>(localFaceSetMapper.localFaceSetsToLocalFaceSetDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    @PostMapping("/local-face-set/{id}/users/{userLogin}")
    @Timed
    public ResponseEntity<LocalFaceSetDTO> updateLocalFaceSetShare(@PathVariable Long id, @PathVariable String userLogin) throws URISyntaxException {
        log.debug("REST request to update LocalFaceSet : id:{}, userLogin:{}", id, userLogin);
        if (userLogin == null || id == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idnotshareexists", "login name or localFaceSet id not null")).body(null);
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if(!user.isPresent()){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "usernotexists", "login  null")).body(null);
        }
        try {
            LocalFaceSet localFaceSet = localFaceSetRepository.findOne(id);
            if(localFaceSet.getCreatedBy() != null && userLogin.equals(localFaceSet.getCreatedBy().getLogin())){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "noPushYourself", "Don't push for yourself")).body(null);
            }
            if(!localFaceSet.getUsers().contains(user.get())){
                user.ifPresent(user1 -> {
                    localFaceSet.addUser(user1);
                    localFaceSetRepository.saveAndFlush(localFaceSet);
                    pushLocalFaceSet.updateTime(localFaceSet.getId().toString(), user1.getId().toString());
                });
            }else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "norepeatshare", "no repeat share")).body(null);
            }
            LocalFaceSetDTO localFaceSetDTO = localFaceSetMapper.localFaceSetToLocalFaceSetDTO(localFaceSet);
            localFaceSetDTO.setLocalFaces(localFaceRepository.findSizeByFaceSetId(localFaceSetDTO.getId()));
            for(UserDTO userDTO:localFaceSetDTO.getUsers()){
                userDTO.setLastModifiedDate(pushLocalFaceSet.getTime(localFaceSetDTO.getId().toString(), userDTO.getId().toString()));
            }
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, localFaceSetDTO.getId().toString()))
                .body(localFaceSetDTO);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "url.not.found", "分享失败")).body(null);
        }
    }

    /**
     * delete  /local-face-set/{id}/users/{userId}: 删除推送给自己的离线库.
     *
     */
    @DeleteMapping("/local-face-set/{id}/users/{userId}")
    @Timed
    public ResponseEntity<LocalFaceSetDTO> deleteLocalFaceSetShareUser(@PathVariable Long id, @PathVariable Long userId) {
        log.debug("REST request to delete user : id:{}, userId:{}", id, userId);
        LocalFaceSet localFaceSet = localFaceSetRepository.save(localFaceSetRepository.findOne(id).removeUser(userRepository.findOne(userId)));
        LocalFaceSetDTO result = localFaceSetMapper.localFaceSetToLocalFaceSetDTO(localFaceSet);
        result.setLocalFaces(localFaceRepository.findSizeByFaceSetId(result.getId()));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).body(result);
    }

    /**
     * delete  /local-face-set/{id}/users: 删除推送给自己的离线库.
     *
     */
    @DeleteMapping("/local-face-set/{id}/users")
    @Timed
    public ResponseEntity<LocalFaceSetDTO> deleteLocalFaceSetShareUser(@PathVariable Long id) {
        log.debug("REST request to delete user : id:{}", id);
        LocalFaceSet localFaceSet = localFaceSetRepository.save(localFaceSetRepository.findOne(id).
            removeUser(userRepository.findOne( userService.getUserWithAuthorities().getId())));
        LocalFaceSetDTO result = localFaceSetMapper.localFaceSetToLocalFaceSetDTO(localFaceSet);
        result.setLocalFaces(localFaceRepository.findSizeByFaceSetId(result.getId()));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).body(result);
    }

    /**
     * get  /local-face-set/activated-share: 分享给自己的离线库列表.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of localFaceSets in body
     *
     */
    @GetMapping("/local-face-set/activated-share")
    @Timed
    public ResponseEntity<List<LocalFaceSetDTO>> getActivatedShareLocalFaceSets(@ApiParam Pageable pageable) {
        Page<LocalFaceSet> page =localFaceSetRepository.findActivatedByCreatedByNotIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/local-face-set/activated-share");
        List<LocalFaceSetDTO> localFaceSetDTOs = localFaceSetMapper.localFaceSetsToLocalFaceSetDTOs(page.getContent());
        localFaceSetDTOs.forEach(localFaceSetDTO -> {
            localFaceSetDTO.setLastModifiedDate( pushLocalFaceSet.getTime(localFaceSetDTO.getId().toString(),
                userService.getUserWithAuthorities().getId().toString()));
            localFaceSetDTO.setLocalFaces(localFaceRepository.findSizeByFaceSetId(localFaceSetDTO.getId()));
        });
        return new ResponseEntity<>(localFaceSetDTOs, headers, HttpStatus.OK);
    }
}
