package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "num_prod")
    private Long numProd;

    @Column(name = "nom_prod")
    private String nomProd;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "prix_vente")
    private Float prixVente;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "ice")
    private String ice;

    @JsonIgnoreProperties(value = { "employe", "client", "fournisseur", "magazin" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Client id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumProd() {
        return this.numProd;
    }

    public Client numProd(Long numProd) {
        this.setNumProd(numProd);
        return this;
    }

    public void setNumProd(Long numProd) {
        this.numProd = numProd;
    }

    public String getNomProd() {
        return this.nomProd;
    }

    public Client nomProd(String nomProd) {
        this.setNomProd(nomProd);
        return this;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Client status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Float getPrixVente() {
        return this.prixVente;
    }

    public Client prixVente(Float prixVente) {
        this.setPrixVente(prixVente);
        return this;
    }

    public void setPrixVente(Float prixVente) {
        this.prixVente = prixVente;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Client image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public Client imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getIce() {
        return this.ice;
    }

    public Client ice(String ice) {
        this.setIce(ice);
        return this;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Client location(Location location) {
        this.setLocation(location);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", numProd=" + getNumProd() +
            ", nomProd='" + getNomProd() + "'" +
            ", status='" + getStatus() + "'" +
            ", prixVente=" + getPrixVente() +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", ice='" + getIce() + "'" +
            "}";
    }
}
