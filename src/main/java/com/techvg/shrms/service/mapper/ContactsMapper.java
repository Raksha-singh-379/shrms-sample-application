package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.Contacts;
import com.techvg.shrms.domain.Employee;
import com.techvg.shrms.service.dto.ContactsDTO;
import com.techvg.shrms.service.dto.EmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contacts} and its DTO {@link ContactsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContactsMapper extends EntityMapper<ContactsDTO, Contacts> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    ContactsDTO toDto(Contacts s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
