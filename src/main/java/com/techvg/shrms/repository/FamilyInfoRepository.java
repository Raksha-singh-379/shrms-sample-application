package com.techvg.shrms.repository;

import com.techvg.shrms.domain.FamilyInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FamilyInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamilyInfoRepository extends JpaRepository<FamilyInfo, Long>, JpaSpecificationExecutor<FamilyInfo> {}
