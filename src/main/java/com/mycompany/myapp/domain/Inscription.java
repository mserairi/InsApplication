package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
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

    @NotNull
    @Column(name = "lasession", nullable = false)
    private String lasession;

    @ManyToOne
    @JsonIgnoreProperties(value = { "sousCat" }, allowSetters = true)
    private Category concerne;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_inscription__inscrits",
        joinColumns = @JoinColumn(name = "inscription_id"),
        inverseJoinColumns = @JoinColumn(name = "inscrits_id")
    )
    @JsonIgnoreProperties(value = { "parents", "suivres" }, allowSetters = true)
    private Set<Enfant> inscrits = new HashSet<>();

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

    public String getLasession() {
        return this.lasession;
    }

    public Inscription lasession(String lasession) {
        this.lasession = lasession;
        return this;
    }

    public void setLasession(String lasession) {
        this.lasession = lasession;
    }

    public Category getConcerne() {
        return this.concerne;
    }

    public Inscription concerne(Category category) {
        this.setConcerne(category);
        return this;
    }

    public void setConcerne(Category category) {
        this.concerne = category;
    }

    public Set<Enfant> getInscrits() {
        return this.inscrits;
    }

    public Inscription inscrits(Set<Enfant> enfants) {
        this.setInscrits(enfants);
        return this;
    }

    public Inscription addInscrits(Enfant enfant) {
        this.inscrits.add(enfant);
        enfant.getSuivres().add(this);
        return this;
    }

    public Inscription removeInscrits(Enfant enfant) {
        this.inscrits.remove(enfant);
        enfant.getSuivres().remove(this);
        return this;
    }

    public void setInscrits(Set<Enfant> enfants) {
        this.inscrits = enfants;
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
            ", lasession='" + getLasession() + "'" +
            "}";
    }
}
