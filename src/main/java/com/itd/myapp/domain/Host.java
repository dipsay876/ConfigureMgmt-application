package com.itd.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.itd.myapp.domain.enumeration.Layer;

import com.itd.myapp.domain.enumeration.OS;

/**
 * Host entity.\n@author The DevOps team.
 */
@ApiModel(description = "Host entity.\n@author The DevOps team.")
@Entity
@Table(name = "host")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Host implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "layer_name", nullable = false)
    private Layer layerName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "os_name", nullable = false)
    private OS osName;

    @NotNull
    @Column(name = "os_version", nullable = false)
    private String osVersion;

    @NotNull
    @Column(name = "host_name", nullable = false)
    private String hostName;

    @Column(name = "host_ip")
    private String hostIP;

    @Column(name = "host_cpu")
    private Long hostCPU;

    @Column(name = "host_memory_mb")
    private Long hostMemoryMB;

    @Column(name = "host_hddgb")
    private Long hostHDDGB;

    @OneToOne(mappedBy = "hostName")
    @JsonIgnore
    private ConfigurationFile hostName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Layer getLayerName() {
        return layerName;
    }

    public Host layerName(Layer layerName) {
        this.layerName = layerName;
        return this;
    }

    public void setLayerName(Layer layerName) {
        this.layerName = layerName;
    }

    public OS getOsName() {
        return osName;
    }

    public Host osName(OS osName) {
        this.osName = osName;
        return this;
    }

    public void setOsName(OS osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public Host osVersion(String osVersion) {
        this.osVersion = osVersion;
        return this;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getHostName() {
        return hostName;
    }

    public Host hostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostIP() {
        return hostIP;
    }

    public Host hostIP(String hostIP) {
        this.hostIP = hostIP;
        return this;
    }

    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }

    public Long getHostCPU() {
        return hostCPU;
    }

    public Host hostCPU(Long hostCPU) {
        this.hostCPU = hostCPU;
        return this;
    }

    public void setHostCPU(Long hostCPU) {
        this.hostCPU = hostCPU;
    }

    public Long getHostMemoryMB() {
        return hostMemoryMB;
    }

    public Host hostMemoryMB(Long hostMemoryMB) {
        this.hostMemoryMB = hostMemoryMB;
        return this;
    }

    public void setHostMemoryMB(Long hostMemoryMB) {
        this.hostMemoryMB = hostMemoryMB;
    }

    public Long getHostHDDGB() {
        return hostHDDGB;
    }

    public Host hostHDDGB(Long hostHDDGB) {
        this.hostHDDGB = hostHDDGB;
        return this;
    }

    public void setHostHDDGB(Long hostHDDGB) {
        this.hostHDDGB = hostHDDGB;
    }

    public ConfigurationFile getHostName() {
        return hostName;
    }

    public Host hostName(ConfigurationFile configurationFile) {
        this.hostName = configurationFile;
        return this;
    }

    public void setHostName(ConfigurationFile configurationFile) {
        this.hostName = configurationFile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Host)) {
            return false;
        }
        return id != null && id.equals(((Host) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Host{" +
            "id=" + getId() +
            ", layerName='" + getLayerName() + "'" +
            ", osName='" + getOsName() + "'" +
            ", osVersion='" + getOsVersion() + "'" +
            ", hostName='" + getHostName() + "'" +
            ", hostIP='" + getHostIP() + "'" +
            ", hostCPU=" + getHostCPU() +
            ", hostMemoryMB=" + getHostMemoryMB() +
            ", hostHDDGB=" + getHostHDDGB() +
            "}";
    }
}
