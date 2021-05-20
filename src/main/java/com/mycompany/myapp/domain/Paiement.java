package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.TypePaiement;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Paiement.
 */
@Entity
@Table(name = "paiement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Paiement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "date")
    private Instant date;

    @Column(name = "montant")
    private Float montant;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypePaiement type;

    @ManyToOne
    private Facture facture;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paiement id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public Paiement numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Instant getDate() {
        return this.date;
    }

    public Paiement date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Float getMontant() {
        return this.montant;
    }

    public Paiement montant(Float montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public TypePaiement getType() {
        return this.type;
    }

    public Paiement type(TypePaiement type) {
        this.type = type;
        return this;
    }

    public void setType(TypePaiement type) {
        this.type = type;
    }

    public Facture getFacture() {
        return this.facture;
    }

    public Paiement facture(Facture facture) {
        this.setFacture(facture);
        return this;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paiement)) {
            return false;
        }
        return id != null && id.equals(((Paiement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paiement{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", date='" + getDate() + "'" +
            ", montant=" + getMontant() +
            ", type='" + getType() + "'" +
            "}";
    }
}
