package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.Employee;
import com.techvg.shrms.domain.FamilyInfo;
import com.techvg.shrms.service.dto.EmployeeDTO;
import com.techvg.shrms.service.dto.FamilyInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FamilyInfo} and its DTO {@link FamilyInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface FamilyInfoMapper extends EntityMapper<FamilyInfoDTO, FamilyInfo> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    FamilyInfoDTO toDto(FamilyInfo s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
