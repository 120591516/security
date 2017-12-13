package com.llvision.security.repository;

import com.llvision.security.domain.RecognitionRecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the RecognitionRecord entity.
 */
@SuppressWarnings("unused")
public interface RecognitionRecordRepository extends JpaRepository<RecognitionRecord,Long> {

    @Query("select recognitionRecord from RecognitionRecord recognitionRecord where recognitionRecord.createdBy.login = ?#{principal.username}")
    List<RecognitionRecord> findByCreatedByIsCurrentUser();

    @Query("select recognitionRecord from RecognitionRecord recognitionRecord where recognitionRecord.lastModifiedBy.login = ?#{principal.username}")
    List<RecognitionRecord> findByLastModifiedByIsCurrentUser();

    @Query("select recognitionRecord from RecognitionRecord recognitionRecord where (recognitionRecord.deleted = FALSE OR recognitionRecord.deleted IS NULL) AND recognitionRecord.workRecord.id = :workRecordId")
    List<RecognitionRecord> findByWorkRecordId(@Param("workRecordId") Long workRecordId);

    @Query("select recognitionRecord from RecognitionRecord recognitionRecord where (recognitionRecord.deleted = FALSE OR recognitionRecord.deleted IS NULL) AND recognitionRecord.workRecord.id = :workRecordId")
    Page<RecognitionRecord> findByWorkRecordId(@Param("workRecordId") Long workRecordId, Pageable pageable);

}
