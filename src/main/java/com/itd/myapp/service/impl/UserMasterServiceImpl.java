package com.itd.myapp.service.impl;

import com.itd.myapp.service.UserMasterService;
import com.itd.myapp.domain.UserMaster;
import com.itd.myapp.repository.UserMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserMaster}.
 */
@Service
@Transactional
public class UserMasterServiceImpl implements UserMasterService {

    private final Logger log = LoggerFactory.getLogger(UserMasterServiceImpl.class);

    private final UserMasterRepository userMasterRepository;

    public UserMasterServiceImpl(UserMasterRepository userMasterRepository) {
        this.userMasterRepository = userMasterRepository;
    }

    /**
     * Save a userMaster.
     *
     * @param userMaster the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UserMaster save(UserMaster userMaster) {
        log.debug("Request to save UserMaster : {}", userMaster);
        return userMasterRepository.save(userMaster);
    }

    /**
     * Get all the userMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserMaster> findAll(Pageable pageable) {
        log.debug("Request to get all UserMasters");
        return userMasterRepository.findAll(pageable);
    }


    /**
     * Get one userMaster by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserMaster> findOne(Long id) {
        log.debug("Request to get UserMaster : {}", id);
        return userMasterRepository.findById(id);
    }

    /**
     * Delete the userMaster by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserMaster : {}", id);
        userMasterRepository.deleteById(id);
    }
}
