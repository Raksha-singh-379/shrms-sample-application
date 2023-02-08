package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.SalarySettings;
import com.techvg.shrms.service.dto.SalarySettingsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SalarySettings} and its DTO {@link SalarySettingsDTO}.
 */
@Mapper(componentModel = "spring")
public interface SalarySettingsMapper extends EntityMapper<SalarySettingsDTO, SalarySettings> {}
