package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A Bti.
 */
@Entity
@Table(name = "bti")
public class Bti implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "num_ordre")
    private Long numOrdre;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "ref")
    private Long ref;

    @Column(name = "qte")
    private Integer qte;

    @ManyToOne
    @JsonIgnoreProperties(value = { "categorie", "marque" }, allowSetters = true)
    private Produit produit;

    @ManyToOne
    @JsonIgnoreProperties(value = { "location" }, allowSetters = true)
    private Magazin magazin;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bti id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumOrdre() {
        return this.numOrdre;
    }

    public Bti numOrdre(Long numOrdre) {
        this.setNumOrdre(numOrdre);
        return this;
    }

    public void setNumOrdre(Long numOrdre) {
        this.numOrdre = numOrdre;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Bti date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getRef() {
        return this.ref;
    }

    public Bti ref(Long ref) {
        this.setRef(ref);
        return this;
    }

    public void setRef(Long ref) {
        this.ref = ref;
    }

    public Integer getQte() {
        return this.qte;
    }

    public Bti qte(Integer qte) {
        this.setQte(qte);
        return this;
    }

    public void setQte(Integer qte) {
        this.qte = qte;
    }

    public Produit getProduit() {
        return this.produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Bti produit(Produit produit) {
        this.setProduit(produit);
        return this;
    }

    public Magazin getMagazin() {
        return this.magazin;
    }

    public void setMagazin(Magazin magazin) {
        this.magazin = magazin;
    }

    public Bti magazin(Magazin magazin) {
        this.setMagazin(magazin);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bti)) {
            return false;
        }
        return id != null && id.equals(((Bti) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bti{" +
            "id=" + getId() +
            ", numOrdre=" + getNumOrdre() +
            ", date='" + getDate() + "'" +
            ", ref=" + getRef() +
            ", qte=" + getQte() +
            "}";
    }
}
