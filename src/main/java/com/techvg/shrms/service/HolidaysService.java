package com.techvg.shrms.service;

import com.techvg.shrms.domain.Holidays;
import com.techvg.shrms.repository.HolidaysRepository;
import com.techvg.shrms.service.dto.HolidaysDTO;
import com.techvg.shrms.service.mapper.HolidaysMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Holidays}.
 */
@Service
@Transactional
public class HolidaysService {

    private final Logger log = LoggerFactory.getLogger(HolidaysService.class);

    private final HolidaysRepository holidaysRepository;

    private final HolidaysMapper holidaysMapper;

    public HolidaysService(HolidaysRepository holidaysRepository, HolidaysMapper holidaysMapper) {
        this.holidaysRepository = holidaysRepository;
        this.holidaysMapper = holidaysMapper;
    }

    /**
     * Save a holidays.
     *
     * @param holidaysDTO the entity to save.
     * @return the persisted entity.
     */
    public HolidaysDTO save(HolidaysDTO holidaysDTO) {
        log.debug("Request to save Holidays : {}", holidaysDTO);
        Holidays holidays = holidaysMapper.toEntity(holidaysDTO);
        holidays = holidaysRepository.save(holidays);
        return holidaysMapper.toDto(holidays);
    }

    /**
     * Update a holidays.
     *
     * @param holidaysDTO the entity to save.
     * @return the persisted entity.
     */
    public HolidaysDTO update(HolidaysDTO holidaysDTO) {
        log.debug("Request to update Holidays : {}", holidaysDTO);
        Holidays holidays = holidaysMapper.toEntity(holidaysDTO);
        holidays = holidaysRepository.save(holidays);
        return holidaysMapper.toDto(holidays);
    }

    /**
     * Partially update a holidays.
     *
     * @param holidaysDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HolidaysDTO> partialUpdate(HolidaysDTO holidaysDTO) {
        log.debug("Request to partially update Holidays : {}", holidaysDTO);

        return holidaysRepository
            .findById(holidaysDTO.getId())
            .map(existingHolidays -> {
                holidaysMapper.partialUpdate(existingHolidays, holidaysDTO);

                return existingHolidays;
            })
            .map(holidaysRepository::save)
            .map(holidaysMapper::toDto);
    }

    /**
     * Get all the holidays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HolidaysDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Holidays");
        return holidaysRepository.findAll(pageable).map(holidaysMapper::toDto);
    }

    /**
     * Get one holidays by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HolidaysDTO> findOne(Long id) {
        log.debug("Request to get Holidays : {}", id);
        return holidaysRepository.findById(id).map(holidaysMapper::toDto);
    }

    /**
     * Delete the holidays by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Holidays : {}", id);
        holidaysRepository.deleteById(id);
    }
}
