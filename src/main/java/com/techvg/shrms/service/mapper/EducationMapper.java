package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.Education;
import com.techvg.shrms.domain.Employee;
import com.techvg.shrms.service.dto.EducationDTO;
import com.techvg.shrms.service.dto.EmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Education} and its DTO {@link EducationDTO}.
 */
@Mapper(componentModel = "spring")
public interface EducationMapper extends EntityMapper<EducationDTO, Education> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    EducationDTO toDto(Education s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
