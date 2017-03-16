package ets.pfe.aqs.modele;

/**
 * The role define what kind of actions is possible for users.
 * There are 4 roles :
 * <ul>
 *      <li> Editeur, can see manuel, create form and revision
 *      <li> Approbateur, can see manuel, create form, revision and approve a revision
 *      <li> Admin system, can create enterprise and admin system
 *      <li> Admin entreprise, can create users with editeur or approbateur role
 * </ul>
 * 
 * @author Zeldorine
 * @version 1.0.0
 */
public enum Role {
    EDITEUR,
    APPROBATEUR,
    ADMIN_SYSTEM,
    ADMIN_ENTREPRISE;
}
