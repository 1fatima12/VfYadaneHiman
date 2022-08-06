package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.ModePaiement;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Paiement.
 */
@Entity
@Table(name = "paiement")
public class Paiement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ModePaiement type;

    @Column(name = "avance")
    private Double avance;

    @Column(name = "etat")
    private Boolean etat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Paiement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ModePaiement getType() {
        return this.type;
    }

    public Paiement type(ModePaiement type) {
        this.setType(type);
        return this;
    }

    public void setType(ModePaiement type) {
        this.type = type;
    }

    public Double getAvance() {
        return this.avance;
    }

    public Paiement avance(Double avance) {
        this.setAvance(avance);
        return this;
    }

    public void setAvance(Double avance) {
        this.avance = avance;
    }

    public Boolean getEtat() {
        return this.etat;
    }

    public Paiement etat(Boolean etat) {
        this.setEtat(etat);
        return this;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paiement)) {
            return false;
        }
        return id != null && id.equals(((Paiement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paiement{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", avance=" + getAvance() +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
