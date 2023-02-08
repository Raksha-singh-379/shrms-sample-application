package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.LeavePolicy;
import com.techvg.shrms.service.dto.LeavePolicyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LeavePolicy} and its DTO {@link LeavePolicyDTO}.
 */
@Mapper(componentModel = "spring")
public interface LeavePolicyMapper extends EntityMapper<LeavePolicyDTO, LeavePolicy> {}
