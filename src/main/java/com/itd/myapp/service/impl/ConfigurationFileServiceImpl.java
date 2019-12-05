package com.itd.myapp.service.impl;

import com.itd.myapp.service.ConfigurationFileService;
import com.itd.myapp.domain.ConfigurationFile;
import com.itd.myapp.repository.ConfigurationFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ConfigurationFile}.
 */
@Service
@Transactional
public class ConfigurationFileServiceImpl implements ConfigurationFileService {

    private final Logger log = LoggerFactory.getLogger(ConfigurationFileServiceImpl.class);

    private final ConfigurationFileRepository configurationFileRepository;

    public ConfigurationFileServiceImpl(ConfigurationFileRepository configurationFileRepository) {
        this.configurationFileRepository = configurationFileRepository;
    }

    /**
     * Save a configurationFile.
     *
     * @param configurationFile the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ConfigurationFile save(ConfigurationFile configurationFile) {
        log.debug("Request to save ConfigurationFile : {}", configurationFile);
        return configurationFileRepository.save(configurationFile);
    }

    /**
     * Get all the configurationFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConfigurationFile> findAll(Pageable pageable) {
        log.debug("Request to get all ConfigurationFiles");
        return configurationFileRepository.findAll(pageable);
    }


    /**
     * Get one configurationFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConfigurationFile> findOne(Long id) {
        log.debug("Request to get ConfigurationFile : {}", id);
        return configurationFileRepository.findById(id);
    }

    /**
     * Delete the configurationFile by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConfigurationFile : {}", id);
        configurationFileRepository.deleteById(id);
    }
}
