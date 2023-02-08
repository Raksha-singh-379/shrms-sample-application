package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.Company;
import com.techvg.shrms.domain.Region;
import com.techvg.shrms.service.dto.CompanyDTO;
import com.techvg.shrms.service.dto.RegionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Region} and its DTO {@link RegionDTO}.
 */
@Mapper(componentModel = "spring")
public interface RegionMapper extends EntityMapper<RegionDTO, Region> {
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    RegionDTO toDto(Region s);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);
}
