package com.itd.myapp.web.rest;

import com.itd.myapp.domain.OSVersion;
import com.itd.myapp.service.OSVersionService;
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
 * REST controller for managing {@link com.itd.myapp.domain.OSVersion}.
 */
@RestController
@RequestMapping("/api")
public class OSVersionResource {

    private final Logger log = LoggerFactory.getLogger(OSVersionResource.class);

    private static final String ENTITY_NAME = "configureMgmtSampleApplicationOsVersion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OSVersionService oSVersionService;

    public OSVersionResource(OSVersionService oSVersionService) {
        this.oSVersionService = oSVersionService;
    }

    /**
     * {@code POST  /os-versions} : Create a new oSVersion.
     *
     * @param oSVersion the oSVersion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new oSVersion, or with status {@code 400 (Bad Request)} if the oSVersion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/os-versions")
    public ResponseEntity<OSVersion> createOSVersion(@Valid @RequestBody OSVersion oSVersion) throws URISyntaxException {
        log.debug("REST request to save OSVersion : {}", oSVersion);
        if (oSVersion.getId() != null) {
            throw new BadRequestAlertException("A new oSVersion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OSVersion result = oSVersionService.save(oSVersion);
        return ResponseEntity.created(new URI("/api/os-versions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /os-versions} : Updates an existing oSVersion.
     *
     * @param oSVersion the oSVersion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oSVersion,
     * or with status {@code 400 (Bad Request)} if the oSVersion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the oSVersion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/os-versions")
    public ResponseEntity<OSVersion> updateOSVersion(@Valid @RequestBody OSVersion oSVersion) throws URISyntaxException {
        log.debug("REST request to update OSVersion : {}", oSVersion);
        if (oSVersion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OSVersion result = oSVersionService.save(oSVersion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, oSVersion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /os-versions} : get all the oSVersions.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of oSVersions in body.
     */
    @GetMapping("/os-versions")
    public ResponseEntity<List<OSVersion>> getAllOSVersions(Pageable pageable) {
        log.debug("REST request to get a page of OSVersions");
        Page<OSVersion> page = oSVersionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /os-versions/:id} : get the "id" oSVersion.
     *
     * @param id the id of the oSVersion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the oSVersion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/os-versions/{id}")
    public ResponseEntity<OSVersion> getOSVersion(@PathVariable Long id) {
        log.debug("REST request to get OSVersion : {}", id);
        Optional<OSVersion> oSVersion = oSVersionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(oSVersion);
    }

    /**
     * {@code DELETE  /os-versions/:id} : delete the "id" oSVersion.
     *
     * @param id the id of the oSVersion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/os-versions/{id}")
    public ResponseEntity<Void> deleteOSVersion(@PathVariable Long id) {
        log.debug("REST request to delete OSVersion : {}", id);
        oSVersionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
