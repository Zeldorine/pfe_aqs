package ets.pfe.aqs.modele;

import ets.pfe.aqs.PfeAqsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zeldorine
 */
public enum AuditType {
    CREATION(1),
    MODIFICATION(2),
    CONNEXION(3),
    ACTIVATION(4),
    DESACTIVATION(5);
    
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
        
        LOGGER.warn("Cannot retrieve audit type with this value in db : " + value);
        
        return null;
    }
}
