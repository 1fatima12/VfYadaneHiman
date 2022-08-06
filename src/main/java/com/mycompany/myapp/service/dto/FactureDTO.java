package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Facture} entity.
 */
public class FactureDTO implements Serializable {

    private Long id;

    private Integer idFacture;

    private Double montant;

    private ArrivageDTO arrivage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(Integer idFacture) {
        this.idFacture = idFacture;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public ArrivageDTO getArrivage() {
        return arrivage;
    }

    public void setArrivage(ArrivageDTO arrivage) {
        this.arrivage = arrivage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FactureDTO)) {
            return false;
        }

        FactureDTO factureDTO = (FactureDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, factureDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactureDTO{" +
            "id=" + getId() +
            ", idFacture=" + getIdFacture() +
            ", montant=" + getMontant() +
            ", arrivage=" + getArrivage() +
            "}";
    }
}
