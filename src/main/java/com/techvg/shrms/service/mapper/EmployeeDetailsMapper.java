package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.Employee;
import com.techvg.shrms.domain.EmployeeDetails;
import com.techvg.shrms.service.dto.EmployeeDTO;
import com.techvg.shrms.service.dto.EmployeeDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeDetails} and its DTO {@link EmployeeDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeDetailsMapper extends EntityMapper<EmployeeDetailsDTO, EmployeeDetails> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    EmployeeDetailsDTO toDto(EmployeeDetails s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
