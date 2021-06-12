package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.TypeGenre;
import java.io.Serializable;
import java.time.Instant;
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

    @Column(name = "date_naissance")
    private Instant dateNaissance;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private TypeGenre genre;

    @Column(name = "nom_parent_2")
    private String nomParent2;

    @Column(name = "prenom_parent_2")
    private String prenomParent2;

    @Pattern(regexp = "^\\d{10,10}$")
    @Column(name = "mob_parent_2")
    private String mobParent2;

    @Pattern(regexp = "^.+@.+$")
    @Column(name = "email_parent_2")
    private String emailParent2;

    @Column(name = "info_sante")
    private String infoSante;

    @Column(name = "autorisation_image")
    private Boolean autorisationImage;

    @Column(name = "nom_contact")
    private String nomContact;

    @Pattern(regexp = "^\\d{10,10}$")
    @Column(name = "mob_contact")
    private String mobContact;

    @ManyToOne(optional = false)
    @NotNull
    private User parent;

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

    public Instant getDateNaissance() {
        return this.dateNaissance;
    }

    public Enfant dateNaissance(Instant dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(Instant dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public TypeGenre getGenre() {
        return this.genre;
    }

    public Enfant genre(TypeGenre genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(TypeGenre genre) {
        this.genre = genre;
    }

    public String getNomParent2() {
        return this.nomParent2;
    }

    public Enfant nomParent2(String nomParent2) {
        this.nomParent2 = nomParent2;
        return this;
    }

    public void setNomParent2(String nomParent2) {
        this.nomParent2 = nomParent2;
    }

    public String getPrenomParent2() {
        return this.prenomParent2;
    }

    public Enfant prenomParent2(String prenomParent2) {
        this.prenomParent2 = prenomParent2;
        return this;
    }

    public void setPrenomParent2(String prenomParent2) {
        this.prenomParent2 = prenomParent2;
    }

    public String getMobParent2() {
        return this.mobParent2;
    }

    public Enfant mobParent2(String mobParent2) {
        this.mobParent2 = mobParent2;
        return this;
    }

    public void setMobParent2(String mobParent2) {
        this.mobParent2 = mobParent2;
    }

    public String getEmailParent2() {
        return this.emailParent2;
    }

    public Enfant emailParent2(String emailParent2) {
        this.emailParent2 = emailParent2;
        return this;
    }

    public void setEmailParent2(String emailParent2) {
        this.emailParent2 = emailParent2;
    }

    public String getInfoSante() {
        return this.infoSante;
    }

    public Enfant infoSante(String infoSante) {
        this.infoSante = infoSante;
        return this;
    }

    public void setInfoSante(String infoSante) {
        this.infoSante = infoSante;
    }

    public Boolean getAutorisationImage() {
        return this.autorisationImage;
    }

    public Enfant autorisationImage(Boolean autorisationImage) {
        this.autorisationImage = autorisationImage;
        return this;
    }

    public void setAutorisationImage(Boolean autorisationImage) {
        this.autorisationImage = autorisationImage;
    }

    public String getNomContact() {
        return this.nomContact;
    }

    public Enfant nomContact(String nomContact) {
        this.nomContact = nomContact;
        return this;
    }

    public void setNomContact(String nomContact) {
        this.nomContact = nomContact;
    }

    public String getMobContact() {
        return this.mobContact;
    }

    public Enfant mobContact(String mobContact) {
        this.mobContact = mobContact;
        return this;
    }

    public void setMobContact(String mobContact) {
        this.mobContact = mobContact;
    }

    public User getParent() {
        return this.parent;
    }

    public Enfant parent(User user) {
        this.setParent(user);
        return this;
    }

    public void setParent(User user) {
        this.parent = user;
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
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", genre='" + getGenre() + "'" +
            ", nomParent2='" + getNomParent2() + "'" +
            ", prenomParent2='" + getPrenomParent2() + "'" +
            ", mobParent2='" + getMobParent2() + "'" +
            ", emailParent2='" + getEmailParent2() + "'" +
            ", infoSante='" + getInfoSante() + "'" +
            ", autorisationImage='" + getAutorisationImage() + "'" +
            ", nomContact='" + getNomContact() + "'" +
            ", mobContact='" + getMobContact() + "'" +
            "}";
    }
}
