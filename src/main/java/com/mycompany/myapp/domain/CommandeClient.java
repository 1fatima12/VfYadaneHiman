package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A CommandeClient.
 */
@Entity
@Table(name = "commande_client")
public class CommandeClient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_com")
    private LocalDate dateCom;

    @Column(name = "designation")
    private String designation;

    @ManyToOne
    @JsonIgnoreProperties(value = { "location" }, allowSetters = true)
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CommandeClient id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCom() {
        return this.dateCom;
    }

    public CommandeClient dateCom(LocalDate dateCom) {
        this.setDateCom(dateCom);
        return this;
    }

    public void setDateCom(LocalDate dateCom) {
        this.dateCom = dateCom;
    }

    public String getDesignation() {
        return this.designation;
    }

    public CommandeClient designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public CommandeClient client(Client client) {
        this.setClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandeClient)) {
            return false;
        }
        return id != null && id.equals(((CommandeClient) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandeClient{" +
            "id=" + getId() +
            ", dateCom='" + getDateCom() + "'" +
            ", designation='" + getDesignation() + "'" +
            "}";
    }
}
