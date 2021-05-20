package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.PERIODICITE;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cours.
 */
@Entity
@Table(name = "cours")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "libille")
    private String libille;

    @Column(name = "description")
    private String description;

    @Column(name = "seuil")
    private Integer seuil;

    @Column(name = "duree")
    private Integer duree;

    @Enumerated(EnumType.STRING)
    @Column(name = "periode")
    private PERIODICITE periode;

    @Column(name = "frequence")
    private Integer frequence;

    @Column(name = "agiminrec")
    private Integer agiminrec;

    @Column(name = "agemaxrec")
    private Integer agemaxrec;

    @ManyToOne
    @JsonIgnoreProperties(value = { "category", "cours" }, allowSetters = true)
    private SousCategory souscategory;

    @OneToMany(mappedBy = "cours")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "creneaus", "cours", "enfants" }, allowSetters = true)
    private Set<Groupe> groupes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cours id(Long id) {
        this.id = id;
        return this;
    }

    public String getNumero() {
        return this.numero;
    }

    public Cours numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLibille() {
        return this.libille;
    }

    public Cours libille(String libille) {
        this.libille = libille;
        return this;
    }

    public void setLibille(String libille) {
        this.libille = libille;
    }

    public String getDescription() {
        return this.description;
    }

    public Cours description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSeuil() {
        return this.seuil;
    }

    public Cours seuil(Integer seuil) {
        this.seuil = seuil;
        return this;
    }

    public void setSeuil(Integer seuil) {
        this.seuil = seuil;
    }

    public Integer getDuree() {
        return this.duree;
    }

    public Cours duree(Integer duree) {
        this.duree = duree;
        return this;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public PERIODICITE getPeriode() {
        return this.periode;
    }

    public Cours periode(PERIODICITE periode) {
        this.periode = periode;
        return this;
    }

    public void setPeriode(PERIODICITE periode) {
        this.periode = periode;
    }

    public Integer getFrequence() {
        return this.frequence;
    }

    public Cours frequence(Integer frequence) {
        this.frequence = frequence;
        return this;
    }

    public void setFrequence(Integer frequence) {
        this.frequence = frequence;
    }

    public Integer getAgiminrec() {
        return this.agiminrec;
    }

    public Cours agiminrec(Integer agiminrec) {
        this.agiminrec = agiminrec;
        return this;
    }

    public void setAgiminrec(Integer agiminrec) {
        this.agiminrec = agiminrec;
    }

    public Integer getAgemaxrec() {
        return this.agemaxrec;
    }

    public Cours agemaxrec(Integer agemaxrec) {
        this.agemaxrec = agemaxrec;
        return this;
    }

    public void setAgemaxrec(Integer agemaxrec) {
        this.agemaxrec = agemaxrec;
    }

    public SousCategory getSouscategory() {
        return this.souscategory;
    }

    public Cours souscategory(SousCategory sousCategory) {
        this.setSouscategory(sousCategory);
        return this;
    }

    public void setSouscategory(SousCategory sousCategory) {
        this.souscategory = sousCategory;
    }

    public Set<Groupe> getGroupes() {
        return this.groupes;
    }

    public Cours groupes(Set<Groupe> groupes) {
        this.setGroupes(groupes);
        return this;
    }

    public Cours addGroupe(Groupe groupe) {
        this.groupes.add(groupe);
        groupe.setCours(this);
        return this;
    }

    public Cours removeGroupe(Groupe groupe) {
        this.groupes.remove(groupe);
        groupe.setCours(null);
        return this;
    }

    public void setGroupes(Set<Groupe> groupes) {
        if (this.groupes != null) {
            this.groupes.forEach(i -> i.setCours(null));
        }
        if (groupes != null) {
            groupes.forEach(i -> i.setCours(this));
        }
        this.groupes = groupes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cours)) {
            return false;
        }
        return id != null && id.equals(((Cours) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cours{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", libille='" + getLibille() + "'" +
            ", description='" + getDescription() + "'" +
            ", seuil=" + getSeuil() +
            ", duree=" + getDuree() +
            ", periode='" + getPeriode() + "'" +
            ", frequence=" + getFrequence() +
            ", agiminrec=" + getAgiminrec() +
            ", agemaxrec=" + getAgemaxrec() +
            "}";
    }
}
