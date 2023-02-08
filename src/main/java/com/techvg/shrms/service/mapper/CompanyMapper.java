package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.Address;
import com.techvg.shrms.domain.Company;
import com.techvg.shrms.service.dto.AddressDTO;
import com.techvg.shrms.service.dto.CompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Company} and its DTO {@link CompanyDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    CompanyDTO toDto(Company s);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);
}
