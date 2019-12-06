package com.itd.myapp.service.impl;

import com.itd.myapp.service.ComponentMasterService;
import com.itd.myapp.domain.ComponentMaster;
import com.itd.myapp.repository.ComponentMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ComponentMaster}.
 */
@Service
@Transactional
public class ComponentMasterServiceImpl implements ComponentMasterService {

    private final Logger log = LoggerFactory.getLogger(ComponentMasterServiceImpl.class);

    private final ComponentMasterRepository componentMasterRepository;

    public ComponentMasterServiceImpl(ComponentMasterRepository componentMasterRepository) {
        this.componentMasterRepository = componentMasterRepository;
    }

    /**
     * Save a componentMaster.
     *
     * @param componentMaster the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ComponentMaster save(ComponentMaster componentMaster) {
        log.debug("Request to save ComponentMaster : {}", componentMaster);
        return componentMasterRepository.save(componentMaster);
    }

    /**
     * Get all the componentMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ComponentMaster> findAll(Pageable pageable) {
        log.debug("Request to get all ComponentMasters");
        return componentMasterRepository.findAll(pageable);
    }


    /**
     * Get one componentMaster by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ComponentMaster> findOne(Long id) {
        log.debug("Request to get ComponentMaster : {}", id);
        return componentMasterRepository.findById(id);
    }

    /**
     * Delete the componentMaster by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ComponentMaster : {}", id);
        componentMasterRepository.deleteById(id);
    }
}
