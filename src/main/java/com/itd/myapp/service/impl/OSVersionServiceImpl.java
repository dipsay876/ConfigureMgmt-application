package com.itd.myapp.service.impl;

import com.itd.myapp.service.OSVersionService;
import com.itd.myapp.domain.OSVersion;
import com.itd.myapp.repository.OSVersionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OSVersion}.
 */
@Service
@Transactional
public class OSVersionServiceImpl implements OSVersionService {

    private final Logger log = LoggerFactory.getLogger(OSVersionServiceImpl.class);

    private final OSVersionRepository oSVersionRepository;

    public OSVersionServiceImpl(OSVersionRepository oSVersionRepository) {
        this.oSVersionRepository = oSVersionRepository;
    }

    /**
     * Save a oSVersion.
     *
     * @param oSVersion the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OSVersion save(OSVersion oSVersion) {
        log.debug("Request to save OSVersion : {}", oSVersion);
        return oSVersionRepository.save(oSVersion);
    }

    /**
     * Get all the oSVersions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OSVersion> findAll(Pageable pageable) {
        log.debug("Request to get all OSVersions");
        return oSVersionRepository.findAll(pageable);
    }


    /**
     * Get one oSVersion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OSVersion> findOne(Long id) {
        log.debug("Request to get OSVersion : {}", id);
        return oSVersionRepository.findById(id);
    }

    /**
     * Delete the oSVersion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OSVersion : {}", id);
        oSVersionRepository.deleteById(id);
    }
}
