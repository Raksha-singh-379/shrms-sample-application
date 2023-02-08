package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.Employee;
import com.techvg.shrms.domain.WorkExperience;
import com.techvg.shrms.service.dto.EmployeeDTO;
import com.techvg.shrms.service.dto.WorkExperienceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkExperience} and its DTO {@link WorkExperienceDTO}.
 */
@Mapper(componentModel = "spring")
public interface WorkExperienceMapper extends EntityMapper<WorkExperienceDTO, WorkExperience> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    WorkExperienceDTO toDto(WorkExperience s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
