package com.itd.myapp.service;

import com.itd.myapp.domain.Host;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Host}.
 */
public interface HostService {

    /**
     * Save a host.
     *
     * @param host the entity to save.
     * @return the persisted entity.
     */
    Host save(Host host);

    /**
     * Get all the hosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Host> findAll(Pageable pageable);
    /**
     * Get all the HostDTO where HostName is {@code null}.
     *
     * @return the list of entities.
     */
    List<Host> findAllWhereHostNameIsNull();


    /**
     * Get the "id" host.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Host> findOne(Long id);

    /**
     * Delete the "id" host.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
