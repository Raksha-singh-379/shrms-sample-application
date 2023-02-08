package com.techvg.shrms.repository;

import com.techvg.shrms.domain.LeaveType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long>, JpaSpecificationExecutor<LeaveType> {}
