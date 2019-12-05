package com.itd.myapp.web.rest;

import com.itd.myapp.domain.UserMaster;
import com.itd.myapp.service.UserMasterService;
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
 * REST controller for managing {@link com.itd.myapp.domain.UserMaster}.
 */
@RestController
@RequestMapping("/api")
public class UserMasterResource {

    private final Logger log = LoggerFactory.getLogger(UserMasterResource.class);

    private static final String ENTITY_NAME = "configureMgmtSampleApplicationUserMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserMasterService userMasterService;

    public UserMasterResource(UserMasterService userMasterService) {
        this.userMasterService = userMasterService;
    }

    /**
     * {@code POST  /user-masters} : Create a new userMaster.
     *
     * @param userMaster the userMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userMaster, or with status {@code 400 (Bad Request)} if the userMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-masters")
    public ResponseEntity<UserMaster> createUserMaster(@Valid @RequestBody UserMaster userMaster) throws URISyntaxException {
        log.debug("REST request to save UserMaster : {}", userMaster);
        if (userMaster.getId() != null) {
            throw new BadRequestAlertException("A new userMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserMaster result = userMasterService.save(userMaster);
        return ResponseEntity.created(new URI("/api/user-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-masters} : Updates an existing userMaster.
     *
     * @param userMaster the userMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userMaster,
     * or with status {@code 400 (Bad Request)} if the userMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-masters")
    public ResponseEntity<UserMaster> updateUserMaster(@Valid @RequestBody UserMaster userMaster) throws URISyntaxException {
        log.debug("REST request to update UserMaster : {}", userMaster);
        if (userMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserMaster result = userMasterService.save(userMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-masters} : get all the userMasters.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userMasters in body.
     */
    @GetMapping("/user-masters")
    public ResponseEntity<List<UserMaster>> getAllUserMasters(Pageable pageable) {
        log.debug("REST request to get a page of UserMasters");
        Page<UserMaster> page = userMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-masters/:id} : get the "id" userMaster.
     *
     * @param id the id of the userMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-masters/{id}")
    public ResponseEntity<UserMaster> getUserMaster(@PathVariable Long id) {
        log.debug("REST request to get UserMaster : {}", id);
        Optional<UserMaster> userMaster = userMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userMaster);
    }

    /**
     * {@code DELETE  /user-masters/:id} : delete the "id" userMaster.
     *
     * @param id the id of the userMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-masters/{id}")
    public ResponseEntity<Void> deleteUserMaster(@PathVariable Long id) {
        log.debug("REST request to delete UserMaster : {}", id);
        userMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
