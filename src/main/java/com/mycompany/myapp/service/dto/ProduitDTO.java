package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Produit} entity.
 */
public class ProduitDTO implements Serializable {

    private Long id;

    private Long numProd;

    private String nomProd;

    private Boolean status;

    private Float prixVente;

    @Lob
    private byte[] image;

    private String imageContentType;
    private CategorieDTO categorie;

    private MarqueDTO marque;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumProd() {
        return numProd;
    }

    public void setNumProd(Long numProd) {
        this.numProd = numProd;
    }

    public String getNomProd() {
        return nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Float getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(Float prixVente) {
        this.prixVente = prixVente;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public CategorieDTO getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieDTO categorie) {
        this.categorie = categorie;
    }

    public MarqueDTO getMarque() {
        return marque;
    }

    public void setMarque(MarqueDTO marque) {
        this.marque = marque;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitDTO)) {
            return false;
        }

        ProduitDTO produitDTO = (ProduitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, produitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitDTO{" +
            "id=" + getId() +
            ", numProd=" + getNumProd() +
            ", nomProd='" + getNomProd() + "'" +
            ", status='" + getStatus() + "'" +
            ", prixVente=" + getPrixVente() +
            ", image='" + getImage() + "'" +
            ", categorie=" + getCategorie() +
            ", marque=" + getMarque() +
            "}";
    }
}
