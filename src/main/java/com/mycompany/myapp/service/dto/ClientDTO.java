package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Client} entity.
 */
public class ClientDTO implements Serializable {

    private Long id;

    private Long numProd;

    private String nomProd;

    private Boolean status;

    private Float prixVente;

    @Lob
    private byte[] image;

    private String imageContentType;
    private String ice;

    private LocationDTO location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumProd() {
        return numProd;
    }

    public void setNumProd(Long numProd) {
        this.numProd = numProd;
    }

    public String getNomProd() {
        return nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Float getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(Float prixVente) {
        this.prixVente = prixVente;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getIce() {
        return ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientDTO)) {
            return false;
        }

        ClientDTO clientDTO = (ClientDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clientDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientDTO{" +
            "id=" + getId() +
            ", numProd=" + getNumProd() +
            ", nomProd='" + getNomProd() + "'" +
            ", status='" + getStatus() + "'" +
            ", prixVente=" + getPrixVente() +
            ", image='" + getImage() + "'" +
            ", ice='" + getIce() + "'" +
            ", location=" + getLocation() +
            "}";
    }
}
