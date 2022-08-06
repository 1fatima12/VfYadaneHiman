package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Bti} entity.
 */
public class BtiDTO implements Serializable {

    private Long id;

    private Long numOrdre;

    private LocalDate date;

    private Long ref;

    private Integer qte;

    private ProduitDTO produit;

    private MagazinDTO magazin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumOrdre() {
        return numOrdre;
    }

    public void setNumOrdre(Long numOrdre) {
        this.numOrdre = numOrdre;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getRef() {
        return ref;
    }

    public void setRef(Long ref) {
        this.ref = ref;
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
        if (!(o instanceof BtiDTO)) {
            return false;
        }

        BtiDTO btiDTO = (BtiDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, btiDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BtiDTO{" +
            "id=" + getId() +
            ", numOrdre=" + getNumOrdre() +
            ", date='" + getDate() + "'" +
            ", ref=" + getRef() +
            ", qte=" + getQte() +
            ", produit=" + getProduit() +
            ", magazin=" + getMagazin() +
            "}";
    }
}
