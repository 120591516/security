package com.llvision.security.service.mapper;

import com.llvision.security.domain.*;
import com.llvision.security.service.dto.SystemConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SystemConfig and its DTO SystemConfigDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SystemConfigMapper extends EntityMapper<SystemConfigDTO, SystemConfig> {

    

    

    default SystemConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setId(id);
        return systemConfig;
    }
}
