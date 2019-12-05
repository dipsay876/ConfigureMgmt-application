package com.itd.myapp.web.rest;

import com.itd.myapp.domain.Host;
import com.itd.myapp.service.HostService;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.itd.myapp.domain.Host}.
 */
@RestController
@RequestMapping("/api")
public class HostResource {

    private final Logger log = LoggerFactory.getLogger(HostResource.class);

    private static final String ENTITY_NAME = "configureMgmtSampleApplicationHost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HostService hostService;

    public HostResource(HostService hostService) {
        this.hostService = hostService;
    }

    /**
     * {@code POST  /hosts} : Create a new host.
     *
     * @param host the host to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new host, or with status {@code 400 (Bad Request)} if the host has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hosts")
    public ResponseEntity<Host> createHost(@Valid @RequestBody Host host) throws URISyntaxException {
        log.debug("REST request to save Host : {}", host);
        if (host.getId() != null) {
            throw new BadRequestAlertException("A new host cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Host result = hostService.save(host);
        return ResponseEntity.created(new URI("/api/hosts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hosts} : Updates an existing host.
     *
     * @param host the host to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated host,
     * or with status {@code 400 (Bad Request)} if the host is not valid,
     * or with status {@code 500 (Internal Server Error)} if the host couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hosts")
    public ResponseEntity<Host> updateHost(@Valid @RequestBody Host host) throws URISyntaxException {
        log.debug("REST request to update Host : {}", host);
        if (host.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Host result = hostService.save(host);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, host.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /hosts} : get all the hosts.
     *

     * @param pageable the pagination information.

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hosts in body.
     */
    @GetMapping("/hosts")
    public ResponseEntity<List<Host>> getAllHosts(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("hostname-is-null".equals(filter)) {
            log.debug("REST request to get all Hosts where hostName is null");
            return new ResponseEntity<>(hostService.findAllWhereHostNameIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Hosts");
        Page<Host> page = hostService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hosts/:id} : get the "id" host.
     *
     * @param id the id of the host to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the host, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hosts/{id}")
    public ResponseEntity<Host> getHost(@PathVariable Long id) {
        log.debug("REST request to get Host : {}", id);
        Optional<Host> host = hostService.findOne(id);
        return ResponseUtil.wrapOrNotFound(host);
    }

    /**
     * {@code DELETE  /hosts/:id} : delete the "id" host.
     *
     * @param id the id of the host to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hosts/{id}")
    public ResponseEntity<Void> deleteHost(@PathVariable Long id) {
        log.debug("REST request to delete Host : {}", id);
        hostService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
