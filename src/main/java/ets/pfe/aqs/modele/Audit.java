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
    private int id;

    @Column(name = "utilisateur", updatable = false, nullable = false)
    private int utilisateurId;

    @Column(name = "audit_type", updatable = false, nullable = false)
    private AuditType auditType;

    @Column(name = "date", updatable = false, nullable = false)
    private Date date;

    @Column(name = "objet", updatable = false, nullable = false)
    private String objet;

    public Audit(){}
    
    public Audit(int utilisateurId, AuditType auditType, Date date, String objet) {
        this.utilisateurId = utilisateurId;
        this.auditType = auditType;
        this.date = date;
        this.objet = objet;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public AuditType getAuditType() {
        return auditType;
    }

    public void setAuditType(AuditType auditType) {
        this.auditType = auditType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }
    
    
}
