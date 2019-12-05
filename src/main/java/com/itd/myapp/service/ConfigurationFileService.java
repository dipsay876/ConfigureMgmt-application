package com.itd.myapp.service;

import com.itd.myapp.domain.ConfigurationFile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ConfigurationFile}.
 */
public interface ConfigurationFileService {

    /**
     * Save a configurationFile.
     *
     * @param configurationFile the entity to save.
     * @return the persisted entity.
     */
    ConfigurationFile save(ConfigurationFile configurationFile);

    /**
     * Get all the configurationFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConfigurationFile> findAll(Pageable pageable);


    /**
     * Get the "id" configurationFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConfigurationFile> findOne(Long id);

    /**
     * Delete the "id" configurationFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
