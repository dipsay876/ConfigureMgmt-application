package com.itd.myapp.web.rest;

import com.itd.myapp.domain.ComponentMaster;
import com.itd.myapp.service.ComponentMasterService;
import com.itd.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.itd.myapp.domain.ComponentMaster}.
 */
@RestController
@RequestMapping("/api")
public class ComponentMasterResource {

    private final Logger log = LoggerFactory.getLogger(ComponentMasterResource.class);

    private static final String ENTITY_NAME = "configureMgmtSampleApplicationComponentMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComponentMasterService componentMasterService;

    public ComponentMasterResource(ComponentMasterService componentMasterService) {
        this.componentMasterService = componentMasterService;
    }

    /**
     * {@code POST  /component-masters} : Create a new componentMaster.
     *
     * @param componentMaster the componentMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new componentMaster, or with status {@code 400 (Bad Request)} if the componentMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/component-masters")
    public ResponseEntity<ComponentMaster> createComponentMaster(@Valid @RequestBody ComponentMaster componentMaster) throws URISyntaxException {
        log.debug("REST request to save ComponentMaster : {}", componentMaster);
        if (componentMaster.getId() != null) {
            throw new BadRequestAlertException("A new componentMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComponentMaster result = componentMasterService.save(componentMaster);
        return ResponseEntity.created(new URI("/api/component-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /component-masters} : Updates an existing componentMaster.
     *
     * @param componentMaster the componentMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated componentMaster,
     * or with status {@code 400 (Bad Request)} if the componentMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the componentMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/component-masters")
    public ResponseEntity<ComponentMaster> updateComponentMaster(@Valid @RequestBody ComponentMaster componentMaster) throws URISyntaxException {
        log.debug("REST request to update ComponentMaster : {}", componentMaster);
        if (componentMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComponentMaster result = componentMasterService.save(componentMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, componentMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /component-masters} : get all the componentMasters.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of componentMasters in body.
     */
    @GetMapping("/component-masters")
    public ResponseEntity<List<ComponentMaster>> getAllComponentMasters(Pageable pageable) {
        log.debug("REST request to get a page of ComponentMasters");
        Page<ComponentMaster> page = componentMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /component-masters/:id} : get the "id" componentMaster.
     *
     * @param id the id of the componentMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the componentMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/component-masters/{id}")
    public ResponseEntity<ComponentMaster> getComponentMaster(@PathVariable Long id) {
        log.debug("REST request to get ComponentMaster : {}", id);
        Optional<ComponentMaster> componentMaster = componentMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(componentMaster);
    }

    /**
     * {@code DELETE  /component-masters/:id} : delete the "id" componentMaster.
     *
     * @param id the id of the componentMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/component-masters/{id}")
    public ResponseEntity<Void> deleteComponentMaster(@PathVariable Long id) {
        log.debug("REST request to delete ComponentMaster : {}", id);
        componentMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
