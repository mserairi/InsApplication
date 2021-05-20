package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Etatcommande;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CommandeInscriptions.
 */
@Entity
@Table(name = "commande_inscriptions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CommandeInscriptions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "total_avant_remise")
    private Float totalAvantRemise;

    @Column(name = "total_remise")
    private Float totalRemise;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Etatcommande status;

    @OneToOne
    @JoinColumn(unique = true)
    private Facture facture;

    @ManyToOne
    @JsonIgnoreProperties(value = { "concerne", "inscrits" }, allowSetters = true)
    private Inscription inscription;

    @ManyToOne
    @JsonIgnoreProperties(value = { "typeremise" }, allowSetters = true)
    private Remise remise;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CommandeInscriptions id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public CommandeInscriptions numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Float getTotalAvantRemise() {
        return this.totalAvantRemise;
    }

    public CommandeInscriptions totalAvantRemise(Float totalAvantRemise) {
        this.totalAvantRemise = totalAvantRemise;
        return this;
    }

    public void setTotalAvantRemise(Float totalAvantRemise) {
        this.totalAvantRemise = totalAvantRemise;
    }

    public Float getTotalRemise() {
        return this.totalRemise;
    }

    public CommandeInscriptions totalRemise(Float totalRemise) {
        this.totalRemise = totalRemise;
        return this;
    }

    public void setTotalRemise(Float totalRemise) {
        this.totalRemise = totalRemise;
    }

    public Etatcommande getStatus() {
        return this.status;
    }

    public CommandeInscriptions status(Etatcommande status) {
        this.status = status;
        return this;
    }

    public void setStatus(Etatcommande status) {
        this.status = status;
    }

    public Facture getFacture() {
        return this.facture;
    }

    public CommandeInscriptions facture(Facture facture) {
        this.setFacture(facture);
        return this;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public Inscription getInscription() {
        return this.inscription;
    }

    public CommandeInscriptions inscription(Inscription inscription) {
        this.setInscription(inscription);
        return this;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public Remise getRemise() {
        return this.remise;
    }

    public CommandeInscriptions remise(Remise remise) {
        this.setRemise(remise);
        return this;
    }

    public void setRemise(Remise remise) {
        this.remise = remise;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandeInscriptions)) {
            return false;
        }
        return id != null && id.equals(((CommandeInscriptions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandeInscriptions{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", totalAvantRemise=" + getTotalAvantRemise() +
            ", totalRemise=" + getTotalRemise() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
