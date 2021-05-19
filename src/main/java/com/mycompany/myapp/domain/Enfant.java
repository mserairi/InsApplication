package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Enfant.
 */
@Entity
@Table(name = "enfant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Enfant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "age")
    private Integer age;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_enfant__parent",
        joinColumns = @JoinColumn(name = "enfant_id"),
        inverseJoinColumns = @JoinColumn(name = "parent_id")
    )
    private Set<User> parents = new HashSet<>();

    @ManyToMany(mappedBy = "inscrits")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "concerne", "inscrits" }, allowSetters = true)
    private Set<Inscription> suivres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Enfant id(Long id) {
        this.id = id;
        return this;
    }

    public String getNom() {
        return this.nom;
    }

    public Enfant nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Enfant prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getAge() {
        return this.age;
    }

    public Enfant age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<User> getParents() {
        return this.parents;
    }

    public Enfant parents(Set<User> users) {
        this.setParents(users);
        return this;
    }

    public Enfant addParent(User user) {
        this.parents.add(user);
        return this;
    }

    public Enfant removeParent(User user) {
        this.parents.remove(user);
        return this;
    }

    public void setParents(Set<User> users) {
        this.parents = users;
    }

    public Set<Inscription> getSuivres() {
        return this.suivres;
    }

    public Enfant suivres(Set<Inscription> inscriptions) {
        this.setSuivres(inscriptions);
        return this;
    }

    public Enfant addSuivre(Inscription inscription) {
        this.suivres.add(inscription);
        inscription.getInscrits().add(this);
        return this;
    }

    public Enfant removeSuivre(Inscription inscription) {
        this.suivres.remove(inscription);
        inscription.getInscrits().remove(this);
        return this;
    }

    public void setSuivres(Set<Inscription> inscriptions) {
        if (this.suivres != null) {
            this.suivres.forEach(i -> i.removeInscrits(this));
        }
        if (inscriptions != null) {
            inscriptions.forEach(i -> i.addInscrits(this));
        }
        this.suivres = inscriptions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Enfant)) {
            return false;
        }
        return id != null && id.equals(((Enfant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Enfant{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", age=" + getAge() +
            "}";
    }
}
