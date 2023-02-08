package com.techvg.shrms.service;

import com.techvg.shrms.domain.LeaveTranscation;
import com.techvg.shrms.repository.LeaveTranscationRepository;
import com.techvg.shrms.service.dto.LeaveTranscationDTO;
import com.techvg.shrms.service.mapper.LeaveTranscationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeaveTranscation}.
 */
@Service
@Transactional
public class LeaveTranscationService {

    private final Logger log = LoggerFactory.getLogger(LeaveTranscationService.class);

    private final LeaveTranscationRepository leaveTranscationRepository;

    private final LeaveTranscationMapper leaveTranscationMapper;

    public LeaveTranscationService(LeaveTranscationRepository leaveTranscationRepository, LeaveTranscationMapper leaveTranscationMapper) {
        this.leaveTranscationRepository = leaveTranscationRepository;
        this.leaveTranscationMapper = leaveTranscationMapper;
    }

    /**
     * Save a leaveTranscation.
     *
     * @param leaveTranscationDTO the entity to save.
     * @return the persisted entity.
     */
    public LeaveTranscationDTO save(LeaveTranscationDTO leaveTranscationDTO) {
        log.debug("Request to save LeaveTranscation : {}", leaveTranscationDTO);
        LeaveTranscation leaveTranscation = leaveTranscationMapper.toEntity(leaveTranscationDTO);
        leaveTranscation = leaveTranscationRepository.save(leaveTranscation);
        return leaveTranscationMapper.toDto(leaveTranscation);
    }

    /**
     * Update a leaveTranscation.
     *
     * @param leaveTranscationDTO the entity to save.
     * @return the persisted entity.
     */
    public LeaveTranscationDTO update(LeaveTranscationDTO leaveTranscationDTO) {
        log.debug("Request to update LeaveTranscation : {}", leaveTranscationDTO);
        LeaveTranscation leaveTranscation = leaveTranscationMapper.toEntity(leaveTranscationDTO);
        leaveTranscation = leaveTranscationRepository.save(leaveTranscation);
        return leaveTranscationMapper.toDto(leaveTranscation);
    }

    /**
     * Partially update a leaveTranscation.
     *
     * @param leaveTranscationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeaveTranscationDTO> partialUpdate(LeaveTranscationDTO leaveTranscationDTO) {
        log.debug("Request to partially update LeaveTranscation : {}", leaveTranscationDTO);

        return leaveTranscationRepository
            .findById(leaveTranscationDTO.getId())
            .map(existingLeaveTranscation -> {
                leaveTranscationMapper.partialUpdate(existingLeaveTranscation, leaveTranscationDTO);

                return existingLeaveTranscation;
            })
            .map(leaveTranscationRepository::save)
            .map(leaveTranscationMapper::toDto);
    }

    /**
     * Get all the leaveTranscations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveTranscationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveTranscations");
        return leaveTranscationRepository.findAll(pageable).map(leaveTranscationMapper::toDto);
    }

    /**
     * Get one leaveTranscation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveTranscationDTO> findOne(Long id) {
        log.debug("Request to get LeaveTranscation : {}", id);
        return leaveTranscationRepository.findById(id).map(leaveTranscationMapper::toDto);
    }

    /**
     * Delete the leaveTranscation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveTranscation : {}", id);
        leaveTranscationRepository.deleteById(id);
    }
}
