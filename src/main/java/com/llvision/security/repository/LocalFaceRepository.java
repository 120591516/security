package com.llvision.security.repository;

import com.llvision.security.domain.LocalFace;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LocalFace entity.
 */
@SuppressWarnings("unused")
public interface LocalFaceRepository extends JpaRepository<LocalFace,Long> {

    Page<LocalFace> findAllByFaceSetId(Long faceSetId, Pageable pageable);

    @Query("select count(*) from LocalFace where faceSet.id = ?1")
    Integer findSizeByFaceSetId(Long faceSetId);
}
