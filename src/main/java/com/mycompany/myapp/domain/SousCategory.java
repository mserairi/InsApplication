package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SousCategory.
 */
@Entity
@Table(name = "sous_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SousCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libile", nullable = false)
    private String libile;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SousCategory id(Long id) {
        this.id = id;
        return this;
    }

    public String getLibile() {
        return this.libile;
    }

    public SousCategory libile(String libile) {
        this.libile = libile;
        return this;
    }

    public void setLibile(String libile) {
        this.libile = libile;
    }

    public String getDescription() {
        return this.description;
    }

    public SousCategory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return this.category;
    }

    public SousCategory category(Category category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SousCategory)) {
            return false;
        }
        return id != null && id.equals(((SousCategory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SousCategory{" +
            "id=" + getId() +
            ", libile='" + getLibile() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
