package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.TypeGenre;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserExtras.
 */
@Entity
@Table(name = "user_extras")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserExtras implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Pattern(regexp = "^\\d{10,10}$")
    @Column(name = "mob")
    private String mob;

    @Column(name = "adresse")
    private String adresse;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private TypeGenre genre;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserExtras id(Long id) {
        this.id = id;
        return this;
    }

    public String getMob() {
        return this.mob;
    }

    public UserExtras mob(String mob) {
        this.mob = mob;
        return this;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public UserExtras adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public TypeGenre getGenre() {
        return this.genre;
    }

    public UserExtras genre(TypeGenre genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(TypeGenre genre) {
        this.genre = genre;
    }

    public User getUser() {
        return this.user;
    }

    public UserExtras user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserExtras)) {
            return false;
        }
        return id != null && id.equals(((UserExtras) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExtras{" +
            "id=" + getId() +
            ", mob='" + getMob() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", genre='" + getGenre() + "'" +
            "}";
    }
}
