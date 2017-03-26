package ets.pfe.aqs.modele;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "\"entreprise\"")
public class Entreprise implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "nom", updatable = false, nullable = false)
    private String nom;

    @Column(name = "mission", nullable = false)
    private String mission;

    @Column(name = "dateCreation", updatable = false, nullable = false)
    private Date dateCreation;

    @Column(name = "niveauApprobation", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ApprobationType approbationType;

    public Entreprise() {
        //Empty because of jpa
    }

    /**
     * 
     * @param nom
     * @param mission
     * @param dateCreation
     * @param approbationType 
     */
    public Entreprise(String nom, String mission, Date dateCreation, ApprobationType approbationType) {
        this.nom = nom;
        this.mission = mission;
        this.dateCreation = dateCreation;
        this.approbationType = approbationType;
    }

    /**
     * 
     * @param id
     * @param nom
     * @param mission
     * @param dateCreation
     * @param approbationType 
     */
    public Entreprise(int id, String nom, String mission, Date dateCreation, ApprobationType approbationType) {
        this.id = id;
        this.nom = nom;
        this.mission = mission;
        this.dateCreation = dateCreation;
        this.approbationType = approbationType;
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
    public String getMission() {
        return mission;
    }

    /**
     * 
     * @param mission 
     */
    public void setMission(String mission) {
        this.mission = mission;
    }

    /**
     * 
     * @return 
     */
    public Date getDateCreation() {
        return dateCreation;
    }

    /**
     * 
     * @param dateCreation 
     */
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    /**
     * 
     * @return 
     */
    public ApprobationType getApprobationType() {
        return approbationType;
    }

    /**
     * 
     * @param approbationType 
     */
    public void setApprobationType(ApprobationType approbationType) {
        this.approbationType = approbationType;
    }
}
