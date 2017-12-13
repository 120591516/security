package com.llvision.security.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.llvision.security.domain.LocalFace;

import com.llvision.security.domain.LocalFaceSet;
import com.llvision.security.repository.LocalFaceRepository;
import com.llvision.security.repository.LocalFaceSetRepository;
import com.llvision.security.repository.UserRepository;
import com.llvision.security.security.SecurityUtils;
import com.llvision.security.service.storage.StorageException;
import com.llvision.security.service.storage.StorageService;
import com.llvision.security.service.storage.UploadFileDTO;
import com.llvision.security.web.rest.errors.ErrorForbidden;
import com.llvision.security.web.rest.util.HeaderUtil;
import com.llvision.security.web.rest.util.PaginationUtil;
import com.llvision.security.service.dto.LocalFaceDTO;
import com.llvision.security.service.mapper.LocalFaceMapper;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing LocalFace.
 */
@RestController
@RequestMapping("/api")
public class LocalFaceResource {

    private final Logger log = LoggerFactory.getLogger(LocalFaceResource.class);

    private final String BASE_DIRECTORY = "local_face";
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    private static final String ENTITY_NAME = "localFace";

    private final LocalFaceRepository localFaceRepository;

    private final LocalFaceMapper localFaceMapper;

    private final LocalFaceSetRepository localFaceSetRepository;

    private final UserRepository userRepository;

    private final StorageService storageService;

    public LocalFaceResource(LocalFaceRepository localFaceRepository,
                             LocalFaceMapper localFaceMapper,
                             LocalFaceSetRepository localFaceSetRepository,
                             UserRepository userRepository,
                             StorageService storageService) {
        this.localFaceRepository = localFaceRepository;
        this.localFaceMapper = localFaceMapper;
        this.localFaceSetRepository = localFaceSetRepository;
        this.userRepository = userRepository;
        this.storageService = storageService;
    }

    /**
     * POST  /local-faces : Create a new localFace.
     *
     * @param localFaceDTO the localFaceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new localFaceDTO, or with status 400 (Bad Request) if the localFace has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/local-faces")
    @Timed
    public ResponseEntity<LocalFaceDTO> createLocalFace(@Valid @RequestBody LocalFaceDTO localFaceDTO) throws URISyntaxException {
        log.debug("REST request to save LocalFace : {}", localFaceDTO);
        if (localFaceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new localFace cannot already have an ID")).body(null);
        }
        Long faceSetId = localFaceDTO.getFaceSetId();
        findAndValidateLocalFaceSet(faceSetId);
        localFaceDTO.setFaceSetId(faceSetId);
        ZonedDateTime now = ZonedDateTime.now();
        localFaceDTO.setCreatedDate(now);
        localFaceDTO.setLastModifiedDate(now);
        LocalFace localFace = localFaceMapper.localFaceDTOToLocalFace(localFaceDTO);
        localFace = localFaceRepository.save(localFace);
        LocalFaceDTO result = localFaceMapper.localFaceToLocalFaceDTO(localFace);
        return ResponseEntity.created(new URI("/api/local-face-sets/" + faceSetId + "/local-faces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /local-faces : Updates an existing localFace.
     *
     * @param localFaceDTO the localFaceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated localFaceDTO,
     * or with status 400 (Bad Request) if the localFaceDTO is not valid,
     * or with status 500 (Internal Server Error) if the localFaceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/local-faces")
    @Timed
    public ResponseEntity<LocalFaceDTO> updateLocalFace(@Valid @RequestBody LocalFaceDTO localFaceDTO) throws URISyntaxException {
        log.debug("REST request to update LocalFace : {}", localFaceDTO);
        if (localFaceDTO.getId() == null) {
            return createLocalFace(localFaceDTO);
        }
        Long faceSetId = localFaceDTO.getFaceSetId();
        findAndValidateLocalFaceSet(faceSetId);
        LocalFace localFace = localFaceMapper.localFaceDTOToLocalFace(localFaceDTO);
        LocalFace localFaceBefore = localFaceRepository.findOne(localFaceDTO.getId());
        if(localFaceBefore != null && localFaceDTO.getImage() != null && !localFaceDTO.getImage().equals(localFaceBefore.getImage())){
            localFace.setHash(null);
            localFace.setModel(null);
        }
        localFace.setLastModifiedDate(ZonedDateTime.now());
        localFace = localFaceRepository.save(localFace);
        LocalFaceDTO result = localFaceMapper.localFaceToLocalFaceDTO(localFace);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, localFaceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /local-face-sets/:faceSetId/local-faces : get all the localFaces for a localFaceSet.
     *
     * @param faceSetId face set id
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of localFaces in body
     */
    @GetMapping("/local-face-sets/{faceSetId}/local-faces")
    @Timed
    public ResponseEntity<List<LocalFaceDTO>> getAllLocalFaces(@PathVariable Long faceSetId, @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of LocalFaces");
        findAndValidateLocalFaceSet(faceSetId);
        Page<LocalFace> page = localFaceRepository.findAllByFaceSetId(faceSetId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/local-face-sets/" + faceSetId + "/local-faces");
        return new ResponseEntity<>(localFaceMapper.localFacesToLocalFaceDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /local-faces/:id : get the "id" localFace.
     *
     * @param id the id of the localFaceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the localFaceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/local-faces/{id}")
    @Timed
    public ResponseEntity<LocalFaceDTO> getLocalFace(@PathVariable Long id) {
        log.debug("REST request to get LocalFace : {}", id);
        LocalFace localFace = localFaceRepository.findOne(id);
        findAndValidateLocalFaceSet(localFace.getFaceSet().getId());
        LocalFaceDTO localFaceDTO = localFaceMapper.localFaceToLocalFaceDTO(localFace);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(localFaceDTO));
    }

    /**
     * DELETE  /local-faces/:id : delete the "id" localFace.
     *
     * @param id the id of the localFaceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/local-faces/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocalFace(@PathVariable Long id) {
        log.debug("REST request to delete LocalFace : {}", id);
        LocalFace localFace = localFaceRepository.findOne(id);
        findAndValidateLocalFaceSet(localFace.getFaceSet().getId());
        localFaceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * POST  /local-face-sets/:faceSetId/local-faces/image : Upload a new face image.
     *
     * @param faceSetId face set id
     * @param file uploaded file
     * @return the ResponseEntity with status 201 (Created) and with body the new file url, or with status 400 (Bad Request) if the localFace has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/local-face-sets/{faceSetId}/local-faces/images")
    @Timed
    public ResponseEntity<UploadFileDTO> createLocalFaceImage(@PathVariable Long faceSetId, @RequestParam("file") MultipartFile file) throws URISyntaxException {
        log.debug("REST request to upload face image : {}", file.getOriginalFilename());
        findAndValidateLocalFaceSet(faceSetId);

        if (!("image/jpeg".equals(file.getContentType()) || "image/png".equals(file.getContentType()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        String path = generateFaceImageFileName(faceSetId, file);
        try {
            storageService.store(file.getBytes(), path);
        } catch (StorageException e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
        UploadFileDTO fileDTO = new UploadFileDTO();
        fileDTO.setPath(path);
        fileDTO.setSize(file.getSize());
        fileDTO.setContentType(file.getContentType());
        return ResponseEntity.ok(fileDTO);
    }

    public String generateFaceImageFileName(Long faceSetId, MultipartFile file) {
        ZonedDateTime currentTime = ZonedDateTime.now();
        int idx = file.getOriginalFilename().lastIndexOf(".");
        String suffix = "";
        if (idx >= 0) {
            suffix = file.getOriginalFilename().substring(idx);
        }
        return Paths.get(BASE_DIRECTORY,
            "IMG_" + currentTime.format(DATETIME_FORMATTER) + "_" + SecurityUtils.getCurrentUserLogin()
                + "_" + faceSetId + suffix).toString();
    }

    private LocalFaceSet findAndValidateLocalFaceSet(Long faceSetId) {
        LocalFaceSet localFaceSet = localFaceSetRepository.findOneWithEagerRelationships(faceSetId);
        if (localFaceSet == null ||
            !(Objects.equals(localFaceSet.getCreatedBy().getLogin(), SecurityUtils.getCurrentUserLogin()) ||
                localFaceSet.getUsers().contains(
                    userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get()))) {
            throw new ErrorForbidden();
        }
        return localFaceSet;

    }
}
