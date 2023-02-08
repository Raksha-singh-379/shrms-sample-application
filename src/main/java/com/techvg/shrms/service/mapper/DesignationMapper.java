package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.Company;
import com.techvg.shrms.domain.Designation;
import com.techvg.shrms.service.dto.CompanyDTO;
import com.techvg.shrms.service.dto.DesignationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Designation} and its DTO {@link DesignationDTO}.
 */
@Mapper(componentModel = "spring")
public interface DesignationMapper extends EntityMapper<DesignationDTO, Designation> {
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    DesignationDTO toDto(Designation s);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);
}
