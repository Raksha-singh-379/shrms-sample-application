package com.techvg.shrms.repository;

import com.techvg.shrms.domain.EmpSalaryInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmpSalaryInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpSalaryInfoRepository extends JpaRepository<EmpSalaryInfo, Long>, JpaSpecificationExecutor<EmpSalaryInfo> {}
