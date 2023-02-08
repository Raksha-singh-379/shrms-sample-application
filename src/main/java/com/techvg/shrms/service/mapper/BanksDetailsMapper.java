package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.BanksDetails;
import com.techvg.shrms.domain.Employee;
import com.techvg.shrms.service.dto.BanksDetailsDTO;
import com.techvg.shrms.service.dto.EmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BanksDetails} and its DTO {@link BanksDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface BanksDetailsMapper extends EntityMapper<BanksDetailsDTO, BanksDetails> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    BanksDetailsDTO toDto(BanksDetails s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
