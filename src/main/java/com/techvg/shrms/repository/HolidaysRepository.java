package com.techvg.shrms.repository;

import com.techvg.shrms.domain.Holidays;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Holidays entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HolidaysRepository extends JpaRepository<Holidays, Long>, JpaSpecificationExecutor<Holidays> {}
