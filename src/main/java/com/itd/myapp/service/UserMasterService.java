package com.itd.myapp.service;

import com.itd.myapp.domain.UserMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link UserMaster}.
 */
public interface UserMasterService {

    /**
     * Save a userMaster.
     *
     * @param userMaster the entity to save.
     * @return the persisted entity.
     */
    UserMaster save(UserMaster userMaster);

    /**
     * Get all the userMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserMaster> findAll(Pageable pageable);


    /**
     * Get the "id" userMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserMaster> findOne(Long id);

    /**
     * Delete the "id" userMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
