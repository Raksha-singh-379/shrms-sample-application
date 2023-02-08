package com.techvg.shrms.repository;

import com.techvg.shrms.domain.BanksDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BanksDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BanksDetailsRepository extends JpaRepository<BanksDetails, Long>, JpaSpecificationExecutor<BanksDetails> {}
