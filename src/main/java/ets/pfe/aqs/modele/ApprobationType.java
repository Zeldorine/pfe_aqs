package ets.pfe.aqs.modele;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zeldorine
 */
public enum ApprobationType {
    ZERO_APPROBATION(1),
    ONE_APPROBATION(2),
    TWO_APPROBATION(5);

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditType.class);
    private final int valueInDB;

    private ApprobationType(int valueInDB) {
        this.valueInDB = valueInDB;
    }

    public int getValueInDb() {
        return valueInDB;
    }

    public ApprobationType getAuditTypeByValueInDb(int value) {
        for (ApprobationType approbationType : ApprobationType.values()) {
            if (approbationType.getValueInDb() == value) {
                return approbationType;
            }
        }

        LOGGER.warn("Cannot retrieve audit type with this value in db : " + value);

        return null;
    }
}
