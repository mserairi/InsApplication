package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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
    @Column(name = "libille", nullable = false)
    private String libille;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "souscategory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "souscategory", "groupes" }, allowSetters = true)
    private Set<Cours> cours = new HashSet<>();

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

    public String getLibille() {
        return this.libille;
    }

    public SousCategory libille(String libille) {
        this.libille = libille;
        return this;
    }

    public void setLibille(String libille) {
        this.libille = libille;
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

    public Set<Cours> getCours() {
        return this.cours;
    }

    public SousCategory cours(Set<Cours> cours) {
        this.setCours(cours);
        return this;
    }

    public SousCategory addCours(Cours cours) {
        this.cours.add(cours);
        cours.setSouscategory(this);
        return this;
    }

    public SousCategory removeCours(Cours cours) {
        this.cours.remove(cours);
        cours.setSouscategory(null);
        return this;
    }

    public void setCours(Set<Cours> cours) {
        if (this.cours != null) {
            this.cours.forEach(i -> i.setSouscategory(null));
        }
        if (cours != null) {
            cours.forEach(i -> i.setSouscategory(this));
        }
        this.cours = cours;
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
            ", libille='" + getLibille() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
