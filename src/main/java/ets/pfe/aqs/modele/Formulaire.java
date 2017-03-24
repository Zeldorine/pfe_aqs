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

    public Formulaire(String nom, int version, String contenu, Date dateCreation, int idTemplate, int idCreateur, int approbation) {
        this.nom = nom;
        this.version = version;
        this.contenu = contenu;
        this.dateCreation = dateCreation;
        this.idTemplate = idTemplate;
        this.idCreateur = idCreateur;
        this.approbation = approbation;
    }

    public Formulaire(int id, String nom, int version, String contenu, Date dateCreation, int idTemplate, int idCreateur, int approbation) {
        this.id = id;
        this.nom = nom;
        this.version = version;
        this.contenu = contenu;
        this.dateCreation = dateCreation;
        this.idTemplate = idTemplate;
        this.idCreateur = idCreateur;
        this.approbation = approbation;
    }

    public long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(int idTemplate) {
        this.idTemplate = idTemplate;
    }

    public int getIdCreateur() {
        return idCreateur;
    }

    public void setIdCreateur(int idCreateur) {
        this.idCreateur = idCreateur;
    }

    public int getApprobation() {
        return approbation;
    }

    public void setApprobation(int approbation) {
        this.approbation = approbation;
    }
}
