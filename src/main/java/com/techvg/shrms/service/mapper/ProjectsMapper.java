package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.Projects;
import com.techvg.shrms.service.dto.ProjectsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Projects} and its DTO {@link ProjectsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectsMapper extends EntityMapper<ProjectsDTO, Projects> {}
