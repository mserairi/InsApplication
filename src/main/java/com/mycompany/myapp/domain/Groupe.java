package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Groupe.
 */
@Entity
@Table(name = "groupe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Groupe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "libille")
    private String libille;

    @Column(name = "lasession")
    private String lasession;

    @Column(name = "nbr_apprenant")
    private Integer nbrApprenant;

    @OneToMany(mappedBy = "groupe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "salle", "groupe" }, allowSetters = true)
    private Set<Creneau> creneaus = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "souscategory", "groupes" }, allowSetters = true)
    private Cours cours;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_groupe__enfant",
        joinColumns = @JoinColumn(name = "groupe_id"),
        inverseJoinColumns = @JoinColumn(name = "enfant_id")
    )
    @JsonIgnoreProperties(value = { "parent" }, allowSetters = true)
    private Set<Enfant> enfants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Groupe id(Long id) {
        this.id = id;
        return this;
    }

    public String getNumero() {
        return this.numero;
    }

    public Groupe numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLibille() {
        return this.libille;
    }

    public Groupe libille(String libille) {
        this.libille = libille;
        return this;
    }

    public void setLibille(String libille) {
        this.libille = libille;
    }

    public String getLasession() {
        return this.lasession;
    }

    public Groupe lasession(String lasession) {
        this.lasession = lasession;
        return this;
    }

    public void setLasession(String lasession) {
        this.lasession = lasession;
    }

    public Integer getNbrApprenant() {
        return this.nbrApprenant;
    }

    public Groupe nbrApprenant(Integer nbrApprenant) {
        this.nbrApprenant = nbrApprenant;
        return this;
    }

    public void setNbrApprenant(Integer nbrApprenant) {
        this.nbrApprenant = nbrApprenant;
    }

    public Set<Creneau> getCreneaus() {
        return this.creneaus;
    }

    public Groupe creneaus(Set<Creneau> creneaus) {
        this.setCreneaus(creneaus);
        return this;
    }

    public Groupe addCreneau(Creneau creneau) {
        this.creneaus.add(creneau);
        creneau.setGroupe(this);
        return this;
    }

    public Groupe removeCreneau(Creneau creneau) {
        this.creneaus.remove(creneau);
        creneau.setGroupe(null);
        return this;
    }

    public void setCreneaus(Set<Creneau> creneaus) {
        if (this.creneaus != null) {
            this.creneaus.forEach(i -> i.setGroupe(null));
        }
        if (creneaus != null) {
            creneaus.forEach(i -> i.setGroupe(this));
        }
        this.creneaus = creneaus;
    }

    public Cours getCours() {
        return this.cours;
    }

    public Groupe cours(Cours cours) {
        this.setCours(cours);
        return this;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public Set<Enfant> getEnfants() {
        return this.enfants;
    }

    public Groupe enfants(Set<Enfant> enfants) {
        this.setEnfants(enfants);
        return this;
    }

    public Groupe addEnfant(Enfant enfant) {
        this.enfants.add(enfant);
        return this;
    }

    public Groupe removeEnfant(Enfant enfant) {
        this.enfants.remove(enfant);
        return this;
    }

    public void setEnfants(Set<Enfant> enfants) {
        this.enfants = enfants;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Groupe)) {
            return false;
        }
        return id != null && id.equals(((Groupe) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Groupe{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", libille='" + getLibille() + "'" +
            ", lasession='" + getLasession() + "'" +
            ", nbrApprenant=" + getNbrApprenant() +
            "}";
    }
}
