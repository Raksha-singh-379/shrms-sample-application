package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.Employee;
import com.techvg.shrms.domain.LeaveApplication;
import com.techvg.shrms.domain.LeavePolicy;
import com.techvg.shrms.service.dto.EmployeeDTO;
import com.techvg.shrms.service.dto.LeaveApplicationDTO;
import com.techvg.shrms.service.dto.LeavePolicyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LeaveApplication} and its DTO {@link LeaveApplicationDTO}.
 */
@Mapper(componentModel = "spring")
public interface LeaveApplicationMapper extends EntityMapper<LeaveApplicationDTO, LeaveApplication> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    @Mapping(target = "leavePolicy", source = "leavePolicy", qualifiedByName = "leavePolicyId")
    LeaveApplicationDTO toDto(LeaveApplication s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);

    @Named("leavePolicyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LeavePolicyDTO toDtoLeavePolicyId(LeavePolicy leavePolicy);
}
