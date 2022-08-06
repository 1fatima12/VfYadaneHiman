package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Magazin.
 */
@Entity
@Table(name = "magazin")
public class Magazin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_magazin")
    private String nomMagazin;

    @Column(name = "adresse_magazin")
    private String adresseMagazin;

    @JsonIgnoreProperties(value = { "employe", "client", "fournisseur", "magazin" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Magazin id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomMagazin() {
        return this.nomMagazin;
    }

    public Magazin nomMagazin(String nomMagazin) {
        this.setNomMagazin(nomMagazin);
        return this;
    }

    public void setNomMagazin(String nomMagazin) {
        this.nomMagazin = nomMagazin;
    }

    public String getAdresseMagazin() {
        return this.adresseMagazin;
    }

    public Magazin adresseMagazin(String adresseMagazin) {
        this.setAdresseMagazin(adresseMagazin);
        return this;
    }

    public void setAdresseMagazin(String adresseMagazin) {
        this.adresseMagazin = adresseMagazin;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Magazin location(Location location) {
        this.setLocation(location);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Magazin)) {
            return false;
        }
        return id != null && id.equals(((Magazin) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Magazin{" +
            "id=" + getId() +
            ", nomMagazin='" + getNomMagazin() + "'" +
            ", adresseMagazin='" + getAdresseMagazin() + "'" +
            "}";
    }
}
