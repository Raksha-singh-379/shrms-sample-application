package com.techvg.shrms.service.mapper;

import com.techvg.shrms.domain.ProjectTeams;
import com.techvg.shrms.service.dto.ProjectTeamsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectTeams} and its DTO {@link ProjectTeamsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectTeamsMapper extends EntityMapper<ProjectTeamsDTO, ProjectTeams> {}
