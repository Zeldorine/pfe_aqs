package ets.pfe.aqs.modele;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zeldorine
 */
public enum AuditType {
    CREATE_USER(1),
    REVISION(2),
    CONNEXION(3),
    ACTIVATION(4),
    DESACTIVATION(5),
    DECONNEXION(6),
    CREATE_ENTREPRISE(7),
    GET_FORMULAIRE(8),
    GET_PROCESSUS(12),
    GET_WORKINSTRUCTION(13),
    GET_PROCEDURE(14),
    CREATE_FORMULAIRE(10),
    CREATE_REVISION(11),
    APPROVE_FORM(17),
    REJECT_FORM(18),
    UPDATE_USER(15),
    UPDATE_ENTERPRISE(16);
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AuditType.class);
    private final int valueInDB;
    
    private AuditType(int valueInDB){
        this.valueInDB = valueInDB;
    }
    
    public int getValueInDb(){
        return valueInDB;
    }
    
    public AuditType getAuditTypeByValueInDb(int value){
        for(AuditType auditType : AuditType.values()){
            if(auditType.getValueInDb() == value){
                return auditType;
            }
        }
        
        LOGGER.warn("Cannot retrieve audit type with this value in db : {}", value);
        
        return null;
    }
}
