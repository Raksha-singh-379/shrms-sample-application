package com.techvg.shrms.service;

import com.techvg.shrms.domain.EmpSalaryInfo;
import com.techvg.shrms.repository.EmpSalaryInfoRepository;
import com.techvg.shrms.service.dto.EmpSalaryInfoDTO;
import com.techvg.shrms.service.mapper.EmpSalaryInfoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmpSalaryInfo}.
 */
@Service
@Transactional
public class EmpSalaryInfoService {

    private final Logger log = LoggerFactory.getLogger(EmpSalaryInfoService.class);

    private final EmpSalaryInfoRepository empSalaryInfoRepository;

    private final EmpSalaryInfoMapper empSalaryInfoMapper;

    public EmpSalaryInfoService(EmpSalaryInfoRepository empSalaryInfoRepository, EmpSalaryInfoMapper empSalaryInfoMapper) {
        this.empSalaryInfoRepository = empSalaryInfoRepository;
        this.empSalaryInfoMapper = empSalaryInfoMapper;
    }

    /**
     * Save a empSalaryInfo.
     *
     * @param empSalaryInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public EmpSalaryInfoDTO save(EmpSalaryInfoDTO empSalaryInfoDTO) {
        log.debug("Request to save EmpSalaryInfo : {}", empSalaryInfoDTO);
        EmpSalaryInfo empSalaryInfo = empSalaryInfoMapper.toEntity(empSalaryInfoDTO);
        empSalaryInfo = empSalaryInfoRepository.save(empSalaryInfo);
        return empSalaryInfoMapper.toDto(empSalaryInfo);
    }

    /**
     * Update a empSalaryInfo.
     *
     * @param empSalaryInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public EmpSalaryInfoDTO update(EmpSalaryInfoDTO empSalaryInfoDTO) {
        log.debug("Request to update EmpSalaryInfo : {}", empSalaryInfoDTO);
        EmpSalaryInfo empSalaryInfo = empSalaryInfoMapper.toEntity(empSalaryInfoDTO);
        empSalaryInfo = empSalaryInfoRepository.save(empSalaryInfo);
        return empSalaryInfoMapper.toDto(empSalaryInfo);
    }

    /**
     * Partially update a empSalaryInfo.
     *
     * @param empSalaryInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmpSalaryInfoDTO> partialUpdate(EmpSalaryInfoDTO empSalaryInfoDTO) {
        log.debug("Request to partially update EmpSalaryInfo : {}", empSalaryInfoDTO);

        return empSalaryInfoRepository
            .findById(empSalaryInfoDTO.getId())
            .map(existingEmpSalaryInfo -> {
                empSalaryInfoMapper.partialUpdate(existingEmpSalaryInfo, empSalaryInfoDTO);

                return existingEmpSalaryInfo;
            })
            .map(empSalaryInfoRepository::save)
            .map(empSalaryInfoMapper::toDto);
    }

    /**
     * Get all the empSalaryInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmpSalaryInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmpSalaryInfos");
        return empSalaryInfoRepository.findAll(pageable).map(empSalaryInfoMapper::toDto);
    }

    /**
     * Get one empSalaryInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmpSalaryInfoDTO> findOne(Long id) {
        log.debug("Request to get EmpSalaryInfo : {}", id);
        return empSalaryInfoRepository.findById(id).map(empSalaryInfoMapper::toDto);
    }

    /**
     * Delete the empSalaryInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmpSalaryInfo : {}", id);
        empSalaryInfoRepository.deleteById(id);
    }
}
