package DaoTest;

import ets.pfe.aqs.dao.UtilisateurDaoImpl;
import ets.pfe.aqs.dao.service.UtilisateurDaoService;
import ets.pfe.aqs.modele.Role;
import ets.pfe.aqs.modele.Utilisateur;
import ets.pfe.aqs.util.JPAUtility;
import ets.pfe.aqs.util.SecurityUtil;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
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
public class UtilisateurDaoTest {

    EntityManager entityManager;

    @BeforeClass
    public static void setUpClass() {
        JPAUtility.open();
    }

    @AfterClass
    public static void tearDownClass() {
        EntityManager em = JPAUtility.openEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("DELETE FROM test.UTILISATEUR").executeUpdate();
        em.getTransaction().commit();
        JPAUtility.closeEntityManager(em);
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
    public void creerUtilisateur() {
        try {
            UtilisateurDaoService dao = new UtilisateurDaoImpl();
            String uuid = UUID.randomUUID().toString();
            Utilisateur utilisateur = new Utilisateur(uuid, "nom", "prenom", "nom@mail.com", Role.EDITEUR, 1, 1);
            dao.creerUtilisateur(utilisateur);

            Utilisateur utilisateurToCheck = entityManager.find(Utilisateur.class, utilisateur.getId());
            Assert.assertNotNull(utilisateurToCheck);
            Assert.assertEquals(utilisateur.getId(), utilisateurToCheck.getId());
            Assert.assertEquals(utilisateur.getCourriel(), utilisateurToCheck.getCourriel());
            Assert.assertEquals(utilisateur.getEntreprise(), utilisateurToCheck.getEntreprise());
            Assert.assertEquals(utilisateur.getNom(), utilisateurToCheck.getNom());
            Assert.assertEquals(utilisateur.getNomUtilisateur(), utilisateurToCheck.getNomUtilisateur());
            Assert.assertEquals(utilisateur.getPrenom(), utilisateurToCheck.getPrenom());
            Assert.assertEquals(utilisateur.getRole(), utilisateurToCheck.getRole());

            entityManager.getTransaction().begin();
            entityManager.remove(utilisateurToCheck);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void getUsersByEnterpriseTest() {
                try {
            UtilisateurDaoService dao = new UtilisateurDaoImpl();
            String uuid = UUID.randomUUID().toString();
            Utilisateur utilisateur = new Utilisateur(uuid, "nom", "prenom", "nom@mail.com", Role.EDITEUR, 1, 1);
            dao.creerUtilisateur(utilisateur);

            List<Utilisateur> user = dao.getUsersByEnterprise(1l);
            
            Assert.assertNotNull(user);
            Assert.assertEquals(1, user.size());
            Utilisateur utilisateurToCheck = user.get(0);
            Assert.assertEquals(utilisateur.getId(), utilisateurToCheck.getId());
            Assert.assertEquals(utilisateur.getCourriel(), utilisateurToCheck.getCourriel());
            Assert.assertEquals(utilisateur.getEntreprise(), utilisateurToCheck.getEntreprise());
            Assert.assertEquals(utilisateur.getNom(), utilisateurToCheck.getNom());
            Assert.assertEquals(utilisateur.getNomUtilisateur(), utilisateurToCheck.getNomUtilisateur());
            Assert.assertEquals(utilisateur.getPrenom(), utilisateurToCheck.getPrenom());
            Assert.assertEquals(utilisateur.getRole(), utilisateurToCheck.getRole());

            Utilisateur utilisateurToRemove = entityManager.find(Utilisateur.class, utilisateur.getId());
            entityManager.getTransaction().begin();
            entityManager.remove(utilisateurToRemove);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void ActivateUser() {
        try {
            UtilisateurDaoService dao = new UtilisateurDaoImpl();
            String uuid = UUID.randomUUID().toString();
            Utilisateur utilisateur = new Utilisateur(uuid, "nom", "prenom", "nom@mail.com", Role.EDITEUR, 0, 1);
            dao.creerUtilisateur(utilisateur);
            utilisateur = dao.activateUtilisateur(utilisateur.getId(), true);

            Assert.assertEquals(true, utilisateur.isActif());

            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Utilisateur.class, utilisateur.getId()));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void DeactivateUser() {
        try {
            UtilisateurDaoService dao = new UtilisateurDaoImpl();
            String uuid = UUID.randomUUID().toString();
            Utilisateur utilisateur = new Utilisateur(uuid, "nom", "prenom", "nom@mail.com", Role.EDITEUR, 1, 1);
            dao.creerUtilisateur(utilisateur);
            utilisateur = dao.activateUtilisateur(utilisateur.getId(), false);

            Assert.assertEquals(false, utilisateur.isActif());

            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Utilisateur.class, utilisateur.getId()));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void changeUserPassword() {
        try {
            UtilisateurDaoService dao = new UtilisateurDaoImpl();
            String uuid = UUID.randomUUID().toString();
            Utilisateur utilisateur = new Utilisateur(uuid, "nom", "prenom", "nom@mail.com", Role.EDITEUR, 1, 1);
            utilisateur = dao.creerUtilisateur(utilisateur);
            utilisateur = dao.changePassword(utilisateur.getId(), SecurityUtil.cryptWithMD5("password"));

            Utilisateur utilisateurToCheck = entityManager.find(Utilisateur.class, utilisateur.getId());
            Assert.assertNotNull(utilisateurToCheck);
            Assert.assertEquals(utilisateur.getId(), utilisateurToCheck.getId());
            Assert.assertEquals(utilisateur.getCourriel(), utilisateurToCheck.getCourriel());
            Assert.assertEquals(utilisateur.getEntreprise(), utilisateurToCheck.getEntreprise());
            Assert.assertEquals(utilisateur.getNom(), utilisateurToCheck.getNom());
            Assert.assertEquals(utilisateur.getNomUtilisateur(), utilisateurToCheck.getNomUtilisateur());
            Assert.assertEquals(utilisateur.getPrenom(), utilisateurToCheck.getPrenom());
            Assert.assertEquals(utilisateur.getRole(), utilisateurToCheck.getRole());

            entityManager.getTransaction().begin();
            entityManager.remove(utilisateurToCheck);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            fail();
        }
    }
}
