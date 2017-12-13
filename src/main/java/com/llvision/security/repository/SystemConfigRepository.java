package com.llvision.security.repository;

import com.llvision.security.domain.SystemConfig;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SystemConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long> {

    SystemConfig findOneByKey(String name);
}
