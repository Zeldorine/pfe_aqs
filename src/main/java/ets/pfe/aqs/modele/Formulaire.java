package ets.pfe.aqs.modele;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Zeldorine
 */
@Entity
@Table(name = "\"formulaire\"")
public class Formulaire {

    public static final int NOM_LONGUEUR_MAX = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "nom", updatable = false, nullable = false)
    private String nom;

    @Column(name = "version", updatable = false, nullable = false)
    private int version;

    @Column(name = "contenu", updatable = false, nullable = false)
    private String contenu;

    @Column(name = "date_creation", updatable = false, nullable = false)
    private Date dateCreation;

    @Column(name = "id_template", updatable = false, nullable = false)
    private int idTemplate;

    @Column(name = "createur", updatable = false, nullable = false)
    private int idCreateur;

    @Column(name = "approbation", nullable = false)
    private int approbation;

    public Formulaire() {
        //Empty because of jpa
    }

    /**
     * 
     * @param nom
     * @param version
     * @param contenu
     * @param dateCreation
     * @param idTemplate
     * @param idCreateur
     * @param approbation 
     */
    public Formulaire(String nom, int version, String contenu, Date dateCreation, int idTemplate, int idCreateur, int approbation) {
        this.nom = nom;
        this.version = version;
        this.contenu = contenu;
        this.dateCreation = dateCreation;
        this.idTemplate = idTemplate;
        this.idCreateur = idCreateur;
        this.approbation = approbation;
    }

    /**
     * 
     * @param id
     * @param form 
     */
    public Formulaire(int id, Formulaire form) {
        this.id = id;
        this.nom = form.getNom();
        this.version = form.getVersion();
        this.contenu = form.getContenu();
        this.dateCreation = form.dateCreation;
        this.idTemplate = form.getIdTemplate();
        this.idCreateur = form.getIdCreateur();
        this.approbation = form.getApprobation();
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
     * @param id 
     */
    public void setId(long id) {
       this.id = id;
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
    public int getVersion() {
        return version;
    }

    /**
     * 
     * @param version 
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * 
     * @return 
     */
    public String getContenu() {
        return contenu;
    }

    /**
     * 
     * @param contenu 
     */
    public void setContenu(String contenu) {
        this.contenu = contenu;
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
    public int getIdTemplate() {
        return idTemplate;
    }

    /**
     * 
     * @param idTemplate 
     */
    public void setIdTemplate(int idTemplate) {
        this.idTemplate = idTemplate;
    }

    /**
     * 
     * @return 
     */
    public int getIdCreateur() {
        return idCreateur;
    }

    /**
     * 
     * @param idCreateur 
     */
    public void setIdCreateur(int idCreateur) {
        this.idCreateur = idCreateur;
    }

    /**
     * 
     * @return 
     */
    public int getApprobation() {
        return approbation;
    }

    /**
     * 
     * @param approbation 
     */
    public void setApprobation(int approbation) {
        this.approbation = approbation;
    }
}
