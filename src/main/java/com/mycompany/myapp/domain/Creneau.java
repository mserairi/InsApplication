package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.JourSemaine;
import com.mycompany.myapp.domain.enumeration.TYPECRENEAU;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Creneau.
 */
@Entity
@Table(name = "creneau")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Creneau implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_creneau", nullable = false)
    private TYPECRENEAU typeCreneau;

    @Enumerated(EnumType.STRING)
    @Column(name = "jour")
    private JourSemaine jour;

    @Column(name = "deb")
    private Instant deb;

    @Column(name = "fin")
    private Instant fin;

    @OneToOne
    @JoinColumn(unique = true)
    private Salle salle;

    @ManyToOne
    @JsonIgnoreProperties(value = { "creneaus", "cours", "enfants" }, allowSetters = true)
    private Groupe groupe;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Creneau id(Long id) {
        this.id = id;
        return this;
    }

    public TYPECRENEAU getTypeCreneau() {
        return this.typeCreneau;
    }

    public Creneau typeCreneau(TYPECRENEAU typeCreneau) {
        this.typeCreneau = typeCreneau;
        return this;
    }

    public void setTypeCreneau(TYPECRENEAU typeCreneau) {
        this.typeCreneau = typeCreneau;
    }

    public JourSemaine getJour() {
        return this.jour;
    }

    public Creneau jour(JourSemaine jour) {
        this.jour = jour;
        return this;
    }

    public void setJour(JourSemaine jour) {
        this.jour = jour;
    }

    public Instant getDeb() {
        return this.deb;
    }

    public Creneau deb(Instant deb) {
        this.deb = deb;
        return this;
    }

    public void setDeb(Instant deb) {
        this.deb = deb;
    }

    public Instant getFin() {
        return this.fin;
    }

    public Creneau fin(Instant fin) {
        this.fin = fin;
        return this;
    }

    public void setFin(Instant fin) {
        this.fin = fin;
    }

    public Salle getSalle() {
        return this.salle;
    }

    public Creneau salle(Salle salle) {
        this.setSalle(salle);
        return this;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Groupe getGroupe() {
        return this.groupe;
    }

    public Creneau groupe(Groupe groupe) {
        this.setGroupe(groupe);
        return this;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Creneau)) {
            return false;
        }
        return id != null && id.equals(((Creneau) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Creneau{" +
            "id=" + getId() +
            ", typeCreneau='" + getTypeCreneau() + "'" +
            ", jour='" + getJour() + "'" +
            ", deb='" + getDeb() + "'" +
            ", fin='" + getFin() + "'" +
            "}";
    }
}
