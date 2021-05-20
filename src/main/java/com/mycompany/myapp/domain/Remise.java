package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Remise.
 */
@Entity
@Table(name = "remise")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Remise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "montant")
    private Integer montant;

    @Column(name = "descreption")
    private String descreption;

    @ManyToOne
    private TypeRemise typeremise;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Remise id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public Remise numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getMontant() {
        return this.montant;
    }

    public Remise montant(Integer montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Integer montant) {
        this.montant = montant;
    }

    public String getDescreption() {
        return this.descreption;
    }

    public Remise descreption(String descreption) {
        this.descreption = descreption;
        return this;
    }

    public void setDescreption(String descreption) {
        this.descreption = descreption;
    }

    public TypeRemise getTyperemise() {
        return this.typeremise;
    }

    public Remise typeremise(TypeRemise typeRemise) {
        this.setTyperemise(typeRemise);
        return this;
    }

    public void setTyperemise(TypeRemise typeRemise) {
        this.typeremise = typeRemise;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Remise)) {
            return false;
        }
        return id != null && id.equals(((Remise) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Remise{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", montant=" + getMontant() +
            ", descreption='" + getDescreption() + "'" +
            "}";
    }
}
