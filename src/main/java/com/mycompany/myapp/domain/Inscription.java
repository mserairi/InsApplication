package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.EtatInscription;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Inscription.
 */
@Entity
@Table(name = "inscription")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Inscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "dateinscription")
    private Instant dateinscription;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EtatInscription status;

    @Column(name = "remarques")
    private String remarques;

    @Column(name = "insto_lat")
    private Boolean instoLAT;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "parent" }, allowSetters = true)
    private Enfant inscrit;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "category" }, allowSetters = true)
    private Formation formation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Inscription id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getDateinscription() {
        return this.dateinscription;
    }

    public Inscription dateinscription(Instant dateinscription) {
        this.dateinscription = dateinscription;
        return this;
    }

    public void setDateinscription(Instant dateinscription) {
        this.dateinscription = dateinscription;
    }

    public EtatInscription getStatus() {
        return this.status;
    }

    public Inscription status(EtatInscription status) {
        this.status = status;
        return this;
    }

    public void setStatus(EtatInscription status) {
        this.status = status;
    }

    public String getRemarques() {
        return this.remarques;
    }

    public Inscription remarques(String remarques) {
        this.remarques = remarques;
        return this;
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }

    public Boolean getInstoLAT() {
        return this.instoLAT;
    }

    public Inscription instoLAT(Boolean instoLAT) {
        this.instoLAT = instoLAT;
        return this;
    }

    public void setInstoLAT(Boolean instoLAT) {
        this.instoLAT = instoLAT;
    }

    public Enfant getInscrit() {
        return this.inscrit;
    }

    public Inscription inscrit(Enfant enfant) {
        this.setInscrit(enfant);
        return this;
    }

    public void setInscrit(Enfant enfant) {
        this.inscrit = enfant;
    }

    public Formation getFormation() {
        return this.formation;
    }

    public Inscription formation(Formation formation) {
        this.setFormation(formation);
        return this;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inscription)) {
            return false;
        }
        return id != null && id.equals(((Inscription) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inscription{" +
            "id=" + getId() +
            ", dateinscription='" + getDateinscription() + "'" +
            ", status='" + getStatus() + "'" +
            ", remarques='" + getRemarques() + "'" +
            ", instoLAT='" + getInstoLAT() + "'" +
            "}";
    }
}
