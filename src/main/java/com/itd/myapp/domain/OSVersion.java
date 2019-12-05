package com.itd.myapp.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.itd.myapp.domain.enumeration.OS;

/**
 * OSVersion entity.\n@author The DevOps team.
 */
@ApiModel(description = "OSVersion entity.\n@author The DevOps team.")
@Entity
@Table(name = "os_version")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OSVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "os_name", nullable = false)
    private OS osName;

    @NotNull
    @Column(name = "os_version", nullable = false)
    private String osVersion;

    @Column(name = "os_ver_desc")
    private String osVerDesc;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OS getOsName() {
        return osName;
    }

    public OSVersion osName(OS osName) {
        this.osName = osName;
        return this;
    }

    public void setOsName(OS osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public OSVersion osVersion(String osVersion) {
        this.osVersion = osVersion;
        return this;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getOsVerDesc() {
        return osVerDesc;
    }

    public OSVersion osVerDesc(String osVerDesc) {
        this.osVerDesc = osVerDesc;
        return this;
    }

    public void setOsVerDesc(String osVerDesc) {
        this.osVerDesc = osVerDesc;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OSVersion)) {
            return false;
        }
        return id != null && id.equals(((OSVersion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OSVersion{" +
            "id=" + getId() +
            ", osName='" + getOsName() + "'" +
            ", osVersion='" + getOsVersion() + "'" +
            ", osVerDesc='" + getOsVerDesc() + "'" +
            "}";
    }
}
