package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.Taluka;
import com.techvg.shrms.service.dto.TalukaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Taluka} and its DTO {@link TalukaDTO}.
 */
@Mapper(componentModel = "spring")
public interface TalukaMapper extends EntityMapper<TalukaDTO, Taluka> {}
