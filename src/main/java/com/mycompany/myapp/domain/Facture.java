package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Facture.
 */
@Entity
@Table(name = "facture")
public class Facture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_facture")
    private Integer idFacture;

    @Column(name = "montant")
    private Double montant;

    @JsonIgnoreProperties(value = { "produit", "fournisseur", "facture" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Arrivage arrivage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Facture id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdFacture() {
        return this.idFacture;
    }

    public Facture idFacture(Integer idFacture) {
        this.setIdFacture(idFacture);
        return this;
    }

    public void setIdFacture(Integer idFacture) {
        this.idFacture = idFacture;
    }

    public Double getMontant() {
        return this.montant;
    }

    public Facture montant(Double montant) {
        this.setMontant(montant);
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Arrivage getArrivage() {
        return this.arrivage;
    }

    public void setArrivage(Arrivage arrivage) {
        this.arrivage = arrivage;
    }

    public Facture arrivage(Arrivage arrivage) {
        this.setArrivage(arrivage);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Facture)) {
            return false;
        }
        return id != null && id.equals(((Facture) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Facture{" +
            "id=" + getId() +
            ", idFacture=" + getIdFacture() +
            ", montant=" + getMontant() +
            "}";
    }
}
