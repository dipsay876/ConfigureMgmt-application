package com.itd.myapp.service.impl;

import com.itd.myapp.service.HostService;
import com.itd.myapp.domain.Host;
import com.itd.myapp.repository.HostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Host}.
 */
@Service
@Transactional
public class HostServiceImpl implements HostService {

    private final Logger log = LoggerFactory.getLogger(HostServiceImpl.class);

    private final HostRepository hostRepository;

    public HostServiceImpl(HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }

    /**
     * Save a host.
     *
     * @param host the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Host save(Host host) {
        log.debug("Request to save Host : {}", host);
        return hostRepository.save(host);
    }

    /**
     * Get all the hosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Host> findAll(Pageable pageable) {
        log.debug("Request to get all Hosts");
        return hostRepository.findAll(pageable);
    }


    /**
     * Get one host by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Host> findOne(Long id) {
        log.debug("Request to get Host : {}", id);
        return hostRepository.findById(id);
    }

    /**
     * Delete the host by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Host : {}", id);
        hostRepository.deleteById(id);
    }
}
