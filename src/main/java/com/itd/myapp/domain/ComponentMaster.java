package com.itd.myapp.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * Component entity.\n@author The DevOps team.
 */
@ApiModel(description = "Component entity.\n@author The DevOps team.")
@Entity
@Table(name = "component_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ComponentMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "component_name", nullable = false)
    private String componentName;

    @Column(name = "component_desc")
    private String componentDesc;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComponentName() {
        return componentName;
    }

    public ComponentMaster componentName(String componentName) {
        this.componentName = componentName;
        return this;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getComponentDesc() {
        return componentDesc;
    }

    public ComponentMaster componentDesc(String componentDesc) {
        this.componentDesc = componentDesc;
        return this;
    }

    public void setComponentDesc(String componentDesc) {
        this.componentDesc = componentDesc;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComponentMaster)) {
            return false;
        }
        return id != null && id.equals(((ComponentMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ComponentMaster{" +
            "id=" + getId() +
            ", componentName='" + getComponentName() + "'" +
            ", componentDesc='" + getComponentDesc() + "'" +
            "}";
    }
}
