package com.techvg.shrms.repository;

import com.techvg.shrms.domain.LeaveTranscation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveTranscation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveTranscationRepository extends JpaRepository<LeaveTranscation, Long>, JpaSpecificationExecutor<LeaveTranscation> {}
