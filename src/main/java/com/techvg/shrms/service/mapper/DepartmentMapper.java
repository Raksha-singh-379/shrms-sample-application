package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.Company;
import com.techvg.shrms.domain.Department;
import com.techvg.shrms.service.dto.CompanyDTO;
import com.techvg.shrms.service.dto.DepartmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Department} and its DTO {@link DepartmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    DepartmentDTO toDto(Department s);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);
}
