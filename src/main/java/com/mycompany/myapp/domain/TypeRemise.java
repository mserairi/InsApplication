package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TypeRemise.
 */
@Entity
@Table(name = "type_remise")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TypeRemise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "libille")
    private String libille;

    @Column(name = "condition")
    private String condition;

    @Column(name = "mantant_libre")
    private Boolean mantantLibre;

    @Column(name = "montant_unitaire")
    private Float montantUnitaire;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeRemise id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public TypeRemise numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getLibille() {
        return this.libille;
    }

    public TypeRemise libille(String libille) {
        this.libille = libille;
        return this;
    }

    public void setLibille(String libille) {
        this.libille = libille;
    }

    public String getCondition() {
        return this.condition;
    }

    public TypeRemise condition(String condition) {
        this.condition = condition;
        return this;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Boolean getMantantLibre() {
        return this.mantantLibre;
    }

    public TypeRemise mantantLibre(Boolean mantantLibre) {
        this.mantantLibre = mantantLibre;
        return this;
    }

    public void setMantantLibre(Boolean mantantLibre) {
        this.mantantLibre = mantantLibre;
    }

    public Float getMontantUnitaire() {
        return this.montantUnitaire;
    }

    public TypeRemise montantUnitaire(Float montantUnitaire) {
        this.montantUnitaire = montantUnitaire;
        return this;
    }

    public void setMontantUnitaire(Float montantUnitaire) {
        this.montantUnitaire = montantUnitaire;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeRemise)) {
            return false;
        }
        return id != null && id.equals(((TypeRemise) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeRemise{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", libille='" + getLibille() + "'" +
            ", condition='" + getCondition() + "'" +
            ", mantantLibre='" + getMantantLibre() + "'" +
            ", montantUnitaire=" + getMontantUnitaire() +
            "}";
    }
}
