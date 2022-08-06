package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Stock} entity.
 */
public class StockDTO implements Serializable {

    private Long id;

    private Integer qte;

    private ProduitDTO produit;

    private MagazinDTO magazin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQte() {
        return qte;
    }

    public void setQte(Integer qte) {
        this.qte = qte;
    }

    public ProduitDTO getProduit() {
        return produit;
    }

    public void setProduit(ProduitDTO produit) {
        this.produit = produit;
    }

    public MagazinDTO getMagazin() {
        return magazin;
    }

    public void setMagazin(MagazinDTO magazin) {
        this.magazin = magazin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockDTO)) {
            return false;
        }

        StockDTO stockDTO = (StockDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, stockDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockDTO{" +
            "id=" + getId() +
            ", qte=" + getQte() +
            ", produit=" + getProduit() +
            ", magazin=" + getMagazin() +
            "}";
    }
}
