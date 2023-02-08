package com.techvg.shrms.service;

import com.techvg.shrms.domain.WorkExperience;
import com.techvg.shrms.repository.WorkExperienceRepository;
import com.techvg.shrms.service.dto.WorkExperienceDTO;
import com.techvg.shrms.service.mapper.WorkExperienceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WorkExperience}.
 */
@Service
@Transactional
public class WorkExperienceService {

    private final Logger log = LoggerFactory.getLogger(WorkExperienceService.class);

    private final WorkExperienceRepository workExperienceRepository;

    private final WorkExperienceMapper workExperienceMapper;

    public WorkExperienceService(WorkExperienceRepository workExperienceRepository, WorkExperienceMapper workExperienceMapper) {
        this.workExperienceRepository = workExperienceRepository;
        this.workExperienceMapper = workExperienceMapper;
    }

    /**
     * Save a workExperience.
     *
     * @param workExperienceDTO the entity to save.
     * @return the persisted entity.
     */
    public WorkExperienceDTO save(WorkExperienceDTO workExperienceDTO) {
        log.debug("Request to save WorkExperience : {}", workExperienceDTO);
        WorkExperience workExperience = workExperienceMapper.toEntity(workExperienceDTO);
        workExperience = workExperienceRepository.save(workExperience);
        return workExperienceMapper.toDto(workExperience);
    }

    /**
     * Update a workExperience.
     *
     * @param workExperienceDTO the entity to save.
     * @return the persisted entity.
     */
    public WorkExperienceDTO update(WorkExperienceDTO workExperienceDTO) {
        log.debug("Request to update WorkExperience : {}", workExperienceDTO);
        WorkExperience workExperience = workExperienceMapper.toEntity(workExperienceDTO);
        workExperience = workExperienceRepository.save(workExperience);
        return workExperienceMapper.toDto(workExperience);
    }

    /**
     * Partially update a workExperience.
     *
     * @param workExperienceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WorkExperienceDTO> partialUpdate(WorkExperienceDTO workExperienceDTO) {
        log.debug("Request to partially update WorkExperience : {}", workExperienceDTO);

        return workExperienceRepository
            .findById(workExperienceDTO.getId())
            .map(existingWorkExperience -> {
                workExperienceMapper.partialUpdate(existingWorkExperience, workExperienceDTO);

                return existingWorkExperience;
            })
            .map(workExperienceRepository::save)
            .map(workExperienceMapper::toDto);
    }

    /**
     * Get all the workExperiences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkExperienceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkExperiences");
        return workExperienceRepository.findAll(pageable).map(workExperienceMapper::toDto);
    }

    /**
     * Get one workExperience by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkExperienceDTO> findOne(Long id) {
        log.debug("Request to get WorkExperience : {}", id);
        return workExperienceRepository.findById(id).map(workExperienceMapper::toDto);
    }

    /**
     * Delete the workExperience by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkExperience : {}", id);
        workExperienceRepository.deleteById(id);
    }
}
