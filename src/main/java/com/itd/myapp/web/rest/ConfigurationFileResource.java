package com.itd.myapp.web.rest;

import com.itd.myapp.domain.ConfigurationFile;
import com.itd.myapp.service.ConfigurationFileService;
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
 * REST controller for managing {@link com.itd.myapp.domain.ConfigurationFile}.
 */
@RestController
@RequestMapping("/api")
public class ConfigurationFileResource {

    private final Logger log = LoggerFactory.getLogger(ConfigurationFileResource.class);

    private static final String ENTITY_NAME = "configureMgmtSampleApplicationConfigurationFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfigurationFileService configurationFileService;

    public ConfigurationFileResource(ConfigurationFileService configurationFileService) {
        this.configurationFileService = configurationFileService;
    }

    /**
     * {@code POST  /configuration-files} : Create a new configurationFile.
     *
     * @param configurationFile the configurationFile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new configurationFile, or with status {@code 400 (Bad Request)} if the configurationFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/configuration-files")
    public ResponseEntity<ConfigurationFile> createConfigurationFile(@Valid @RequestBody ConfigurationFile configurationFile) throws URISyntaxException {
        log.debug("REST request to save ConfigurationFile : {}", configurationFile);
        if (configurationFile.getId() != null) {
            throw new BadRequestAlertException("A new configurationFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConfigurationFile result = configurationFileService.save(configurationFile);
        return ResponseEntity.created(new URI("/api/configuration-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /configuration-files} : Updates an existing configurationFile.
     *
     * @param configurationFile the configurationFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configurationFile,
     * or with status {@code 400 (Bad Request)} if the configurationFile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the configurationFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/configuration-files")
    public ResponseEntity<ConfigurationFile> updateConfigurationFile(@Valid @RequestBody ConfigurationFile configurationFile) throws URISyntaxException {
        log.debug("REST request to update ConfigurationFile : {}", configurationFile);
        if (configurationFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConfigurationFile result = configurationFileService.save(configurationFile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, configurationFile.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /configuration-files} : get all the configurationFiles.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of configurationFiles in body.
     */
    @GetMapping("/configuration-files")
    public ResponseEntity<List<ConfigurationFile>> getAllConfigurationFiles(Pageable pageable) {
        log.debug("REST request to get a page of ConfigurationFiles");
        Page<ConfigurationFile> page = configurationFileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /configuration-files/:id} : get the "id" configurationFile.
     *
     * @param id the id of the configurationFile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the configurationFile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/configuration-files/{id}")
    public ResponseEntity<ConfigurationFile> getConfigurationFile(@PathVariable Long id) {
        log.debug("REST request to get ConfigurationFile : {}", id);
        Optional<ConfigurationFile> configurationFile = configurationFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(configurationFile);
    }

    /**
     * {@code DELETE  /configuration-files/:id} : delete the "id" configurationFile.
     *
     * @param id the id of the configurationFile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/configuration-files/{id}")
    public ResponseEntity<Void> deleteConfigurationFile(@PathVariable Long id) {
        log.debug("REST request to delete ConfigurationFile : {}", id);
        configurationFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
