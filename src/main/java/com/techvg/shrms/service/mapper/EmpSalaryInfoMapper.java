package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.EmpSalaryInfo;
import com.techvg.shrms.domain.Employee;
import com.techvg.shrms.service.dto.EmpSalaryInfoDTO;
import com.techvg.shrms.service.dto.EmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmpSalaryInfo} and its DTO {@link EmpSalaryInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmpSalaryInfoMapper extends EntityMapper<EmpSalaryInfoDTO, EmpSalaryInfo> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    EmpSalaryInfoDTO toDto(EmpSalaryInfo s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
