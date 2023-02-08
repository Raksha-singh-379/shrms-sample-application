package com.techvg.shrms.service;

import com.techvg.shrms.domain.SalarySettings;
import com.techvg.shrms.repository.SalarySettingsRepository;
import com.techvg.shrms.service.dto.SalarySettingsDTO;
import com.techvg.shrms.service.mapper.SalarySettingsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SalarySettings}.
 */
@Service
@Transactional
public class SalarySettingsService {

    private final Logger log = LoggerFactory.getLogger(SalarySettingsService.class);

    private final SalarySettingsRepository salarySettingsRepository;

    private final SalarySettingsMapper salarySettingsMapper;

    public SalarySettingsService(SalarySettingsRepository salarySettingsRepository, SalarySettingsMapper salarySettingsMapper) {
        this.salarySettingsRepository = salarySettingsRepository;
        this.salarySettingsMapper = salarySettingsMapper;
    }

    /**
     * Save a salarySettings.
     *
     * @param salarySettingsDTO the entity to save.
     * @return the persisted entity.
     */
    public SalarySettingsDTO save(SalarySettingsDTO salarySettingsDTO) {
        log.debug("Request to save SalarySettings : {}", salarySettingsDTO);
        SalarySettings salarySettings = salarySettingsMapper.toEntity(salarySettingsDTO);
        salarySettings = salarySettingsRepository.save(salarySettings);
        return salarySettingsMapper.toDto(salarySettings);
    }

    /**
     * Update a salarySettings.
     *
     * @param salarySettingsDTO the entity to save.
     * @return the persisted entity.
     */
    public SalarySettingsDTO update(SalarySettingsDTO salarySettingsDTO) {
        log.debug("Request to update SalarySettings : {}", salarySettingsDTO);
        SalarySettings salarySettings = salarySettingsMapper.toEntity(salarySettingsDTO);
        salarySettings = salarySettingsRepository.save(salarySettings);
        return salarySettingsMapper.toDto(salarySettings);
    }

    /**
     * Partially update a salarySettings.
     *
     * @param salarySettingsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SalarySettingsDTO> partialUpdate(SalarySettingsDTO salarySettingsDTO) {
        log.debug("Request to partially update SalarySettings : {}", salarySettingsDTO);

        return salarySettingsRepository
            .findById(salarySettingsDTO.getId())
            .map(existingSalarySettings -> {
                salarySettingsMapper.partialUpdate(existingSalarySettings, salarySettingsDTO);

                return existingSalarySettings;
            })
            .map(salarySettingsRepository::save)
            .map(salarySettingsMapper::toDto);
    }

    /**
     * Get all the salarySettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SalarySettingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SalarySettings");
        return salarySettingsRepository.findAll(pageable).map(salarySettingsMapper::toDto);
    }

    /**
     * Get one salarySettings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SalarySettingsDTO> findOne(Long id) {
        log.debug("Request to get SalarySettings : {}", id);
        return salarySettingsRepository.findById(id).map(salarySettingsMapper::toDto);
    }

    /**
     * Delete the salarySettings by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SalarySettings : {}", id);
        salarySettingsRepository.deleteById(id);
    }
}
