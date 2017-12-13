package com.llvision.security.repository;

import com.llvision.security.domain.WorkRecord;

import com.llvision.security.domain.WorkRecordStat;
import com.llvision.security.domain.WorkRecordUserStat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the WorkRecord entity.
 */
@SuppressWarnings("unused")
public interface WorkRecordRepository extends JpaRepository<WorkRecord,Long> {

    @Query("select workRecord from WorkRecord workRecord where (workRecord.deleted = FALSE OR workRecord.deleted IS NULL) AND workRecord.createdBy.login = ?#{principal.username} AND workRecord.type = :type")
    Page<WorkRecord> findByCreatedByIsCurrentUser(@Param("type") int type, Pageable pageable);

    @Query("select workRecord from WorkRecord workRecord where (workRecord.deleted = FALSE OR workRecord.deleted IS NULL) AND workRecord.lastModifiedBy.login = ?#{principal.username}")
    List<WorkRecord> findByLastModifiedByIsCurrentUser();

    @Query("select workRecord from WorkRecord workRecord where (workRecord.deleted = FALSE OR workRecord.deleted IS NULL) AND workRecord.type = :type")
    Page<WorkRecord> findAll(@Param("type") int type, Pageable pageable);

    @Query("select NEW com.llvision.security.domain.WorkRecordUserStat(" +
        "workRecord.createdBy, " +
        "COUNT(workRecord), " +
        "COUNT(CASE workRecord.attention WHEN TRUE THEN 1 ELSE NULL END), " +
        "COUNT(CASE workRecord.report WHEN TRUE THEN 1 ELSE NULL END)) " +
        "from WorkRecord workRecord " +
        "where " +
        "(workRecord.deleted = FALSE OR workRecord.deleted IS NULL) AND " +
        "workRecord.type = :type " +
        "AND " +
        "workRecord.createdDate >= :startDate AND workRecord.createdDate < :endDate " +
        "GROUP BY workRecord.createdBy " +
        "ORDER BY COUNT(workRecord) DESC")
    Page<WorkRecordUserStat> getTopPerformingUserStats(
        @Param("type") int type,
        @Param("startDate") ZonedDateTime startDate,
        @Param("endDate") ZonedDateTime endDate,
        Pageable pageable);

    @Query("select NEW com.llvision.security.domain.WorkRecordStat(" +
        "COUNT(workRecord), " +
        "COUNT(CASE workRecord.attention WHEN TRUE THEN 1 ELSE NULL END), " +
        "COUNT(CASE workRecord.report WHEN TRUE THEN 1 ELSE NULL END)) " +
        "from WorkRecord workRecord where " +
        "(workRecord.deleted = FALSE OR workRecord.deleted IS NULL) AND " +
        "workRecord.type = :type AND " +
        "workRecord.createdDate >= :startDate AND workRecord.createdDate < :endDate")
    List<WorkRecordStat> getAllWorkRecordStats(
        @Param("type") int type, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);

    @Query("select NEW com.llvision.security.domain.WorkRecordStat(" +
        "COUNT(workRecord), " +
        "COUNT(CASE workRecord.attention WHEN TRUE THEN 1 ELSE NULL END), " +
        "COUNT(CASE workRecord.report WHEN TRUE THEN 1 ELSE NULL END)) " +
        "from WorkRecord workRecord where " +
        "(workRecord.deleted = FALSE OR workRecord.deleted IS NULL) AND " +
        "workRecord.createdBy.login = ?#{principal.username} AND " +
        "workRecord.type = :type AND " +
        "workRecord.createdDate >= :startDate AND workRecord.createdDate < :endDate")
    List<WorkRecordStat> getWorkRecordStatsCreatedByCurrentUser(
        @Param("type") int type, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);
}
