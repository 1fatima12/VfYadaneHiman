package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rue_address")
    private String rueAddress;

    @Column(name = "code_postal")
    private String codePostal;

    @Column(name = "ville")
    private String ville;

    @Column(name = "state_province")
    private String stateProvince;

    @JsonIgnoreProperties(value = { "location", "magzin" }, allowSetters = true)
    @OneToOne(mappedBy = "location")
    private Employe employe;

    @JsonIgnoreProperties(value = { "location" }, allowSetters = true)
    @OneToOne(mappedBy = "location")
    private Client client;

    @JsonIgnoreProperties(value = { "location" }, allowSetters = true)
    @OneToOne(mappedBy = "location")
    private Fournisseur fournisseur;

    @JsonIgnoreProperties(value = { "location" }, allowSetters = true)
    @OneToOne(mappedBy = "location")
    private Magazin magazin;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Location id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRueAddress() {
        return this.rueAddress;
    }

    public Location rueAddress(String rueAddress) {
        this.setRueAddress(rueAddress);
        return this;
    }

    public void setRueAddress(String rueAddress) {
        this.rueAddress = rueAddress;
    }

    public String getCodePostal() {
        return this.codePostal;
    }

    public Location codePostal(String codePostal) {
        this.setCodePostal(codePostal);
        return this;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return this.ville;
    }

    public Location ville(String ville) {
        this.setVille(ville);
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getStateProvince() {
        return this.stateProvince;
    }

    public Location stateProvince(String stateProvince) {
        this.setStateProvince(stateProvince);
        return this;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public Employe getEmploye() {
        return this.employe;
    }

    public void setEmploye(Employe employe) {
        if (this.employe != null) {
            this.employe.setLocation(null);
        }
        if (employe != null) {
            employe.setLocation(this);
        }
        this.employe = employe;
    }

    public Location employe(Employe employe) {
        this.setEmploye(employe);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        if (this.client != null) {
            this.client.setLocation(null);
        }
        if (client != null) {
            client.setLocation(this);
        }
        this.client = client;
    }

    public Location client(Client client) {
        this.setClient(client);
        return this;
    }

    public Fournisseur getFournisseur() {
        return this.fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        if (this.fournisseur != null) {
            this.fournisseur.setLocation(null);
        }
        if (fournisseur != null) {
            fournisseur.setLocation(this);
        }
        this.fournisseur = fournisseur;
    }

    public Location fournisseur(Fournisseur fournisseur) {
        this.setFournisseur(fournisseur);
        return this;
    }

    public Magazin getMagazin() {
        return this.magazin;
    }

    public void setMagazin(Magazin magazin) {
        if (this.magazin != null) {
            this.magazin.setLocation(null);
        }
        if (magazin != null) {
            magazin.setLocation(this);
        }
        this.magazin = magazin;
    }

    public Location magazin(Magazin magazin) {
        this.setMagazin(magazin);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        return id != null && id.equals(((Location) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", rueAddress='" + getRueAddress() + "'" +
            ", codePostal='" + getCodePostal() + "'" +
            ", ville='" + getVille() + "'" +
            ", stateProvince='" + getStateProvince() + "'" +
            "}";
    }
}
