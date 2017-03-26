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
    private long id;

    @Column(name = "nom_utilisateur", updatable = false, nullable = false)
    private String nomUtilisateur;

    @Column(name = "nom", updatable = false, nullable = false)
    private String nom;

    @Column(name = "prenom", updatable = false, nullable = false)
    private String prenom;

    @Column(name = "courriel", nullable = false)
    private String courriel;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @Column(name = "actif", nullable = false)
    private int actif;

    @Column(name = "id_entreprise", updatable = false, nullable = false)
    private long entreprise;

    public Utilisateur() {
        //Empty because of jpa
    }

    /**
     * 
     * @param nomUtilisateur
     * @param nom
     * @param prenom
     * @param courriel
     * @param role
     * @param actif
     * @param entreprise 
     */
    public Utilisateur(String nomUtilisateur, String nom, String prenom, String courriel, Role role, int actif, long entreprise) {
        this.nomUtilisateur = nomUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.courriel = courriel;
        this.role = role;
        this.actif = actif;
        this.entreprise = entreprise;
    }
    
    /**
     * 
     * @param id
     * @param utilisateur 
     */
    public Utilisateur(long id, Utilisateur utilisateur) {
        this.id = id;
        this.nomUtilisateur = utilisateur.getNomUtilisateur();
        this.nom = utilisateur.getNom();
        this.prenom = utilisateur.getPrenom();
        this.courriel = utilisateur.getCourriel();
        this.role = utilisateur.getRole();
        this.actif = utilisateur.isActif() ? 1 : 0;
        this.entreprise = utilisateur.getEntreprise();
    }

    /**
     * 
     * @return 
     */
    public Long getId() {
        return id;
    }
    
    /**
     * 
     * @return 
     */
    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    /**
     * 
     * @param nomUtilisateur 
     */
    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    /**
     * 
     * @return 
     */
    public String getNom() {
        return nom;
    }

    /**
     * 
     * @param nom 
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * 
     * @return 
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * 
     * @param prenom 
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * 
     * @return 
     */
    public String getCourriel() {
        return courriel;
    }

    /**
     * 
     * @param courriel 
     */
    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    /**
     * 
     * @return 
     */
    public Role getRole() {
        return role;
    }

    /**
     * 
     * @param role 
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * 
     * @return 
     */
    public boolean isActif() {
        return actif == 1;
    }

    /**
     * 
     * @param actif 
     */
    public void setActif(boolean actif) {
        this.actif = actif ? 1 : 0;
    }

    /**
     * 
     * @return 
     */
    public long getEntreprise() {
        return entreprise;
    }

    /**
     * 
     * @param entreprise
     */
    public void setEntreprise(long entreprise) {
        this.entreprise = entreprise;
    }

}
