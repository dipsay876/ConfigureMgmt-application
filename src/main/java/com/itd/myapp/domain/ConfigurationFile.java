package com.itd.myapp.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.itd.myapp.domain.enumeration.EnvironmentMaster;

import com.itd.myapp.domain.enumeration.Layer;

/**
 * ConfigurationFile entity.\n@author The DevOps team.
 */
@ApiModel(description = "ConfigurationFile entity.\n@author The DevOps team.")
@Entity
@Table(name = "configuration_file")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConfigurationFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "environment_name", nullable = false)
    private EnvironmentMaster environmentName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "layer_name", nullable = false)
    private Layer layerName;

    @NotNull
    @Column(name = "component_name", nullable = false)
    private String componentName;

    @NotNull
    @Column(name = "host_name", nullable = false)
    private String hostName;

    @NotNull
    @Column(name = "conf_file_name", nullable = false)
    private String confFileName;

    @Column(name = "conf_file_install_path")
    private String confFileInstallPath;

    @Column(name = "conf_file_upload_path")
    private String confFileUploadPath;

    @OneToOne
    @JoinColumn(unique = true)
    private ComponentMaster componentName;

    @OneToOne
    @JoinColumn(unique = true)
    private Host hostName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnvironmentMaster getEnvironmentName() {
        return environmentName;
    }

    public ConfigurationFile environmentName(EnvironmentMaster environmentName) {
        this.environmentName = environmentName;
        return this;
    }

    public void setEnvironmentName(EnvironmentMaster environmentName) {
        this.environmentName = environmentName;
    }

    public Layer getLayerName() {
        return layerName;
    }

    public ConfigurationFile layerName(Layer layerName) {
        this.layerName = layerName;
        return this;
    }

    public void setLayerName(Layer layerName) {
        this.layerName = layerName;
    }

    public String getComponentName() {
        return componentName;
    }

    public ConfigurationFile componentName(String componentName) {
        this.componentName = componentName;
        return this;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getHostName() {
        return hostName;
    }

    public ConfigurationFile hostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getConfFileName() {
        return confFileName;
    }

    public ConfigurationFile confFileName(String confFileName) {
        this.confFileName = confFileName;
        return this;
    }

    public void setConfFileName(String confFileName) {
        this.confFileName = confFileName;
    }

    public String getConfFileInstallPath() {
        return confFileInstallPath;
    }

    public ConfigurationFile confFileInstallPath(String confFileInstallPath) {
        this.confFileInstallPath = confFileInstallPath;
        return this;
    }

    public void setConfFileInstallPath(String confFileInstallPath) {
        this.confFileInstallPath = confFileInstallPath;
    }

    public String getConfFileUploadPath() {
        return confFileUploadPath;
    }

    public ConfigurationFile confFileUploadPath(String confFileUploadPath) {
        this.confFileUploadPath = confFileUploadPath;
        return this;
    }

    public void setConfFileUploadPath(String confFileUploadPath) {
        this.confFileUploadPath = confFileUploadPath;
    }

    public ComponentMaster getComponentName() {
        return componentName;
    }

    public ConfigurationFile componentName(ComponentMaster componentMaster) {
        this.componentName = componentMaster;
        return this;
    }

    public void setComponentName(ComponentMaster componentMaster) {
        this.componentName = componentMaster;
    }

    public Host getHostName() {
        return hostName;
    }

    public ConfigurationFile hostName(Host host) {
        this.hostName = host;
        return this;
    }

    public void setHostName(Host host) {
        this.hostName = host;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfigurationFile)) {
            return false;
        }
        return id != null && id.equals(((ConfigurationFile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ConfigurationFile{" +
            "id=" + getId() +
            ", environmentName='" + getEnvironmentName() + "'" +
            ", layerName='" + getLayerName() + "'" +
            ", componentName='" + getComponentName() + "'" +
            ", hostName='" + getHostName() + "'" +
            ", confFileName='" + getConfFileName() + "'" +
            ", confFileInstallPath='" + getConfFileInstallPath() + "'" +
            ", confFileUploadPath='" + getConfFileUploadPath() + "'" +
            "}";
    }
}
