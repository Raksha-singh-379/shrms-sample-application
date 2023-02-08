package com.techvg.shrms.service;

import com.techvg.shrms.domain.ProjectTeams;
import com.techvg.shrms.repository.ProjectTeamsRepository;
import com.techvg.shrms.service.dto.ProjectTeamsDTO;
import com.techvg.shrms.service.mapper.ProjectTeamsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProjectTeams}.
 */
@Service
@Transactional
public class ProjectTeamsService {

    private final Logger log = LoggerFactory.getLogger(ProjectTeamsService.class);

    private final ProjectTeamsRepository projectTeamsRepository;

    private final ProjectTeamsMapper projectTeamsMapper;

    public ProjectTeamsService(ProjectTeamsRepository projectTeamsRepository, ProjectTeamsMapper projectTeamsMapper) {
        this.projectTeamsRepository = projectTeamsRepository;
        this.projectTeamsMapper = projectTeamsMapper;
    }

    /**
     * Save a projectTeams.
     *
     * @param projectTeamsDTO the entity to save.
     * @return the persisted entity.
     */
    public ProjectTeamsDTO save(ProjectTeamsDTO projectTeamsDTO) {
        log.debug("Request to save ProjectTeams : {}", projectTeamsDTO);
        ProjectTeams projectTeams = projectTeamsMapper.toEntity(projectTeamsDTO);
        projectTeams = projectTeamsRepository.save(projectTeams);
        return projectTeamsMapper.toDto(projectTeams);
    }

    /**
     * Update a projectTeams.
     *
     * @param projectTeamsDTO the entity to save.
     * @return the persisted entity.
     */
    public ProjectTeamsDTO update(ProjectTeamsDTO projectTeamsDTO) {
        log.debug("Request to update ProjectTeams : {}", projectTeamsDTO);
        ProjectTeams projectTeams = projectTeamsMapper.toEntity(projectTeamsDTO);
        projectTeams = projectTeamsRepository.save(projectTeams);
        return projectTeamsMapper.toDto(projectTeams);
    }

    /**
     * Partially update a projectTeams.
     *
     * @param projectTeamsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProjectTeamsDTO> partialUpdate(ProjectTeamsDTO projectTeamsDTO) {
        log.debug("Request to partially update ProjectTeams : {}", projectTeamsDTO);

        return projectTeamsRepository
            .findById(projectTeamsDTO.getId())
            .map(existingProjectTeams -> {
                projectTeamsMapper.partialUpdate(existingProjectTeams, projectTeamsDTO);

                return existingProjectTeams;
            })
            .map(projectTeamsRepository::save)
            .map(projectTeamsMapper::toDto);
    }

    /**
     * Get all the projectTeams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTeamsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectTeams");
        return projectTeamsRepository.findAll(pageable).map(projectTeamsMapper::toDto);
    }

    /**
     * Get one projectTeams by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectTeamsDTO> findOne(Long id) {
        log.debug("Request to get ProjectTeams : {}", id);
        return projectTeamsRepository.findById(id).map(projectTeamsMapper::toDto);
    }

    /**
     * Delete the projectTeams by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectTeams : {}", id);
        projectTeamsRepository.deleteById(id);
    }
}
