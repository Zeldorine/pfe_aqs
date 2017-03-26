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
@Table(name = "\"audit\"")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "utilisateur", updatable = false, nullable = false)
    private long utilisateurId;

    @Column(name = "audit_type", updatable = false, nullable = false)
    private AuditType auditType;

    @Column(name = "date", updatable = false, nullable = false)
    private Date date;

    @Column(name = "objet", updatable = false, nullable = false)
    private String objet;

    /**
     * 
     */
    public Audit() {
        //Empty because of jpa
    }

    /**
     * 
     * @param utilisateurId
     * @param auditType
     * @param date
     * @param objet 
     */
    public Audit(long utilisateurId, AuditType auditType, Date date, String objet) {
        this.utilisateurId = utilisateurId;
        this.auditType = auditType;
        this.date = date;
        this.objet = objet;
    }

    /**
     * 
     * @return 
     */
    public long getId() {
        return id;
    }

    /**
     * 
     * @return 
     */
    public long getUtilisateurId() {
        return utilisateurId;
    }

    /**
     * 
     * @param utilisateurId 
     */
    public void setUtilisateurId(long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    /**
     * 
     * @return 
     */
    public AuditType getAuditType() {
        return auditType;
    }

    /**
     * 
     * @param auditType 
     */
    public void setAuditType(AuditType auditType) {
        this.auditType = auditType;
    }

    /**
     * 
     * @return 
     */
    public Date getDate() {
        return date;
    }

    /**
     * 
     * @param date 
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * 
     * @return 
     */
    public String getObjet() {
        return objet;
    }

    /**
     * 
     * @param objet 
     */
    public void setObjet(String objet) {
        this.objet = objet;
    }

}
