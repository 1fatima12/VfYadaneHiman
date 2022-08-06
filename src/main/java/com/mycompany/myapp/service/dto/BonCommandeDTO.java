package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.BonCommande} entity.
 */
public class BonCommandeDTO implements Serializable {

    private Long id;

    private Double qteCommandee;

    private CommandeFournisseurDTO commandeF;

    private CommandeClientDTO commandeC;

    private ProduitDTO produit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQteCommandee() {
        return qteCommandee;
    }

    public void setQteCommandee(Double qteCommandee) {
        this.qteCommandee = qteCommandee;
    }

    public CommandeFournisseurDTO getCommandeF() {
        return commandeF;
    }

    public void setCommandeF(CommandeFournisseurDTO commandeF) {
        this.commandeF = commandeF;
    }

    public CommandeClientDTO getCommandeC() {
        return commandeC;
    }

    public void setCommandeC(CommandeClientDTO commandeC) {
        this.commandeC = commandeC;
    }

    public ProduitDTO getProduit() {
        return produit;
    }

    public void setProduit(ProduitDTO produit) {
        this.produit = produit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BonCommandeDTO)) {
            return false;
        }

        BonCommandeDTO bonCommandeDTO = (BonCommandeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bonCommandeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BonCommandeDTO{" +
            "id=" + getId() +
            ", qteCommandee=" + getQteCommandee() +
            ", commandeF=" + getCommandeF() +
            ", commandeC=" + getCommandeC() +
            ", produit=" + getProduit() +
            "}";
    }
}
