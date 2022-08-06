package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CommandeClient} entity.
 */
public class CommandeClientDTO implements Serializable {

    private Long id;

    private LocalDate dateCom;

    private String designation;

    private ClientDTO client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCom() {
        return dateCom;
    }

    public void setDateCom(LocalDate dateCom) {
        this.dateCom = dateCom;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandeClientDTO)) {
            return false;
        }

        CommandeClientDTO commandeClientDTO = (CommandeClientDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commandeClientDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandeClientDTO{" +
            "id=" + getId() +
            ", dateCom='" + getDateCom() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", client=" + getClient() +
            "}";
    }
}
