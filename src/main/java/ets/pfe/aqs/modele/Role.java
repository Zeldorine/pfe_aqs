package ets.pfe.aqs.modele;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zeldorine
 */
public enum Role {
    EDITEUR(1),
    APPROBATEUR(2),
    ADMIN_SYSTEM(3),
    ADMIN_ENTREPRISE(4);

    private static final Logger LOGGER = LoggerFactory.getLogger(Role.class);
    private final int valueInDB;

    private Role(int valueInDB) {
        this.valueInDB = valueInDB;
    }

    public int getValueInDb() {
        return valueInDB;
    }

    public Role getAuditTypeByValueInDb(int value) {
        for (Role role : Role.values()) {
            if (role.getValueInDb() == value) {
                return role;
            }
        }

        LOGGER.warn("Cannot retrieve role with this value in db : " + value);

        return null;
    }
}
