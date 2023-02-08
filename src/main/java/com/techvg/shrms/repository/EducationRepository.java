package com.techvg.shrms.repository;

import com.techvg.shrms.domain.Education;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Education entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EducationRepository extends JpaRepository<Education, Long>, JpaSpecificationExecutor<Education> {}
