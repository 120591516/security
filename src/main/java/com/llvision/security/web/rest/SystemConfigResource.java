package com.llvision.security.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.llvision.security.domain.SystemConfig;

import com.llvision.security.plugin.service.PluginServiceFactory;
import com.llvision.security.plugin.service.SystemConfigService;
import com.llvision.security.plugin.service.SystemConfigUtil;
import com.llvision.security.repository.SystemConfigRepository;
import com.llvision.security.security.AuthoritiesConstants;
import com.llvision.security.web.rest.util.HeaderUtil;
import com.llvision.security.web.rest.util.PaginationUtil;
import com.llvision.security.service.dto.SystemConfigDTO;
import com.llvision.security.service.mapper.SystemConfigMapper;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SystemConfig.
 */
@RestController
@RequestMapping("/api")
public class SystemConfigResource {

    private final Logger log = LoggerFactory.getLogger(SystemConfigResource.class);

    private static final String ENTITY_NAME = "systemConfig";

    private final SystemConfigRepository systemConfigRepository;

    private final SystemConfigMapper systemConfigMapper;

    private final SystemConfigUtil systemConfigUtil;

    private final PluginServiceFactory pluginServiceFactory;

    public SystemConfigResource(
        SystemConfigRepository systemConfigRepository,
        SystemConfigMapper systemConfigMapper,
        SystemConfigUtil systemConfigUtil,
        PluginServiceFactory pluginServiceFactory) {
        this.systemConfigRepository = systemConfigRepository;
        this.systemConfigMapper = systemConfigMapper;
        this.systemConfigUtil = systemConfigUtil;
        this.pluginServiceFactory = pluginServiceFactory;
    }

    /**
     * PUT  /system-configs : 更新已有的systemConfig。仅系统管理员（ADMIN）可以访问
     *
     * @param systemConfigDTO 需要更新的systemConfigDTO
     * @return the ResponseEntity with status 200 (OK) and with body the updated systemConfigDTO,
     * or with status 400 (Bad Request) if the systemConfigDTO is not valid,
     * or with status 500 (Internal Server Error) if the systemConfigDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/system-configs")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<SystemConfigDTO> updateSystemConfig(@Valid @RequestBody SystemConfigDTO systemConfigDTO) throws URISyntaxException {
        log.debug("REST request to update SystemConfig : {}", systemConfigDTO);
        if (systemConfigDTO.getId() == null) {
            return ResponseEntity.notFound().build();
        }
        SystemConfig systemConfig = systemConfigRepository.findOne(systemConfigDTO.getId());
        if (systemConfig == null) {
            return ResponseEntity.notFound().build();
        }
        // 只更新value
        systemConfig.setValue(systemConfigDTO.getValue());
        systemConfig = systemConfigRepository.save(systemConfig);
        SystemConfigDTO result = systemConfigMapper.toDto(systemConfig);
        // 提示插件系统配置已经更新
        for (SystemConfigService service : pluginServiceFactory.getSystemConfigServices()) {
            service.onValueChanged(systemConfig.getKey());
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, systemConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /system-configs : 获取所有的系统设置。仅系统管理员（ADMIN）可以访问
     *
     * @param pageable 分页和排序信息
     * @return 带有200（OK）状态码的ResponseEntity。Body里包含systemConfig列表
     */
    @GetMapping("/system-configs")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<List<SystemConfigDTO>> getAllSystemConfigs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SystemConfigs");
        // 如果系统配置为空，初始化
        if (systemConfigRepository.count() <= 0) {
            for (SystemConfigService service : pluginServiceFactory.getSystemConfigServices()) {
                service.initConfig();
            }
        }
        Page<SystemConfig> page = systemConfigRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/system-configs");
        return new ResponseEntity<>(systemConfigMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /system-configs/:id : 获取指定id的系统设置。仅系统管理员（ADMIN）可以访问.
     *
     * @param id 需要返回的systemConfigDTO的id
     * @return 成功则返回带有200（OK）状态码的ResponseEntity。Body里包含systemConfigDTO。否则返回404（NOT FOUND）状态码
     */
    @GetMapping("/system-configs/{id}")
    @Timed
    public ResponseEntity<SystemConfigDTO> getSystemConfig(@PathVariable Long id) {
        log.debug("REST request to get SystemConfig : {}", id);
        SystemConfig systemConfig = systemConfigRepository.findOne(id);
        SystemConfigDTO systemConfigDTO = systemConfigMapper.toDto(systemConfig);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(systemConfigDTO));
    }
}
