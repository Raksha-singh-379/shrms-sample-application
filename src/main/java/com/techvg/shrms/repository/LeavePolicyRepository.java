package com.techvg.shrms.repository;

import com.techvg.shrms.domain.LeavePolicy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeavePolicy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeavePolicyRepository extends JpaRepository<LeavePolicy, Long>, JpaSpecificationExecutor<LeavePolicy> {}
