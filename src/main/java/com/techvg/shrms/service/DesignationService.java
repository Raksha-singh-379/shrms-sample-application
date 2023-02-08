package com.techvg.shrms.service;

import com.techvg.shrms.domain.Designation;
import com.techvg.shrms.repository.DesignationRepository;
import com.techvg.shrms.service.dto.DesignationDTO;
import com.techvg.shrms.service.mapper.DesignationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Designation}.
 */
@Service
@Transactional
public class DesignationService {

    private final Logger log = LoggerFactory.getLogger(DesignationService.class);

    private final DesignationRepository designationRepository;

    private final DesignationMapper designationMapper;

    public DesignationService(DesignationRepository designationRepository, DesignationMapper designationMapper) {
        this.designationRepository = designationRepository;
        this.designationMapper = designationMapper;
    }

    /**
     * Save a designation.
     *
     * @param designationDTO the entity to save.
     * @return the persisted entity.
     */
    public DesignationDTO save(DesignationDTO designationDTO) {
        log.debug("Request to save Designation : {}", designationDTO);
        Designation designation = designationMapper.toEntity(designationDTO);
        designation = designationRepository.save(designation);
        return designationMapper.toDto(designation);
    }

    /**
     * Update a designation.
     *
     * @param designationDTO the entity to save.
     * @return the persisted entity.
     */
    public DesignationDTO update(DesignationDTO designationDTO) {
        log.debug("Request to update Designation : {}", designationDTO);
        Designation designation = designationMapper.toEntity(designationDTO);
        designation = designationRepository.save(designation);
        return designationMapper.toDto(designation);
    }

    /**
     * Partially update a designation.
     *
     * @param designationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DesignationDTO> partialUpdate(DesignationDTO designationDTO) {
        log.debug("Request to partially update Designation : {}", designationDTO);

        return designationRepository
            .findById(designationDTO.getId())
            .map(existingDesignation -> {
                designationMapper.partialUpdate(existingDesignation, designationDTO);

                return existingDesignation;
            })
            .map(designationRepository::save)
            .map(designationMapper::toDto);
    }

    /**
     * Get all the designations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DesignationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Designations");
        return designationRepository.findAll(pageable).map(designationMapper::toDto);
    }

    /**
     * Get one designation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DesignationDTO> findOne(Long id) {
        log.debug("Request to get Designation : {}", id);
        return designationRepository.findById(id).map(designationMapper::toDto);
    }

    /**
     * Delete the designation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Designation : {}", id);
        designationRepository.deleteById(id);
    }
}
