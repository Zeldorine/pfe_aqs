package DaoTest;

import ets.pfe.aqs.dao.LoginDaoImpl;
import ets.pfe.aqs.dao.UtilisateurDaoImpl;
import ets.pfe.aqs.dao.service.LoginDaoService;
import ets.pfe.aqs.dao.service.UtilisateurDaoService;
import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Role;
import ets.pfe.aqs.modele.Utilisateur;
import ets.pfe.aqs.util.JPAUtility;
import ets.pfe.aqs.util.SecurityUtil;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
public class LoginDaoTest {

    static String password = SecurityUtil.cryptWithMD5("password");
    EntityManager entityManager;

    @BeforeClass
    public static void setUpClass() {
        JPAUtility.open();
    }

    @AfterClass
    public static void tearDownClass() {

        JPAUtility.close();
    }

    @Before
    public void setUp() {
        entityManager = JPAUtility.openEntityManager();
    }

    @After
    public void tearDown() {
        JPAUtility.closeEntityManager(entityManager);
    }

    @Test
    public void connexionSuccess() {
        Utilisateur utilisateur = null;
        try {
            UtilisateurDaoService daoUser = new UtilisateurDaoImpl();
            LoginDaoService daoLogin = new LoginDaoImpl();
            String uuid = UUID.randomUUID().toString();
            utilisateur = new Utilisateur(uuid, "nom", "prenom", "nom@mail.com", Role.EDITEUR, 1, 1);
            utilisateur = daoUser.creerUtilisateur(utilisateur);
            utilisateur = daoUser.changePassword(utilisateur.getId(), password);

            Utilisateur login = daoLogin.connexion(uuid, password);
            Assert.assertNotNull(login);
            Assert.assertEquals(utilisateur.getId(), login.getId());
            Assert.assertEquals(utilisateur.getCourriel(), login.getCourriel());
            Assert.assertEquals(utilisateur.getEntreprise(), login.getEntreprise());
            Assert.assertEquals(utilisateur.getNom(), login.getNom());
            Assert.assertEquals(utilisateur.getNomUtilisateur(), login.getNomUtilisateur());
            Assert.assertEquals(utilisateur.getPrenom(), login.getPrenom());
            Assert.assertEquals(utilisateur.getRole(), login.getRole());

            Utilisateur utilisateurToCheck = entityManager.find(Utilisateur.class, utilisateur.getId());
            entityManager.getTransaction().begin();
            entityManager.remove(utilisateurToCheck);
            entityManager.getTransaction().commit();
        } catch (NoResultException ex) {
            Utilisateur utilisateurToCheck = entityManager.find(Utilisateur.class, utilisateur.getId());
            entityManager.getTransaction().begin();
            entityManager.remove(utilisateurToCheck);
            entityManager.getTransaction().commit();
            return;
        } catch (PfeAqsException ex) {
            fail();
        }
    }

    @Test
    public void connexionFail() {
        Utilisateur utilisateur = null;
        try {
            UtilisateurDaoService daoUser = new UtilisateurDaoImpl();
            LoginDaoService daoLogin = new LoginDaoImpl();
            String uuid = UUID.randomUUID().toString();
            utilisateur = new Utilisateur(uuid, "nom", "prenom", "nom@mail.com", Role.EDITEUR, 1, 1);
            utilisateur = daoUser.creerUtilisateur(utilisateur);
            utilisateur = daoUser.changePassword(utilisateur.getId(), password);

            Utilisateur login = daoLogin.connexion("uuid", password);
            Assert.assertNull(login);

            Utilisateur utilisateurToCheck = entityManager.find(Utilisateur.class, utilisateur.getId());
            entityManager.getTransaction().begin();
            entityManager.remove(utilisateurToCheck);
            entityManager.getTransaction().commit();
        } catch (NoResultException ex) {
            Utilisateur utilisateurToCheck = entityManager.find(Utilisateur.class, utilisateur.getId());
            entityManager.getTransaction().begin();
            entityManager.remove(utilisateurToCheck);
            entityManager.getTransaction().commit();
            return;
        } catch (PfeAqsException ex) {
            Logger.getLogger(LoginDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
