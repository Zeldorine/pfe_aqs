package ets.pfe.aqs.modele;

/**
 *
 * @author Zeldorine
 */
public enum AuditType {
    CREATE_USER(),
    REVISION(),
    CONNEXION(),
    ACTIVATION(),
    DESACTIVATION(),
    DECONNEXION(),
    CREATE_ENTREPRISE(),
    GET_FORMULAIRE(),
    GET_PROCESSUS(),
    GET_WORKINSTRUCTION(),
    GET_PROCEDURE(),
    CREATE_FORMULAIRE(),
    CREATE_REVISION(),
    APPROVE_FORM(),
    REJECT_FORM(),
    GET_ENTERPRISES(),
    GET_USERS(),
    UPDATE_USER(),
    UPDATE_ENTERPRISE();
}
