package ets.pfe.aqs.modele;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Zeldorine
 */
@Entity
@Table(name = "\"utilisateur\"")
public class Utilisateur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;
    
    @Column(name = "nom_utilisateur", updatable = false, nullable = false)
    private String nomUtilisateur;
    
    @Column(name = "nom", updatable = false, nullable = false)
    private String nom;
    
    @Column(name = "prenom", updatable = false, nullable = false)
    private String prenom;
    
    @Column(name = "courriel", updatable = false, nullable = false)
    private String courriel;
    
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Role role;
    
    @Column(name = "actif", updatable = false, nullable = false)
    private int actif;
    
    @Column(name = "id_entreprise", updatable = false, nullable = false)
    private int entreprise;

    public Utilisateur() {
    }

    public Utilisateur(String nomUtilisateur, String nom, String prenom, String courriel, Role role, int actif, int entreprise) {
        this.nomUtilisateur = nomUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.courriel = courriel;
        this.role = role;
        this.actif = actif;
        this.entreprise = entreprise;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActif() {
        return actif == 1 ? true : false;
    }

    public void setActif(boolean actif) {
        this.actif = actif  ? 1 : 0;
    }

    public int getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(int entreprise) {
        this.entreprise = entreprise;
    }
    
    
}
