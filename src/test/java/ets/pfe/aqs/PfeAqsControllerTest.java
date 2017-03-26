package ets.pfe.aqs;

import ets.pfe.aqs.dao.EntrepriseDaoImpl;
import ets.pfe.aqs.dao.service.EntrepriseDaoService;
import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.ApprobationType;
import ets.pfe.aqs.modele.Entreprise;
import ets.pfe.aqs.modele.Formulaire;
import ets.pfe.aqs.modele.Role;
import ets.pfe.aqs.modele.Utilisateur;
import ets.pfe.aqs.util.ConfigUtil;
import ets.pfe.aqs.util.JPAUtility;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    private static Entreprise entreprise;
    private static Utilisateur authenticateUser;

    @BeforeClass
    public static void setUpClass() {
        try {
            JPAUtility.open();

            EntrepriseDaoService dao = new EntrepriseDaoImpl();
            Date now = Calendar.getInstance().getTime();
            entreprise = new Entreprise("ENT_Test", "mission d'insertion", now, ApprobationType.ONE_APPROBATION);
            entreprise = dao.ajouterEntreprise(entreprise);

            controller = new PfeAqsController(getConfig());
            setAuthenticateUser(Role.ADMIN_SYSTEM);
        } catch (Exception ex) {
            fail();
        }
    }

    @AfterClass
    public static void tearDownClass() {
        cleanDB(true);
        JPAUtility.close();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private static void cleanDB(boolean cleanEnt) {
        EntityManager em = JPAUtility.openEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("DELETE FROM test.AUDIT").executeUpdate();
        em.createNativeQuery("DELETE FROM test.FORMULAIRE").executeUpdate();
        if (cleanEnt) {
            em.createNativeQuery("DELETE FROM test.ENTREPRISE").executeUpdate();
        }
        em.createNativeQuery("DELETE FROM test.utilisateur").executeUpdate();
        em.getTransaction().commit();
        JPAUtility.closeEntityManager(em);
    }

    private static void setAuthenticateUser(Role role) {
        authenticateUser = new Utilisateur("username", "lastname", "firstname", "user@mail.com", role, 0, entreprise.getId());
        authenticateUser = new Utilisateur(1, authenticateUser);
        controller.setAuthenticateUser(authenticateUser);
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
            cleanDB(false);
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
            cleanDB(false);
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
            cleanDB(false);
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
            cleanDB(false);
        }
    }

    @Test
    public void activateUtilisateurWrongTest() {
        try {
            setAuthenticateUser(Role.ADMIN_ENTREPRISE);
            JSONObject jsonData = new JSONObject("{\"nomUtilisateur\":\"Zeldorine\","
                    + "\"nom\":\"Cazorla\","
                    + "\"prenom\":\"Tony\","
                    + "\"courriel\":\"tc@mail.ca\","
                    + "\"role\":\"APPROBATEUR\","
                    + "\"actif\":\"0\","
                    + "\"entreprise\":\"1\"}");

            Utilisateur newUser = controller.addUser(jsonData);

            setAuthenticateUser(Role.EDITEUR);

            controller.activateUtilisateur(newUser.getId(), true);
        } catch (PfeAqsException ex) {
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void activateUtilisateurGoodTest() {
        try {
            setAuthenticateUser(Role.ADMIN_ENTREPRISE);
            JSONObject jsonData = new JSONObject("{\"nomUtilisateur\":\"Zeldorine\","
                    + "\"nom\":\"Cazorla\","
                    + "\"prenom\":\"Tony\","
                    + "\"courriel\":\"tc@mail.ca\","
                    + "\"role\":\"APPROBATEUR\","
                    + "\"actif\":\"0\","
                    + "\"entreprise\":\"1\"}");

            Utilisateur newUser = controller.addUser(jsonData);
            newUser = controller.activateUtilisateur(newUser.getId(), true);
            Assert.assertNotNull(newUser);
            Assert.assertEquals(true, newUser.isActif());
        } catch (PfeAqsException ex) {
            fail();
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void getUsersWrongTest() {
        setAuthenticateUser(Role.ADMIN_SYSTEM);

        try {
            JSONObject jsonData = new JSONObject("{\"nomUtilisateur\":\"Zeldorine\","
                    + "\"nom\":\"Cazorla\","
                    + "\"prenom\":\"Tony\","
                    + "\"courriel\":\"tc@mail.ca\","
                    + "\"role\":\"APPROBATEUR\","
                    + "\"actif\":\"1\","
                    + "\"entreprise\":\"1\"}");

            controller.addUser(jsonData);
            List<Utilisateur> users = controller.getUtilisateurByEnterpriseId(1l);

            Assert.assertNotNull(users);
            Assert.assertEquals(1, users.size());
        } catch (PfeAqsException ex) {
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void getUsersGoodTest() {
        setAuthenticateUser(Role.ADMIN_ENTREPRISE);

        try {
            JSONObject jsonData = new JSONObject("{\"nomUtilisateur\":\"Zeldorine\","
                    + "\"nom\":\"Cazorla\","
                    + "\"prenom\":\"Tony\","
                    + "\"courriel\":\"tc@mail.ca\","
                    + "\"role\":\"APPROBATEUR\","
                    + "\"actif\":\"1\","
                    + "\"entreprise\":\"1\"}");

            controller.addUser(jsonData);
            List<Utilisateur> users = controller.getUtilisateurByEnterpriseId(1l);

            Assert.assertNotNull(users);
            Assert.assertEquals(1, users.size());
        } catch (PfeAqsException ex) {
            fail();
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void addEnterpriseWrongTest() {
        setAuthenticateUser(Role.ADMIN_ENTREPRISE);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"nom\","
                    + "\"mission\":\"mission\","
                    + "\"approbationType\":\"ONE_APPROBATION\"}");

            Entreprise enterprise = controller.addEnterprise(jsonData);
            Assert.assertNotNull(enterprise);
            Assert.assertEquals("nom", enterprise.getNom());
            Assert.assertEquals("mission", enterprise.getMission());
            Assert.assertEquals(ApprobationType.ONE_APPROBATION, enterprise.getApprobationType());
        } catch (PfeAqsException ex) {
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void addEnterpriseGoodTest() {
        setAuthenticateUser(Role.ADMIN_SYSTEM);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"nom\","
                    + "\"mission\":\"mission\","
                    + "\"approbationType\":\"ONE_APPROBATION\"}");

            Entreprise enterprise = controller.addEnterprise(jsonData);
            Assert.assertNotNull(enterprise);
            Assert.assertEquals("nom", enterprise.getNom());
            Assert.assertEquals("mission", enterprise.getMission());
            Assert.assertEquals(ApprobationType.ONE_APPROBATION, enterprise.getApprobationType());
        } catch (PfeAqsException ex) {
            fail();
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void updateEnterpriseWrongTest() {
        setAuthenticateUser(Role.ADMIN_SYSTEM);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"nom\","
                    + "\"mission\":\"mission\","
                    + "\"approbationType\":\"ONE_APPROBATION\"}");

            Entreprise enterprise = controller.addEnterprise(jsonData);

            setAuthenticateUser(Role.EDITEUR);

            jsonData = new JSONObject("{\"nom\":\"nom\","
                    + "\"mission\":\"Mission super\","
                    + "\"approbationType\":\"TWO_APPROBATION\"}");

            enterprise = controller.updateEnterprise(jsonData);

            Assert.assertNotNull(enterprise);
            Assert.assertEquals("nom", enterprise.getNom());
            Assert.assertEquals("Mission super", enterprise.getMission());
            Assert.assertEquals(ApprobationType.TWO_APPROBATION, enterprise.getApprobationType());
        } catch (PfeAqsException ex) {
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void updateEnterpriseGoodTest() {
        setAuthenticateUser(Role.ADMIN_SYSTEM);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"nom\","
                    + "\"mission\":\"mission\","
                    + "\"approbationType\":\"ONE_APPROBATION\"}");

            Entreprise enterprise = controller.addEnterprise(jsonData);

            jsonData = new JSONObject("{\"nom\":\"nom\","
                    + "\"id\":\"" + enterprise.getId() + "\","
                    + "\"mission\":\"Mission super\","
                    + "\"approbationType\":\"TWO_APPROBATION\"}");

            enterprise = controller.updateEnterprise(jsonData);

            Assert.assertNotNull(enterprise);
            Assert.assertEquals("nom", enterprise.getNom());
            Assert.assertEquals("Mission super", enterprise.getMission());
            Assert.assertEquals(ApprobationType.TWO_APPROBATION, enterprise.getApprobationType());
        } catch (PfeAqsException ex) {
            fail();
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void getEnterprisesWrongTest() {
        setAuthenticateUser(Role.ADMIN_ENTREPRISE);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"nom\","
                    + "\"mission\":\"mission\","
                    + "\"approbationType\":\"ONE_APPROBATION\"}");

            controller.addEnterprise(jsonData);
            List<Entreprise> enterprise = controller.getEnterprises();

            Assert.assertNotNull(enterprise);
            Assert.assertEquals(1, enterprise.size());
        } catch (PfeAqsException ex) {
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void getEnterprisesGoodTest() {
        setAuthenticateUser(Role.ADMIN_SYSTEM);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"nom\","
                    + "\"mission\":\"mission\","
                    + "\"approbationType\":\"ONE_APPROBATION\"}");

            controller.addEnterprise(jsonData);
            List<Entreprise> enterprise = controller.getEnterprises();

            Assert.assertNotNull(enterprise);
            Assert.assertTrue(enterprise.size() > 0);
        } catch (PfeAqsException ex) {
            fail();
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void getFormWrongTest() {
        setAuthenticateUser(Role.EDITEUR);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"form A\","
                    + "\"version\":\"1\","
                    + "\"contenu\":{\"nom\":\"nom\",\"mission\":\"mission\",\"approbation\":\"1\"},"
                    + "\"idTemplate\":\"1\"}");

            Formulaire form = controller.createForm(jsonData);

            setAuthenticateUser(Role.ADMIN_ENTREPRISE);
            form = controller.getForm(form.getNom());

            Assert.assertNotNull(form);
            Assert.assertEquals("form A", form.getNom());
            Assert.assertEquals(1, form.getVersion());
            Assert.assertNotNull(form.getContenu());
            Assert.assertEquals(authenticateUser.getId().intValue(), form.getIdCreateur());
            Assert.assertEquals(1, form.getIdTemplate());
            Assert.assertEquals(1, form.getApprobation());
        } catch (PfeAqsException ex) {
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void getFormWrongTestNoExists() {
        setAuthenticateUser(Role.EDITEUR);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"form A\","
                    + "\"version\":\"1\","
                    + "\"contenu\":{\"nom\":\"nom\",\"mission\":\"mission\",\"approbation\":\"1\"},"
                    + "\"idTemplate\":\"1\"}");

            Formulaire form = controller.createForm(jsonData);

            setAuthenticateUser(Role.ADMIN_ENTREPRISE);
            form = controller.getForm("Form b");

            Assert.assertNull(form);
        } catch (PfeAqsException ex) {
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void getFormGoodNoResultTest() {
        setAuthenticateUser(Role.EDITEUR);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"form A\","
                    + "\"version\":\"1\","
                    + "\"contenu\":{\"nom\":\"nom\",\"mission\":\"mission\",\"approbation\":\"1\"},"
                    + "\"idTemplate\":\"1\"}");

            Formulaire form = controller.createForm(jsonData);
            form = controller.getForm(form.getNom());
            Assert.assertNull(form);
        } catch (PfeAqsException ex) {
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void getFormGoodOneResultZeroApprobationTest() {
        setAuthenticateUser(Role.EDITEUR);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"form A\","
                    + "\"version\":\"1\","
                    + "\"contenu\":{\"nom\":\"nom\",\"mission\":\"mission\",\"approbation\":\"0\"},"
                    + "\"idTemplate\":\"1\"}");

            Formulaire form = controller.createForm(jsonData);
            form = controller.getForm(form.getNom());
            Assert.assertNull(form);
        } catch (PfeAqsException ex) {
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void getFormGoodOneResultTest() {
        setAuthenticateUser(Role.APPROBATEUR);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"form A\","
                    + "\"version\":\"1\","
                    + "\"contenu\":{\"nom\":\"nom\",\"mission\":\"mission\",\"approbation\":\"1\"},"
                    + "\"idTemplate\":\"1\"}");

            Formulaire form = controller.createForm(jsonData);
            form = controller.getForm(form.getNom());

            Assert.assertNotNull(form);
            Assert.assertEquals("form A", form.getNom());
            Assert.assertEquals(1, form.getVersion());
            Assert.assertNotNull(form.getContenu());
            Assert.assertEquals(authenticateUser.getId().intValue(), form.getIdCreateur());
            Assert.assertEquals(1, form.getIdTemplate());
            Assert.assertEquals(1, form.getApprobation());
        } catch (PfeAqsException ex) {
            fail();
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void getAllFormWrongTest() {
        setAuthenticateUser(Role.ADMIN_ENTREPRISE);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"form A\","
                    + "\"version\":\"1\","
                    + "\"contenu\":{\"nom\":\"nom\",\"mission\":\"mission\",\"approbation\":\"1\"},"
                    + "\"idTemplate\":\"1\"}");

            controller.createForm(jsonData);
            List<Formulaire> forms = controller.getAllForm();
            Assert.assertNull(forms);
        } catch (PfeAqsException ex) {
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void getAllFormGoodTest() {
        setAuthenticateUser(Role.APPROBATEUR);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"form A\","
                    + "\"version\":\"1\","
                    + "\"contenu\":{\"nom\":\"nom\",\"mission\":\"mission\",\"approbation\":\"1\"},"
                    + "\"idTemplate\":\"1\"}");

            controller.createForm(jsonData);
            List<Formulaire> forms = controller.getAllForm();
            Assert.assertNotNull(forms);
            Assert.assertEquals(1, forms.size());
        } catch (PfeAqsException ex) {
            fail();
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void getAllFormGoodNoResultTest() {
        setAuthenticateUser(Role.EDITEUR);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"form A\","
                    + "\"version\":\"1\","
                    + "\"contenu\":{\"nom\":\"nom\",\"mission\":\"mission\",\"approbationType\":\"ONE_APPROBATION\"},"
                    + "\"idTemplate\":\"1\"}");

            controller.createForm(jsonData);
            List<Formulaire> forms = controller.getAllForm();
            Assert.assertEquals(0, forms.size());
        } catch (PfeAqsException ex) {
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void approveFormWrongTest() {
        setAuthenticateUser(Role.EDITEUR);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"form A\","
                    + "\"version\":\"1\","
                    + "\"contenu\":{\"nom\":\"nom\",\"mission\":\"mission\",\"approbationType\":\"ONE_APPROBATION\"},"
                    + "\"idTemplate\":\"1\"}");

            Formulaire form = controller.createForm(jsonData);
            form = controller.approveForm(form.getId());
            Assert.assertNull(form);
        } catch (PfeAqsException ex) {
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void approveFormWrongTestNoExists() {
        setAuthenticateUser(Role.EDITEUR);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"form A\","
                    + "\"version\":\"1\","
                    + "\"contenu\":{\"nom\":\"nom\",\"mission\":\"mission\",\"approbationType\":\"ONE_APPROBATION\"},"
                    + "\"idTemplate\":\"1\"}");

            Formulaire form = controller.createForm(jsonData);
            form = controller.approveForm(-1l);
            Assert.assertNull(form);
        } catch (PfeAqsException ex) {
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void approveFormGoodTest() {
        setAuthenticateUser(Role.APPROBATEUR);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"form A\","
                    + "\"version\":\"1\","
                    + "\"contenu\":{\"nom\":\"nom\",\"mission\":\"mission\",\"approbationType\":\"ONE_APPROBATION\"},"
                    + "\"idTemplate\":\"1\"}");

            Formulaire form = controller.createForm(jsonData);
            form = controller.approveForm(form.getId());
            Assert.assertNotNull(form);
            Assert.assertEquals(0, form.getApprobation());
        } catch (PfeAqsException ex) {
            fail();
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void approveRejectFormFailTest() {
        setAuthenticateUser(Role.APPROBATEUR);
        Formulaire form = null;

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"form A\","
                    + "\"version\":\"1\","
                    + "\"contenu\":{\"nom\":\"nom\",\"mission\":\"mission\",\"approbationType\":\"ONE_APPROBATION\"},"
                    + "\"idTemplate\":\"1\"}");

            form = controller.createForm(jsonData);
            form = controller.rejectForm(form.getId());
            form = controller.approveForm(form.getId());
            Assert.assertNotNull(form);
            Assert.assertEquals(-1, form.getApprobation());
        } catch (PfeAqsException ex) {
            Assert.assertEquals(-1, form.getApprobation());
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void rejectFormWrongTest() {
        setAuthenticateUser(Role.EDITEUR);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"form A\","
                    + "\"version\":\"1\","
                    + "\"contenu\":{\"nom\":\"nom\",\"mission\":\"mission\",\"approbationType\":\"ONE_APPROBATION\"},"
                    + "\"idTemplate\":\"1\"}");

            Formulaire form = controller.createForm(jsonData);
            form = controller.rejectForm(form.getId());
            Assert.assertNull(form);
        } catch (PfeAqsException ex) {
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void rejectFormWrongTestNoExists() {
        setAuthenticateUser(Role.APPROBATEUR);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"form A\","
                    + "\"version\":\"1\","
                    + "\"contenu\":{\"nom\":\"nom\",\"mission\":\"mission\",\"approbationType\":\"ONE_APPROBATION\"},"
                    + "\"idTemplate\":\"1\"}");

            Formulaire form = controller.createForm(jsonData);
            form = controller.approveForm(-1l);
            Assert.assertNull(form);
        } catch (PfeAqsException ex) {
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void rejectFormGoodTest() {
        setAuthenticateUser(Role.APPROBATEUR);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"form A\","
                    + "\"version\":\"1\","
                    + "\"contenu\":{\"nom\":\"nom\",\"mission\":\"mission\",\"approbationType\":\"ONE_APPROBATION\"},"
                    + "\"idTemplate\":\"1\"}");

            Formulaire form = controller.createForm(jsonData);
            form = controller.rejectForm(form.getId());
            Assert.assertNotNull(form);
            Assert.assertEquals(-1, form.getApprobation());
        } catch (PfeAqsException ex) {
            fail();
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void rejectApproveFormFailTest() {
        setAuthenticateUser(Role.APPROBATEUR);
        Formulaire form = null;

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"form A\","
                    + "\"version\":\"1\","
                    + "\"contenu\":{\"nom\":\"nom\",\"mission\":\"mission\",\"approbationType\":\"ONE_APPROBATION\"},"
                    + "\"idTemplate\":\"1\"}");

            form = controller.createForm(jsonData);
            form = controller.approveForm(form.getId());
            form = controller.rejectForm(form.getId());
            Assert.assertNotNull(form);
            Assert.assertEquals(-1, form.getApprobation());
        } catch (PfeAqsException ex) {
            Assert.assertEquals(0, form.getApprobation());
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void createFormWrongTest() {
        setAuthenticateUser(Role.ADMIN_ENTREPRISE);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"form A\","
                    + "\"version\":\"1\","
                    + "\"contenu\":{\"nom\":\"nom\",\"mission\":\"mission\",\"approbationType\":\"ONE_APPROBATION\"},"
                    + "\"idTemplate\":\"1\"}");

            Formulaire form = controller.createForm(jsonData);

            Assert.assertNotNull(form);
            Assert.assertEquals("form A", form.getNom());
            Assert.assertEquals(1, form.getVersion());
            Assert.assertNotNull(form.getContenu());
            Assert.assertEquals(authenticateUser.getId().intValue(), form.getIdCreateur());
            Assert.assertEquals(1, form.getIdTemplate());
            Assert.assertEquals(1, form.getApprobation());
        } catch (PfeAqsException ex) {
        } finally {
            cleanDB(false);
        }
    }

    @Test
    public void createFormGoodTest() {
        setAuthenticateUser(Role.EDITEUR);

        try {
            JSONObject jsonData = new JSONObject("{\"nom\":\"form A\","
                    + "\"version\":\"1\","
                    + "\"contenu\":{\"nom\":\"nom\",\"mission\":\"mission\",\"approbationType\":\"ONE_APPROBATION\"},"
                    + "\"idTemplate\":\"1\"}");

            Formulaire form = controller.createForm(jsonData);

            Assert.assertNotNull(form);
            Assert.assertEquals("form A", form.getNom());
            Assert.assertEquals(1, form.getVersion());
            Assert.assertNotNull(form.getContenu());
            Assert.assertEquals(authenticateUser.getId().intValue(), form.getIdCreateur());
            Assert.assertEquals(1, form.getIdTemplate());
            Assert.assertEquals(1, form.getApprobation());
        } catch (PfeAqsException ex) {
        } finally {
            cleanDB(false);
        }
    }
}
