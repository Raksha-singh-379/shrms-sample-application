package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.City;
import com.techvg.shrms.service.dto.CityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link City} and its DTO {@link CityDTO}.
 */
@Mapper(componentModel = "spring")
public interface CityMapper extends EntityMapper<CityDTO, City> {}
