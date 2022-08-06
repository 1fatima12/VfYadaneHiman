package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Magazin} entity.
 */
public class MagazinDTO implements Serializable {

    private Long id;

    private String nomMagazin;

    private String adresseMagazin;

    private LocationDTO location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomMagazin() {
        return nomMagazin;
    }

    public void setNomMagazin(String nomMagazin) {
        this.nomMagazin = nomMagazin;
    }

    public String getAdresseMagazin() {
        return adresseMagazin;
    }

    public void setAdresseMagazin(String adresseMagazin) {
        this.adresseMagazin = adresseMagazin;
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
        if (!(o instanceof MagazinDTO)) {
            return false;
        }

        MagazinDTO magazinDTO = (MagazinDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, magazinDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MagazinDTO{" +
            "id=" + getId() +
            ", nomMagazin='" + getNomMagazin() + "'" +
            ", adresseMagazin='" + getAdresseMagazin() + "'" +
            ", location=" + getLocation() +
            "}";
    }
}
