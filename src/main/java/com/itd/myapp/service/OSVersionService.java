package com.itd.myapp.service;

import com.itd.myapp.domain.OSVersion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link OSVersion}.
 */
public interface OSVersionService {

    /**
     * Save a oSVersion.
     *
     * @param oSVersion the entity to save.
     * @return the persisted entity.
     */
    OSVersion save(OSVersion oSVersion);

    /**
     * Get all the oSVersions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OSVersion> findAll(Pageable pageable);


    /**
     * Get the "id" oSVersion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OSVersion> findOne(Long id);

    /**
     * Delete the "id" oSVersion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
