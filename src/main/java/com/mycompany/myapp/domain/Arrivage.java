package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A Arrivage.
 */
@Entity
@Table(name = "arrivage")
public class Arrivage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_arrivage")
    private LocalDate dateArrivage;

    @Column(name = "prix_achat")
    private Float prixAchat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "categorie", "marque" }, allowSetters = true)
    private Produit produit;

    @ManyToOne
    @JsonIgnoreProperties(value = { "location" }, allowSetters = true)
    private Fournisseur fournisseur;

    @JsonIgnoreProperties(value = { "arrivage" }, allowSetters = true)
    @OneToOne(mappedBy = "arrivage")
    private Facture facture;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Arrivage id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateArrivage() {
        return this.dateArrivage;
    }

    public Arrivage dateArrivage(LocalDate dateArrivage) {
        this.setDateArrivage(dateArrivage);
        return this;
    }

    public void setDateArrivage(LocalDate dateArrivage) {
        this.dateArrivage = dateArrivage;
    }

    public Float getPrixAchat() {
        return this.prixAchat;
    }

    public Arrivage prixAchat(Float prixAchat) {
        this.setPrixAchat(prixAchat);
        return this;
    }

    public void setPrixAchat(Float prixAchat) {
        this.prixAchat = prixAchat;
    }

    public Produit getProduit() {
        return this.produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Arrivage produit(Produit produit) {
        this.setProduit(produit);
        return this;
    }

    public Fournisseur getFournisseur() {
        return this.fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Arrivage fournisseur(Fournisseur fournisseur) {
        this.setFournisseur(fournisseur);
        return this;
    }

    public Facture getFacture() {
        return this.facture;
    }

    public void setFacture(Facture facture) {
        if (this.facture != null) {
            this.facture.setArrivage(null);
        }
        if (facture != null) {
            facture.setArrivage(this);
        }
        this.facture = facture;
    }

    public Arrivage facture(Facture facture) {
        this.setFacture(facture);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Arrivage)) {
            return false;
        }
        return id != null && id.equals(((Arrivage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Arrivage{" +
            "id=" + getId() +
            ", dateArrivage='" + getDateArrivage() + "'" +
            ", prixAchat=" + getPrixAchat() +
            "}";
    }
}
