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
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category implements Serializable {

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

    @Column(name = "tarif")
    private Float tarif;

    @ManyToOne
    @JsonIgnoreProperties(value = { "sousCat", "inscrits" }, allowSetters = true)
    private Category sousCat;

    @ManyToMany(mappedBy = "suivres")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "suivres", "parents" }, allowSetters = true)
    private Set<Enfant> inscrits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category id(Long id) {
        this.id = id;
        return this;
    }

    public String getLibile() {
        return this.libile;
    }

    public Category libile(String libile) {
        this.libile = libile;
        return this;
    }

    public void setLibile(String libile) {
        this.libile = libile;
    }

    public String getDescription() {
        return this.description;
    }

    public Category description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getTarif() {
        return this.tarif;
    }

    public Category tarif(Float tarif) {
        this.tarif = tarif;
        return this;
    }

    public void setTarif(Float tarif) {
        this.tarif = tarif;
    }

    public Category getSousCat() {
        return this.sousCat;
    }

    public Category sousCat(Category category) {
        this.setSousCat(category);
        return this;
    }

    public void setSousCat(Category category) {
        this.sousCat = category;
    }

    public Set<Enfant> getInscrits() {
        return this.inscrits;
    }

    public Category inscrits(Set<Enfant> enfants) {
        this.setInscrits(enfants);
        return this;
    }

    public Category addInscrits(Enfant enfant) {
        this.inscrits.add(enfant);
        enfant.getSuivres().add(this);
        return this;
    }

    public Category removeInscrits(Enfant enfant) {
        this.inscrits.remove(enfant);
        enfant.getSuivres().remove(this);
        return this;
    }

    public void setInscrits(Set<Enfant> enfants) {
        if (this.inscrits != null) {
            this.inscrits.forEach(i -> i.removeSuivre(this));
        }
        if (enfants != null) {
            enfants.forEach(i -> i.addSuivre(this));
        }
        this.inscrits = enfants;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", libile='" + getLibile() + "'" +
            ", description='" + getDescription() + "'" +
            ", tarif=" + getTarif() +
            "}";
    }
}
