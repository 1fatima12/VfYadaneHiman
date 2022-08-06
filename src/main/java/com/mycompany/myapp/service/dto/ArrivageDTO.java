package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Arrivage} entity.
 */
public class ArrivageDTO implements Serializable {

    private Long id;

    private LocalDate dateArrivage;

    private Float prixAchat;

    private ProduitDTO produit;

    private FournisseurDTO fournisseur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateArrivage() {
        return dateArrivage;
    }

    public void setDateArrivage(LocalDate dateArrivage) {
        this.dateArrivage = dateArrivage;
    }

    public Float getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(Float prixAchat) {
        this.prixAchat = prixAchat;
    }

    public ProduitDTO getProduit() {
        return produit;
    }

    public void setProduit(ProduitDTO produit) {
        this.produit = produit;
    }

    public FournisseurDTO getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(FournisseurDTO fournisseur) {
        this.fournisseur = fournisseur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArrivageDTO)) {
            return false;
        }

        ArrivageDTO arrivageDTO = (ArrivageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, arrivageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArrivageDTO{" +
            "id=" + getId() +
            ", dateArrivage='" + getDateArrivage() + "'" +
            ", prixAchat=" + getPrixAchat() +
            ", produit=" + getProduit() +
            ", fournisseur=" + getFournisseur() +
            "}";
    }
}
