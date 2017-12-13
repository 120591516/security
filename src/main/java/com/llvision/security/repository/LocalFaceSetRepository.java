package com.llvision.security.repository;

import com.llvision.security.domain.LocalFaceSet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the LocalFaceSet entity.
 */
@SuppressWarnings("unused")
public interface LocalFaceSetRepository extends JpaRepository<LocalFaceSet,Long> {

    @Query("select localFaceSet from LocalFaceSet localFaceSet " +
        "where " +
        "localFaceSet.createdBy.login = ?#{principal.username} AND " +
        "(localFaceSet.deleted IS NULL OR localFaceSet = false)")
    Page<LocalFaceSet> findByCreatedByIsCurrentUser(Pageable pageable);

    @Query("select localFaceSet from LocalFaceSet localFaceSet " +
        "where localFaceSet.activated IS TRUE AND " +
        "localFaceSet.createdBy.login = ?#{principal.username} AND " +
        "(localFaceSet.deleted IS NULL OR localFaceSet = false)")
    Page<LocalFaceSet> findActivatedByCreatedByIsCurrentUser(Pageable pageable);

    @Query("select localFaceSet from LocalFaceSet localFaceSet where localFaceSet.lastModifiedBy.login = ?#{principal.username}")
    List<LocalFaceSet> findByLastModifiedByIsCurrentUser();

    @Query("select distinct localFaceSet from LocalFaceSet localFaceSet left join fetch localFaceSet.users")
    List<LocalFaceSet> findAllWithEagerRelationships();

    @Query("select localFaceSet from LocalFaceSet localFaceSet left join fetch localFaceSet.users where " +
        "localFaceSet.id =:id AND (localFaceSet.deleted IS NULL OR localFaceSet = false)")
    LocalFaceSet findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select localFaceSet from LocalFaceSet localFaceSet " +
        "inner join localFaceSet.users user " +
        "where localFaceSet.activated IS TRUE AND " +
        "(localFaceSet.deleted IS NULL OR localFaceSet = false) AND "+
        "user.login = ?#{principal.username}")
    Page<LocalFaceSet> findActivatedByCreatedByNotIsCurrentUser(Pageable pageable);

    @Query("select localFaceSet from LocalFaceSet localFaceSet left join fetch localFaceSet.users where " +
        "localFaceSet.name =:name AND (localFaceSet.deleted IS NULL OR localFaceSet = false)")
    LocalFaceSet findOneWithEagerRelationships(@Param("name") String name);

}
