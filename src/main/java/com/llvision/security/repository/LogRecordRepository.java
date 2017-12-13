package com.llvision.security.repository;

import com.llvision.security.domain.LogRecord;

import com.llvision.security.domain.RecognitionRecord;
import com.llvision.security.domain.WorkRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the LogRecord entity.
 */
@SuppressWarnings("unused")
public interface LogRecordRepository extends JpaRepository<LogRecord,Long> {

    @Query("select logRecord from LogRecord logRecord where (logRecord.deleted = FALSE OR logRecord.deleted IS NULL) and logRecord.createdBy.login = ?#{principal.username} and logRecord.workRecord.id = :workRecordId")
    Page<LogRecord> findByCreatedByIsCurrentUser(@Param("workRecordId") Long workRecordId, Pageable pageable);

    @Query("select logRecord from LogRecord logRecord where (logRecord.deleted = FALSE OR logRecord.deleted IS NULL) and logRecord.workRecord.id = :workRecordId ")
    Page<LogRecord> findAllByWorkRecordId(@Param("workRecordId") Long workRecordId, Pageable pageable);

}
