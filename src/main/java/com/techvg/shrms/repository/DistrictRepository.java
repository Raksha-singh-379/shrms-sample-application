package com.techvg.shrms.repository;

import com.techvg.shrms.domain.District;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the District entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DistrictRepository extends JpaRepository<District, Long>, JpaSpecificationExecutor<District> {}
