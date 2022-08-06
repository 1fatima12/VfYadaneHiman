package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A BonCommande.
 */
@Entity
@Table(name = "bon_commande")
public class BonCommande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "qte_commandee")
    private Double qteCommandee;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = { "fournisseur" }, allowSetters = true)
    private CommandeFournisseur commandeF;

    @ManyToOne
    @JsonIgnoreProperties(value = { "client" }, allowSetters = true)
    private CommandeClient commandeC;

    @ManyToOne
    @JsonIgnoreProperties(value = { "categorie", "marque" }, allowSetters = true)
    private Produit produit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BonCommande id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQteCommandee() {
        return this.qteCommandee;
    }

    public BonCommande qteCommandee(Double qteCommandee) {
        this.setQteCommandee(qteCommandee);
        return this;
    }

    public void setQteCommandee(Double qteCommandee) {
        this.qteCommandee = qteCommandee;
    }

    public CommandeFournisseur getCommandeF() {
        return this.commandeF;
    }

    public void setCommandeF(CommandeFournisseur commandeFournisseur) {
        this.commandeF = commandeFournisseur;
    }

    public BonCommande commandeF(CommandeFournisseur commandeFournisseur) {
        this.setCommandeF(commandeFournisseur);
        return this;
    }

    public CommandeClient getCommandeC() {
        return this.commandeC;
    }

    public void setCommandeC(CommandeClient commandeClient) {
        this.commandeC = commandeClient;
    }

    public BonCommande commandeC(CommandeClient commandeClient) {
        this.setCommandeC(commandeClient);
        return this;
    }

    public Produit getProduit() {
        return this.produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public BonCommande produit(Produit produit) {
        this.setProduit(produit);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BonCommande)) {
            return false;
        }
        return id != null && id.equals(((BonCommande) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BonCommande{" +
            "id=" + getId() +
            ", qteCommandee=" + getQteCommandee() +
            "}";
    }
}
