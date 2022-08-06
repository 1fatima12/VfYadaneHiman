package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Marque.
 */
@Entity
@Table(name = "marque")
public class Marque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_marque")
    private String nomMarque;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @OneToMany(mappedBy = "marque")
    @JsonIgnoreProperties(value = { "categorie", "marque" }, allowSetters = true)
    private Set<Produit> produits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Marque id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomMarque() {
        return this.nomMarque;
    }

    public Marque nomMarque(String nomMarque) {
        this.setNomMarque(nomMarque);
        return this;
    }

    public void setNomMarque(String nomMarque) {
        this.nomMarque = nomMarque;
    }

    public byte[] getLogo() {
        return this.logo;
    }

    public Marque logo(byte[] logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return this.logoContentType;
    }

    public Marque logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public Set<Produit> getProduits() {
        return this.produits;
    }

    public void setProduits(Set<Produit> produits) {
        if (this.produits != null) {
            this.produits.forEach(i -> i.setMarque(null));
        }
        if (produits != null) {
            produits.forEach(i -> i.setMarque(this));
        }
        this.produits = produits;
    }

    public Marque produits(Set<Produit> produits) {
        this.setProduits(produits);
        return this;
    }

    public Marque addProduit(Produit produit) {
        this.produits.add(produit);
        produit.setMarque(this);
        return this;
    }

    public Marque removeProduit(Produit produit) {
        this.produits.remove(produit);
        produit.setMarque(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Marque)) {
            return false;
        }
        return id != null && id.equals(((Marque) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Marque{" +
            "id=" + getId() +
            ", nomMarque='" + getNomMarque() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            "}";
    }
}
