package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.Address;
import com.techvg.shrms.domain.City;
import com.techvg.shrms.domain.District;
import com.techvg.shrms.domain.Employee;
import com.techvg.shrms.domain.State;
import com.techvg.shrms.domain.Taluka;
import com.techvg.shrms.service.dto.AddressDTO;
import com.techvg.shrms.service.dto.CityDTO;
import com.techvg.shrms.service.dto.DistrictDTO;
import com.techvg.shrms.service.dto.EmployeeDTO;
import com.techvg.shrms.service.dto.StateDTO;
import com.techvg.shrms.service.dto.TalukaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    @Mapping(target = "state", source = "state", qualifiedByName = "stateId")
    @Mapping(target = "district", source = "district", qualifiedByName = "districtId")
    @Mapping(target = "taluka", source = "taluka", qualifiedByName = "talukaId")
    @Mapping(target = "city", source = "city", qualifiedByName = "cityId")
    AddressDTO toDto(Address s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);

    @Named("stateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StateDTO toDtoStateId(State state);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoDistrictId(District district);

    @Named("talukaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TalukaDTO toDtoTalukaId(Taluka taluka);

    @Named("cityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CityDTO toDtoCityId(City city);
}
