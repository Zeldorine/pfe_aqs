package DaoTest;

import ets.pfe.aqs.dao.DocumentDaoImpl;
import ets.pfe.aqs.dao.service.DocumentDaoService;
import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.ApprobationType;
import ets.pfe.aqs.modele.Formulaire;
import ets.pfe.aqs.util.JPAUtility;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Zeldorine
 */
public class DocumentDaoTest {

    EntityManager entityManager;

    private final String jsonContent = "{"
            + "     \"firstName\": \"John\","
            + "     \"lastName\": \"Smith\","
            + "     \"age\": 25,"
            + "     \"address\": {"
            + "         \"streetAddress\": \"21 2nd Street\","
            + "         \"city\": \"New York\","
            + "         \"state\": \"NY\","
            + "         \"postalCode\": \"10021\""
            + "     },"
            + "     \"phoneNumber\": ["
            + "         { \"type\": \"home\", \"number\": \"212 555-1234\" },"
            + "         { \"type\": \"fax\", \"number\": \"646 555-4567\" }"
            + "     ],"
            + "     \"newSubscription\": false,"
            + "     \"companyName\": null"
            + " }";

    @BeforeClass
    public static void setUpClass() {
        JPAUtility.open();
    }

    @AfterClass
    public static void tearDownClass() {
        EntityManager em = JPAUtility.openEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("DELETE FROM FORMULAIRE").executeUpdate();
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
    public void createForm() {
        try {
            DocumentDaoService dao = new DocumentDaoImpl();
            Date now = Calendar.getInstance().getTime();
            Formulaire formulaire = new Formulaire("Formulaire a", 1, jsonContent, now, 1, 1, ApprobationType.ZERO_APPROBATION.getTotalApprobation());

            dao.createForm(formulaire);

            Formulaire formulaireToCheck = entityManager.find(Formulaire.class, formulaire.getId());
            Assert.assertNotNull(formulaireToCheck);
            Assert.assertEquals(formulaire.getId(), formulaireToCheck.getId());
            Assert.assertEquals(formulaire.getIdCreateur(), formulaireToCheck.getIdCreateur());
            Assert.assertEquals(formulaire.getIdTemplate(), formulaireToCheck.getIdTemplate());
            Assert.assertEquals(formulaire.getNom(), formulaireToCheck.getNom());
            Assert.assertEquals(formulaire.getApprobation(), formulaireToCheck.getApprobation());
            Assert.assertEquals(formulaire.getVersion(), formulaireToCheck.getVersion());
            Assert.assertEquals(formulaire.getDateCreation(), formulaireToCheck.getDateCreation());
            Assert.assertEquals(formulaire.getContenu(), formulaireToCheck.getContenu());

            entityManager.getTransaction().begin();
            entityManager.remove(formulaireToCheck);
            entityManager.getTransaction().commit();
        } catch (PfeAqsException ex) {
            fail();
        }
    }

    @Test
    public void rejectFormFail() {
        Long formIdToRemove = null;
        try {
            DocumentDaoService dao = new DocumentDaoImpl();
            Date now = Calendar.getInstance().getTime();
            Formulaire formulaire = new Formulaire("Formulaire a", 1, jsonContent, now, 1, 1, ApprobationType.ZERO_APPROBATION.getTotalApprobation());

            formulaire = dao.createForm(formulaire);
            formIdToRemove = formulaire.getId();
            formulaire = dao.rejectForm(formulaire.getId());

            Formulaire formulaireToCheck = entityManager.find(Formulaire.class, formulaire.getId());
            Assert.assertNotNull(formulaireToCheck);
            Assert.assertEquals(formulaire.getId(), formulaireToCheck.getId());
            Assert.assertEquals(formulaire.getIdCreateur(), formulaireToCheck.getIdCreateur());
            Assert.assertEquals(formulaire.getIdTemplate(), formulaireToCheck.getIdTemplate());
            Assert.assertEquals(formulaire.getNom(), formulaireToCheck.getNom());
            Assert.assertEquals(ApprobationType.REJECTED.getTotalApprobation(), formulaireToCheck.getApprobation());
            Assert.assertEquals(formulaire.getVersion(), formulaireToCheck.getVersion());
            Assert.assertEquals(formulaire.getDateCreation(), formulaireToCheck.getDateCreation());
            Assert.assertEquals(formulaire.getContenu(), formulaireToCheck.getContenu());

            entityManager.getTransaction().begin();
            entityManager.remove(formulaireToCheck);
            entityManager.getTransaction().commit();
        } catch (PfeAqsException ex) {
            if (ex.getMessage().contains("always approve")) {
                entityManager.getTransaction().begin();
                entityManager.remove(entityManager.find(Formulaire.class, formIdToRemove));
                entityManager.getTransaction().commit();
                return;
            }
            fail();
        }
    }

    @Test
    public void rejectFormSuccess() {
        try {
            DocumentDaoService dao = new DocumentDaoImpl();
            Date now = Calendar.getInstance().getTime();
            Formulaire formulaire = new Formulaire("Formulaire a", 1, jsonContent, now, 1, 1, ApprobationType.ONE_APPROBATION.getTotalApprobation());

            formulaire = dao.createForm(formulaire);
            formulaire = dao.rejectForm(formulaire.getId());

            Formulaire formulaireToCheck = entityManager.find(Formulaire.class, formulaire.getId());
            Assert.assertNotNull(formulaireToCheck);
            Assert.assertEquals(formulaire.getId(), formulaireToCheck.getId());
            Assert.assertEquals(formulaire.getIdCreateur(), formulaireToCheck.getIdCreateur());
            Assert.assertEquals(formulaire.getIdTemplate(), formulaireToCheck.getIdTemplate());
            Assert.assertEquals(formulaire.getNom(), formulaireToCheck.getNom());
            Assert.assertEquals(ApprobationType.REJECTED.getTotalApprobation(), formulaireToCheck.getApprobation());
            Assert.assertEquals(formulaire.getVersion(), formulaireToCheck.getVersion());
            Assert.assertEquals(formulaire.getDateCreation(), formulaireToCheck.getDateCreation());
            Assert.assertEquals(formulaire.getContenu(), formulaireToCheck.getContenu());

            entityManager.getTransaction().begin();
            entityManager.remove(formulaireToCheck);
            entityManager.getTransaction().commit();
        } catch (PfeAqsException ex) {
            fail();
        }
    }

    @Test
    public void approveFormFail() {
        Long formIdToRemove = null;
        try {
            DocumentDaoService dao = new DocumentDaoImpl();
            Date now = Calendar.getInstance().getTime();
            Formulaire formulaire = new Formulaire("Formulaire a", 1, jsonContent, now, 1, 1, ApprobationType.ZERO_APPROBATION.getTotalApprobation());

            formulaire = dao.createForm(formulaire);
            formIdToRemove = formulaire.getId();
            formulaire = dao.approveForm(formulaire.getId());

            Formulaire formulaireToCheck = entityManager.find(Formulaire.class, formulaire.getId());
            Assert.assertNotNull(formulaireToCheck);
            Assert.assertEquals(formulaire.getId(), formulaireToCheck.getId());
            Assert.assertEquals(formulaire.getIdCreateur(), formulaireToCheck.getIdCreateur());
            Assert.assertEquals(formulaire.getIdTemplate(), formulaireToCheck.getIdTemplate());
            Assert.assertEquals(formulaire.getNom(), formulaireToCheck.getNom());
            Assert.assertEquals(ApprobationType.ZERO_APPROBATION.getTotalApprobation(), formulaireToCheck.getApprobation());
            Assert.assertEquals(formulaire.getVersion(), formulaireToCheck.getVersion());
            Assert.assertEquals(formulaire.getDateCreation(), formulaireToCheck.getDateCreation());
            Assert.assertEquals(formulaire.getContenu(), formulaireToCheck.getContenu());

            entityManager.getTransaction().begin();
            entityManager.remove(formulaireToCheck);
            entityManager.getTransaction().commit();
        } catch (PfeAqsException ex) {
            if (ex.getMessage().contains("always approve")) {
                entityManager.getTransaction().begin();
                entityManager.remove(entityManager.find(Formulaire.class, formIdToRemove));
                entityManager.getTransaction().commit();
                return;
            }

            fail();
        }
    }

    @Test
    public void oneApproveFormSucess() {
        try {
            DocumentDaoService dao = new DocumentDaoImpl();
            Date now = Calendar.getInstance().getTime();
            Formulaire formulaire = new Formulaire("Formulaire a", 1, jsonContent, now, 1, 1, ApprobationType.ONE_APPROBATION.getTotalApprobation());

            formulaire = dao.createForm(formulaire);
            formulaire = dao.approveForm(formulaire.getId());

            Formulaire formulaireToCheck = entityManager.find(Formulaire.class, formulaire.getId());
            Assert.assertNotNull(formulaireToCheck);
            Assert.assertEquals(formulaire.getId(), formulaireToCheck.getId());
            Assert.assertEquals(formulaire.getIdCreateur(), formulaireToCheck.getIdCreateur());
            Assert.assertEquals(formulaire.getIdTemplate(), formulaireToCheck.getIdTemplate());
            Assert.assertEquals(formulaire.getNom(), formulaireToCheck.getNom());
            Assert.assertEquals(ApprobationType.ZERO_APPROBATION.getTotalApprobation(), formulaireToCheck.getApprobation());
            Assert.assertEquals(formulaire.getVersion(), formulaireToCheck.getVersion());
            Assert.assertEquals(formulaire.getDateCreation(), formulaireToCheck.getDateCreation());
            Assert.assertEquals(formulaire.getContenu(), formulaireToCheck.getContenu());

            entityManager.getTransaction().begin();
            entityManager.remove(formulaireToCheck);
            entityManager.getTransaction().commit();
        } catch (PfeAqsException ex) {
            if (ex.getMessage().contains("always approve")) {
                return;
            }

            fail();
        }
    }

    @Test
    public void twoApproveFormSucess() {
        try {
            DocumentDaoService dao = new DocumentDaoImpl();
            Date now = Calendar.getInstance().getTime();
            Formulaire formulaire = new Formulaire("Formulaire a", 1, jsonContent, now, 1, 1, ApprobationType.TWO_APPROBATION.getTotalApprobation());

            formulaire = dao.createForm(formulaire);
            formulaire = dao.approveForm(formulaire.getId());

            Formulaire formulaireToCheck = entityManager.find(Formulaire.class, formulaire.getId());
            Assert.assertNotNull(formulaireToCheck);
            Assert.assertEquals(formulaire.getId(), formulaireToCheck.getId());
            Assert.assertEquals(formulaire.getIdCreateur(), formulaireToCheck.getIdCreateur());
            Assert.assertEquals(formulaire.getIdTemplate(), formulaireToCheck.getIdTemplate());
            Assert.assertEquals(formulaire.getNom(), formulaireToCheck.getNom());
            Assert.assertEquals(ApprobationType.ONE_APPROBATION.getTotalApprobation(), formulaireToCheck.getApprobation());
            Assert.assertEquals(formulaire.getVersion(), formulaireToCheck.getVersion());
            Assert.assertEquals(formulaire.getDateCreation(), formulaireToCheck.getDateCreation());
            Assert.assertEquals(formulaire.getContenu(), formulaireToCheck.getContenu());

            formulaire = dao.approveForm(formulaire.getId());
            entityManager.refresh(formulaireToCheck);

            Assert.assertNotNull(formulaireToCheck);
            Assert.assertEquals(formulaire.getId(), formulaireToCheck.getId());
            Assert.assertEquals(formulaire.getIdCreateur(), formulaireToCheck.getIdCreateur());
            Assert.assertEquals(formulaire.getIdTemplate(), formulaireToCheck.getIdTemplate());
            Assert.assertEquals(formulaire.getNom(), formulaireToCheck.getNom());
            Assert.assertEquals(ApprobationType.ZERO_APPROBATION.getTotalApprobation(), formulaireToCheck.getApprobation());
            Assert.assertEquals(formulaire.getVersion(), formulaireToCheck.getVersion());
            Assert.assertEquals(formulaire.getDateCreation(), formulaireToCheck.getDateCreation());
            Assert.assertEquals(formulaire.getContenu(), formulaireToCheck.getContenu());

            entityManager.getTransaction().begin();
            entityManager.remove(formulaireToCheck);
            entityManager.getTransaction().commit();
        } catch (PfeAqsException ex) {
            if (ex.getMessage().contains("always approve")) {
                return;
            }

            fail();
        }
    }

    @Test
    public void getForm() {
        try {
            DocumentDaoService dao = new DocumentDaoImpl();
            Date now = Calendar.getInstance().getTime();
            Formulaire formulaire_a = new Formulaire("Formulaire a", 1, jsonContent, now, 1, 1, ApprobationType.ONE_APPROBATION.getTotalApprobation());
            formulaire_a = dao.createForm(formulaire_a);

            Formulaire formulaire_b = new Formulaire("Formulaire b", 1, jsonContent, now, 1, 1, ApprobationType.TWO_APPROBATION.getTotalApprobation());
            formulaire_b = dao.createForm(formulaire_b);

            Formulaire formulaire_c = new Formulaire("Formulaire c", 1, jsonContent, now, 1, 1, ApprobationType.ZERO_APPROBATION.getTotalApprobation());
            formulaire_c = dao.createForm(formulaire_c);

            Formulaire formulaire_d = new Formulaire("Formulaire d", 1, jsonContent, now, 1, 1, ApprobationType.REJECTED.getTotalApprobation());
            formulaire_d = dao.createForm(formulaire_d);

            Formulaire resultApproved = dao.getForm("Formulaire c", false);
            assertNotNull(resultApproved);

            Formulaire resultRejected = dao.getForm("Formulaire d", false);
            assertNotNull(resultRejected);

            Formulaire resultNull;
            try {
                resultNull = dao.getForm("Formulaire a", false);
            } catch (NoResultException e) {

            }

            Formulaire resultNotApproved = dao.getForm("Formulaire a", true);
            assertNotNull(resultNotApproved);

            resultApproved = dao.getForm("Formulaire c", true);
            assertNotNull(resultNotApproved);

            resultRejected = dao.getForm("Formulaire d", true);
            assertNotNull(resultNotApproved);

            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Formulaire.class, formulaire_a.getId()));
            entityManager.remove(entityManager.find(Formulaire.class, formulaire_b.getId()));
            entityManager.remove(entityManager.find(Formulaire.class, formulaire_c.getId()));
            entityManager.remove(entityManager.find(Formulaire.class, formulaire_d.getId()));
            entityManager.getTransaction().commit();
        } catch (PfeAqsException ex) {
            fail();
        }
    }

    @Test
    public void getAllForm() {
        try {
            DocumentDaoService dao = new DocumentDaoImpl();
            Date now = Calendar.getInstance().getTime();
            Formulaire formulaire_a = new Formulaire("Formulaire a", 1, jsonContent, now, 1, 1, ApprobationType.ONE_APPROBATION.getTotalApprobation());
            formulaire_a = dao.createForm(formulaire_a);

            Formulaire formulaire_b = new Formulaire("Formulaire b", 1, jsonContent, now, 1, 1, ApprobationType.TWO_APPROBATION.getTotalApprobation());
            formulaire_b = dao.createForm(formulaire_b);

            Formulaire formulaire_c = new Formulaire("Formulaire c", 1, jsonContent, now, 1, 1, ApprobationType.ZERO_APPROBATION.getTotalApprobation());
            formulaire_c = dao.createForm(formulaire_c);

            Formulaire formulaire_d = new Formulaire("Formulaire d", 1, jsonContent, now, 1, 1, ApprobationType.REJECTED.getTotalApprobation());
            formulaire_d = dao.createForm(formulaire_d);

            List<Formulaire> resultApproved = dao.getAllForm(false);
            assertNotNull(resultApproved);
            Assert.assertEquals(2, resultApproved.size());
            String nameForm1 = resultApproved.get(0).getNom();
            String nameForm2 = resultApproved.get(1).getNom();
            
            Assert.assertTrue("Formulaire c".equals(nameForm1) || "Formulaire d".equals(nameForm1));
            Assert.assertTrue("Formulaire c".equals(nameForm2) || "Formulaire d".equals(nameForm2));
            
            List<Formulaire> resultAll = dao.getAllForm(true);
            assertNotNull(resultAll);
            Assert.assertEquals(4, resultAll.size());

            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Formulaire.class, formulaire_a.getId()));
            entityManager.remove(entityManager.find(Formulaire.class, formulaire_b.getId()));
            entityManager.remove(entityManager.find(Formulaire.class, formulaire_c.getId()));
            entityManager.remove(entityManager.find(Formulaire.class, formulaire_d.getId()));
            entityManager.getTransaction().commit();
        } catch (PfeAqsException ex) {
            fail();
        }
    }
}
