package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.Address;
import com.techvg.shrms.domain.Branch;
import com.techvg.shrms.domain.Company;
import com.techvg.shrms.domain.Region;
import com.techvg.shrms.service.dto.AddressDTO;
import com.techvg.shrms.service.dto.BranchDTO;
import com.techvg.shrms.service.dto.CompanyDTO;
import com.techvg.shrms.service.dto.RegionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Branch} and its DTO {@link BranchDTO}.
 */
@Mapper(componentModel = "spring")
public interface BranchMapper extends EntityMapper<BranchDTO, Branch> {
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    @Mapping(target = "branch", source = "branch", qualifiedByName = "regionId")
    BranchDTO toDto(Branch s);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);

    @Named("regionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RegionDTO toDtoRegionId(Region region);
}
