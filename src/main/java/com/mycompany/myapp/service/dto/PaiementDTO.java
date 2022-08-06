package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.ModePaiement;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Paiement} entity.
 */
public class PaiementDTO implements Serializable {

    private Long id;

    private ModePaiement type;

    private Double avance;

    private Boolean etat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ModePaiement getType() {
        return type;
    }

    public void setType(ModePaiement type) {
        this.type = type;
    }

    public Double getAvance() {
        return avance;
    }

    public void setAvance(Double avance) {
        this.avance = avance;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaiementDTO)) {
            return false;
        }

        PaiementDTO paiementDTO = (PaiementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paiementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaiementDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", avance=" + getAvance() +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
