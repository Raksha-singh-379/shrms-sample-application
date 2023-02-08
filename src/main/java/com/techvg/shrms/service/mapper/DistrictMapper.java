package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.District;
import com.techvg.shrms.service.dto.DistrictDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link District} and its DTO {@link DistrictDTO}.
 */
@Mapper(componentModel = "spring")
public interface DistrictMapper extends EntityMapper<DistrictDTO, District> {}
