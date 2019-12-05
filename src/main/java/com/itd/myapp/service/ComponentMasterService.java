package com.itd.myapp.service;

import com.itd.myapp.domain.ComponentMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ComponentMaster}.
 */
public interface ComponentMasterService {

    /**
     * Save a componentMaster.
     *
     * @param componentMaster the entity to save.
     * @return the persisted entity.
     */
    ComponentMaster save(ComponentMaster componentMaster);

    /**
     * Get all the componentMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ComponentMaster> findAll(Pageable pageable);
    /**
     * Get all the ComponentMasterDTO where ComponentName is {@code null}.
     *
     * @return the list of entities.
     */
    List<ComponentMaster> findAllWhereComponentNameIsNull();


    /**
     * Get the "id" componentMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ComponentMaster> findOne(Long id);

    /**
     * Delete the "id" componentMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
