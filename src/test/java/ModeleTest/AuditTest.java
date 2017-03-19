package ModeleTest;

import ets.pfe.aqs.modele.ApprobationType;
import ets.pfe.aqs.modele.Audit;
import ets.pfe.aqs.modele.AuditType;
import ets.pfe.aqs.modele.Entreprise;
import ets.pfe.aqs.modele.Role;
import ets.pfe.aqs.modele.Utilisateur;
import ets.pfe.aqs.util.JPAUtility;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Zeldorine
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuditTest {

    EntityManager entityManager;
    static long auditId;
    static long enterpriseId;
    static long utilisateurId;

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
    public void test0PrepareTest() {
        entityManager.getTransaction().begin();
        Date now = Calendar.getInstance().getTime();
        Entreprise entreprise = new Entreprise("ENT_Test", "mission d'insertion", now, ApprobationType.ZERO_APPROBATION);
        entityManager.persist(entreprise);
        entityManager.getTransaction().commit();

        enterpriseId = entreprise.getId();
    }

    @Test
    public void test1PrepareTest() {
        Entreprise entAttach = entityManager.find(Entreprise.class, enterpriseId);

        assertNotNull(entAttach);
        entityManager.getTransaction().begin();
        Utilisateur utilisateur = new Utilisateur("username", "nom", "prenom", "nom@mail.com", Role.EDITEUR, 1, enterpriseId);
        entityManager.persist(utilisateur);
        entityManager.getTransaction().commit();

        utilisateurId = utilisateur.getId();
    }

    @Test
    public void test2Insert() {
        Utilisateur userAttach = entityManager.find(Utilisateur.class, utilisateurId);
        assertNotNull(userAttach);

        entityManager.getTransaction().begin();
        Date now = Calendar.getInstance().getTime();
        Audit audit = new Audit(utilisateurId, AuditType.CONNEXION, now, "pfe_aqs");
        entityManager.persist(audit);
        entityManager.getTransaction().commit();

        auditId = audit.getId();
    }

    @Test
    public void test3Find() {
        Audit audit = entityManager.find(Audit.class, auditId);

        Assert.assertNotNull(audit);
        Assert.assertEquals(utilisateurId, audit.getUtilisateurId());
        Assert.assertEquals(AuditType.CONNEXION, audit.getAuditType());
        Assert.assertEquals("pfe_aqs", audit.getObjet());
    }

    @Test
    public void test4CleanTest() {
        Audit audit = entityManager.find(Audit.class, auditId);
        entityManager.getTransaction().begin();
        entityManager.remove(audit);
        entityManager.getTransaction().commit();

        Utilisateur utilisateur = entityManager.find(Utilisateur.class, utilisateurId);
        entityManager.getTransaction().begin();
        entityManager.remove(utilisateur);
        entityManager.getTransaction().commit();

        Entreprise test = entityManager.find(Entreprise.class, enterpriseId);
        entityManager.getTransaction().begin();
        entityManager.remove(test);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
