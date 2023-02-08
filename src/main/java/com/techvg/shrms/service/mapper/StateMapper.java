package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.State;
import com.techvg.shrms.service.dto.StateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link State} and its DTO {@link StateDTO}.
 */
@Mapper(componentModel = "spring")
public interface StateMapper extends EntityMapper<StateDTO, State> {}
