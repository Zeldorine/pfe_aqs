package ets.pfe.aqs;

import ets.pfe.aqs.PfeAqsController;
import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Role;
import ets.pfe.aqs.modele.Utilisateur;
import ets.pfe.aqs.util.ConfigUtil;
import ets.pfe.aqs.util.JPAUtility;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Zeldorine
 */
public class PfeAqsControllerTest {

    private static PfeAqsController controller;

    @BeforeClass
    public static void setUpClass() {
        try {
            JPAUtility.open();
            controller = new PfeAqsController(getConfig());
            setAuthenticateUser(Role.ADMIN_SYSTEM);
        } catch (Exception ex) {
            fail();
        }
    }

    @AfterClass
    public static void tearDownClass() {
        cleanDB();
        JPAUtility.close();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private static void cleanDB() {
        EntityManager em = JPAUtility.openEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("DELETE FROM test.AUDIT").executeUpdate();
        em.createNativeQuery("DELETE FROM test.FORMULAIRE").executeUpdate();
        em.createNativeQuery("DELETE FROM test.ENTREPRISE").executeUpdate();
        em.createNativeQuery("DELETE FROM test.utilisateur").executeUpdate();
        em.getTransaction().commit();
        JPAUtility.closeEntityManager(em);
    }

    private static void setAuthenticateUser(Role role) {
        Utilisateur user = new Utilisateur(1, "username", "lastname", "firstname", "user@mail.com", role, 0, 0);
        controller.setAuthenticateUser(user);
    }

    private static ConfigUtil getConfig() {
        ConfigUtil config = new ConfigUtil();
        config.setAppName("Pfe AQS");
        config.setFromEmail("serviceClientPfeAqs@gmail.com");
        config.setPfeAqsEmailPassword("!PfeAqs123");
        config.setPfeAqsEmailUsername("serviceClientPfeAqs@gmail.com");
        config.setServerPort("8085");
        config.setSmtpHost("smtp.gmail.com");
        config.setSmtpPort("587");
        config.setDefaultPass("123soleil!");

        return config;
    }

    @Test
    public void LoginFailTest() {
    }

    @Test
    public void LoginGoodTest() {
    }

    @Test
    public void LogoutTest() {
        try {
            controller.logout();
        } catch (PfeAqsException ex) {
            fail();
        }
    }

    @Test
    public void addUserWrongRoleTest() {
        setAuthenticateUser(Role.EDITEUR);
        try {
            JSONObject jsonData = new JSONObject("{\"nomUtilisateur\":\"Zeldorine\","
                    + "\"nom\":\"Cazorla\","
                    + "\"prenom\":\"Tony\","
                    + "\"courriel\":\"tc@mail.ca\","
                    + "\"role\":\"APPROBATEUR\","
                    + "\"actif\":\"1\","
                    + "\"entreprise\":\"1\"}");

            Utilisateur utilisateur = controller.addUser(jsonData);
            Assert.assertNotNull(utilisateur);
            Assert.assertEquals("Zeldorine", utilisateur.getNomUtilisateur());
            Assert.assertEquals("Cazorla", utilisateur.getNom());
            Assert.assertEquals("Tony", utilisateur.getPrenom());
            Assert.assertEquals("tc@mail.ca", utilisateur.getCourriel());
            Assert.assertEquals(Role.APPROBATEUR, utilisateur.getRole());
            Assert.assertEquals(true, utilisateur.isActif());
            Assert.assertEquals(1, utilisateur.getEntreprise());
        } catch (PfeAqsException ex) {
        } finally {
            cleanDB();
        }
    }

    @Test
    public void addUserGoodRoleTest() {
        setAuthenticateUser(Role.ADMIN_ENTREPRISE);

        try {
            JSONObject jsonData = new JSONObject("{\"nomUtilisateur\":\"Zeldorine\","
                    + "\"nom\":\"Cazorla\","
                    + "\"prenom\":\"Tony\","
                    + "\"courriel\":\"tc@mail.ca\","
                    + "\"role\":\"APPROBATEUR\","
                    + "\"actif\":\"1\","
                    + "\"entreprise\":\"1\"}");

            Utilisateur utilisateur = controller.addUser(jsonData);
            Assert.assertNotNull(utilisateur);
            Assert.assertEquals("Zeldorine", utilisateur.getNomUtilisateur());
            Assert.assertEquals("Cazorla", utilisateur.getNom());
            Assert.assertEquals("Tony", utilisateur.getPrenom());
            Assert.assertEquals("tc@mail.ca", utilisateur.getCourriel());
            Assert.assertEquals(Role.APPROBATEUR, utilisateur.getRole());
            Assert.assertEquals(true, utilisateur.isActif());
            Assert.assertEquals(1, utilisateur.getEntreprise());
        } catch (PfeAqsException ex) {
            fail();
        } finally {
            cleanDB();
        }
    }

    @Test
    public void updateUserFailTest() {
        setAuthenticateUser(Role.EDITEUR);

        try {
            JSONObject jsonData = new JSONObject("{\"nomUtilisateur\":\"Zeldorine\","
                    + "\"nom\":\"Cazorla\","
                    + "\"prenom\":\"Tony\","
                    + "\"courriel\":\"tc@mail.ca\","
                    + "\"role\":\"APPROBATEUR\","
                    + "\"actif\":\"1\","
                    + "\"entreprise\":\"1\"}");

            controller.addUser(jsonData);

            jsonData = new JSONObject("{\"nomUtilisateur\":\"Zeldorine\","
                    + "\"nom\":\"Cazorla\","
                    + "\"prenom\":\"Tony\","
                    + "\"courriel\":\"tcNew@mail.ca\","
                    + "\"role\":\"EDITEUR\","
                    + "\"actif\":\"1\","
                    + "\"entreprise\":\"1\"}");

            Utilisateur utilisateur = controller.updateUser(jsonData);

            Assert.assertNotNull(utilisateur);
            Assert.assertEquals("Zeldorine", utilisateur.getNomUtilisateur());
            Assert.assertEquals("Cazorla", utilisateur.getNom());
            Assert.assertEquals("Tony", utilisateur.getPrenom());
            Assert.assertEquals("tcNew@mail.ca", utilisateur.getCourriel());
            Assert.assertEquals(Role.EDITEUR, utilisateur.getRole());
            Assert.assertEquals(true, utilisateur.isActif());
            Assert.assertEquals(1, utilisateur.getEntreprise());
        } catch (PfeAqsException ex) {
        } finally {
            cleanDB();
        }
    }

    @Test
    public void updateUserGoodTest() {
        setAuthenticateUser(Role.ADMIN_ENTREPRISE);

        try {
            JSONObject jsonData = new JSONObject("{\"nomUtilisateur\":\"Zeldorine\","
                    + "\"nom\":\"Cazorla\","
                    + "\"prenom\":\"Tony\","
                    + "\"courriel\":\"tc@mail.ca\","
                    + "\"role\":\"APPROBATEUR\","
                    + "\"actif\":\"1\","
                    + "\"entreprise\":\"1\"}");

            Utilisateur newUser = controller.addUser(jsonData);

            jsonData = new JSONObject("{\"nomUtilisateur\":\"Zeldorine\","
                    + "\"id\":\"" + newUser.getId() + "\","
                    + "\"nom\":\"Cazorla\","
                    + "\"prenom\":\"Tony\","
                    + "\"courriel\":\"tcNew@mail.ca\","
                    + "\"role\":\"EDITEUR\","
                    + "\"actif\":\"1\","
                    + "\"entreprise\":\"1\"}");

            Utilisateur utilisateur = controller.updateUser(jsonData);

            Assert.assertNotNull(utilisateur);
            Assert.assertEquals("Zeldorine", utilisateur.getNomUtilisateur());
            Assert.assertEquals("Cazorla", utilisateur.getNom());
            Assert.assertEquals("Tony", utilisateur.getPrenom());
            Assert.assertEquals("tcNew@mail.ca", utilisateur.getCourriel());
            Assert.assertEquals(Role.EDITEUR, utilisateur.getRole());
            Assert.assertEquals(true, utilisateur.isActif());
            Assert.assertEquals(1, utilisateur.getEntreprise());
        } catch (PfeAqsException ex) {
            fail();
        } finally {
            cleanDB();
        }
    }

    @Test
    public void activateUtilisateurWrongTest() {

    }

    @Test
    public void activateUtilisateurGoodTest() {

    }

    @Test
    public void addEnterpriseWrongTest() {

    }

    @Test
    public void addEnterpriseGoodTest() {

    }

    @Test
    public void updateEnterpriseWrongTest() {

    }

    @Test
    public void updateEnterpriseGoodTest() {

    }

    @Test
    public void getFormWrongTest() {

    }

    @Test
    public void getFormWrongTestNoExists() {

    }

    @Test
    public void getFormGoodTest() {

    }

    @Test
    public void getAllFormWrongTest() {

    }

    @Test
    public void getAllFormGoodTest() {

    }

    @Test
    public void approveFormWrongTest() {

    }

    @Test
    public void approveFormWrongTestNoExists() {

    }

    @Test
    public void approveFormGoodTest() {

    }

    @Test
    public void rejectFormWrongTest() {

    }

    @Test
    public void rejectFormWrongTestNoExists() {

    }

    @Test
    public void rejectFormGoodTest() {

    }

    @Test
    public void createFormWrongTest() {

    }

    @Test
    public void createFormGoodTest() {

    }
}
