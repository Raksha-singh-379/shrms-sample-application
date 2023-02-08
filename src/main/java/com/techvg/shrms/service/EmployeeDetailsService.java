package com.techvg.shrms.service;

import com.techvg.shrms.domain.EmployeeDetails;
import com.techvg.shrms.repository.EmployeeDetailsRepository;
import com.techvg.shrms.service.dto.EmployeeDetailsDTO;
import com.techvg.shrms.service.mapper.EmployeeDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeDetails}.
 */
@Service
@Transactional
public class EmployeeDetailsService {

    private final Logger log = LoggerFactory.getLogger(EmployeeDetailsService.class);

    private final EmployeeDetailsRepository employeeDetailsRepository;

    private final EmployeeDetailsMapper employeeDetailsMapper;

    public EmployeeDetailsService(EmployeeDetailsRepository employeeDetailsRepository, EmployeeDetailsMapper employeeDetailsMapper) {
        this.employeeDetailsRepository = employeeDetailsRepository;
        this.employeeDetailsMapper = employeeDetailsMapper;
    }

    /**
     * Save a employeeDetails.
     *
     * @param employeeDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeDetailsDTO save(EmployeeDetailsDTO employeeDetailsDTO) {
        log.debug("Request to save EmployeeDetails : {}", employeeDetailsDTO);
        EmployeeDetails employeeDetails = employeeDetailsMapper.toEntity(employeeDetailsDTO);
        employeeDetails = employeeDetailsRepository.save(employeeDetails);
        return employeeDetailsMapper.toDto(employeeDetails);
    }

    /**
     * Update a employeeDetails.
     *
     * @param employeeDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeDetailsDTO update(EmployeeDetailsDTO employeeDetailsDTO) {
        log.debug("Request to update EmployeeDetails : {}", employeeDetailsDTO);
        EmployeeDetails employeeDetails = employeeDetailsMapper.toEntity(employeeDetailsDTO);
        employeeDetails = employeeDetailsRepository.save(employeeDetails);
        return employeeDetailsMapper.toDto(employeeDetails);
    }

    /**
     * Partially update a employeeDetails.
     *
     * @param employeeDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeDetailsDTO> partialUpdate(EmployeeDetailsDTO employeeDetailsDTO) {
        log.debug("Request to partially update EmployeeDetails : {}", employeeDetailsDTO);

        return employeeDetailsRepository
            .findById(employeeDetailsDTO.getId())
            .map(existingEmployeeDetails -> {
                employeeDetailsMapper.partialUpdate(existingEmployeeDetails, employeeDetailsDTO);

                return existingEmployeeDetails;
            })
            .map(employeeDetailsRepository::save)
            .map(employeeDetailsMapper::toDto);
    }

    /**
     * Get all the employeeDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeDetails");
        return employeeDetailsRepository.findAll(pageable).map(employeeDetailsMapper::toDto);
    }

    /**
     * Get one employeeDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeDetailsDTO> findOne(Long id) {
        log.debug("Request to get EmployeeDetails : {}", id);
        return employeeDetailsRepository.findById(id).map(employeeDetailsMapper::toDto);
    }

    /**
     * Delete the employeeDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeDetails : {}", id);
        employeeDetailsRepository.deleteById(id);
    }
}
