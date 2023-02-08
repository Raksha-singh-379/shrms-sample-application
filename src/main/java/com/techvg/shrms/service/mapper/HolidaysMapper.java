package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.Company;
import com.techvg.shrms.domain.Holidays;
import com.techvg.shrms.service.dto.CompanyDTO;
import com.techvg.shrms.service.dto.HolidaysDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Holidays} and its DTO {@link HolidaysDTO}.
 */
@Mapper(componentModel = "spring")
public interface HolidaysMapper extends EntityMapper<HolidaysDTO, Holidays> {
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    HolidaysDTO toDto(Holidays s);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);
}
