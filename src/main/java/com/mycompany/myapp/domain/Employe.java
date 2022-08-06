package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A Employe.
 */
@Entity
@Table(name = "employe")
public class Employe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "age")
    private Integer age;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Column(name = "poste")
    private String poste;

    @Column(name = "salaire")
    private Float salaire;

    @Column(name = "date_embauche")
    private LocalDate dateEmbauche;

    @JsonIgnoreProperties(value = { "employe", "client", "fournisseur", "magazin" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    @ManyToOne
    @JsonIgnoreProperties(value = { "location" }, allowSetters = true)
    private Magazin magzin;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Employe nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Employe prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Employe telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return this.email;
    }

    public Employe email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Employe adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getAge() {
        return this.age;
    }

    public Employe age(Integer age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Employe photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Employe photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getPoste() {
        return this.poste;
    }

    public Employe poste(String poste) {
        this.setPoste(poste);
        return this;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public Float getSalaire() {
        return this.salaire;
    }

    public Employe salaire(Float salaire) {
        this.setSalaire(salaire);
        return this;
    }

    public void setSalaire(Float salaire) {
        this.salaire = salaire;
    }

    public LocalDate getDateEmbauche() {
        return this.dateEmbauche;
    }

    public Employe dateEmbauche(LocalDate dateEmbauche) {
        this.setDateEmbauche(dateEmbauche);
        return this;
    }

    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Employe location(Location location) {
        this.setLocation(location);
        return this;
    }

    public Magazin getMagzin() {
        return this.magzin;
    }

    public void setMagzin(Magazin magazin) {
        this.magzin = magazin;
    }

    public Employe magzin(Magazin magazin) {
        this.setMagzin(magazin);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employe)) {
            return false;
        }
        return id != null && id.equals(((Employe) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employe{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", age=" + getAge() +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", poste='" + getPoste() + "'" +
            ", salaire=" + getSalaire() +
            ", dateEmbauche='" + getDateEmbauche() + "'" +
            "}";
    }
}
