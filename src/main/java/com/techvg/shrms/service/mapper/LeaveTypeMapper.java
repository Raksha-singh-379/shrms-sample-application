package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.LeaveType;
import com.techvg.shrms.service.dto.LeaveTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LeaveType} and its DTO {@link LeaveTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface LeaveTypeMapper extends EntityMapper<LeaveTypeDTO, LeaveType> {}
