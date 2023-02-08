package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.LeaveTranscation;
import com.techvg.shrms.service.dto.LeaveTranscationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LeaveTranscation} and its DTO {@link LeaveTranscationDTO}.
 */
@Mapper(componentModel = "spring")
public interface LeaveTranscationMapper extends EntityMapper<LeaveTranscationDTO, LeaveTranscation> {}
