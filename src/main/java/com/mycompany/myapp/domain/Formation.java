package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Formation.
 */
@Entity
@Table(name = "formation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Formation implements Serializable {

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

    @Column(name = "ouverture_inscription")
    private Instant ouvertureInscription;

    @Column(name = "fermeture_inscription")
    private Instant fermetureInscription;

    @Column(name = "seuil_inscrits")
    private Integer seuilInscrits;

    @Column(name = "tarif")
    private Float tarif;

    @ManyToOne(optional = false)
    @NotNull
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Formation id(Long id) {
        this.id = id;
        return this;
    }

    public String getLibille() {
        return this.libille;
    }

    public Formation libille(String libille) {
        this.libille = libille;
        return this;
    }

    public void setLibille(String libille) {
        this.libille = libille;
    }

    public String getDescription() {
        return this.description;
    }

    public Formation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getOuvertureInscription() {
        return this.ouvertureInscription;
    }

    public Formation ouvertureInscription(Instant ouvertureInscription) {
        this.ouvertureInscription = ouvertureInscription;
        return this;
    }

    public void setOuvertureInscription(Instant ouvertureInscription) {
        this.ouvertureInscription = ouvertureInscription;
    }

    public Instant getFermetureInscription() {
        return this.fermetureInscription;
    }

    public Formation fermetureInscription(Instant fermetureInscription) {
        this.fermetureInscription = fermetureInscription;
        return this;
    }

    public void setFermetureInscription(Instant fermetureInscription) {
        this.fermetureInscription = fermetureInscription;
    }

    public Integer getSeuilInscrits() {
        return this.seuilInscrits;
    }

    public Formation seuilInscrits(Integer seuilInscrits) {
        this.seuilInscrits = seuilInscrits;
        return this;
    }

    public void setSeuilInscrits(Integer seuilInscrits) {
        this.seuilInscrits = seuilInscrits;
    }

    public Float getTarif() {
        return this.tarif;
    }

    public Formation tarif(Float tarif) {
        this.tarif = tarif;
        return this;
    }

    public void setTarif(Float tarif) {
        this.tarif = tarif;
    }

    public Category getCategory() {
        return this.category;
    }

    public Formation category(Category category) {
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
        if (!(o instanceof Formation)) {
            return false;
        }
        return id != null && id.equals(((Formation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Formation{" +
            "id=" + getId() +
            ", libille='" + getLibille() + "'" +
            ", description='" + getDescription() + "'" +
            ", ouvertureInscription='" + getOuvertureInscription() + "'" +
            ", fermetureInscription='" + getFermetureInscription() + "'" +
            ", seuilInscrits=" + getSeuilInscrits() +
            ", tarif=" + getTarif() +
            "}";
    }
}
