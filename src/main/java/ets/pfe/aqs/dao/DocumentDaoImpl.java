package ets.pfe.aqs.dao;

import ets.pfe.aqs.dao.service.DocumentDaoService;
import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.ApprobationType;
import ets.pfe.aqs.modele.Formulaire;
import ets.pfe.aqs.util.JPAUtility;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zeldorine
 */
public class DocumentDaoImpl implements DocumentDaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentDaoImpl.class);

    private static final String getALlFormQueryWithNoApproveForm = "From Formulaire";
    private static final String getALlFormQueryWithApproveForm = "From Formulaire where approbation <= 0";
    private static final String getFormQueryWithNoApproveForm = "From Formulaire where nom = :nom";
    private static final String getFormQueryWithApproveForm = "From Formulaire where nom = :nom And approbation <= 0";

    @Override
    public List<Formulaire> getAllForm(boolean includeNotApproveForm) throws PfeAqsException, NoResultException {
        LOGGER.info("Get all forms");
        EntityManager entityManager = JPAUtility.openEntityManager();
        TypedQuery<Formulaire> query;

        if (includeNotApproveForm) {
            query = entityManager.createQuery(getALlFormQueryWithNoApproveForm, Formulaire.class);
        } else {
            query = entityManager.createQuery(getALlFormQueryWithApproveForm, Formulaire.class);
        }

        List<Formulaire> formulaire = query.getResultList();
        LOGGER.info(formulaire.size() + " forms are found");

        JPAUtility.closeEntityManager(entityManager);
        return formulaire;
    }

    @Override
    public Formulaire getForm(String formName, boolean includeNotApproveForm) throws PfeAqsException {
        LOGGER.info("Get form " + formName);
        EntityManager entityManager = JPAUtility.openEntityManager();
        TypedQuery<Formulaire> query;

        if (includeNotApproveForm) {
            query = entityManager.createQuery(getFormQueryWithNoApproveForm, Formulaire.class);
        } else {
            query = entityManager.createQuery(getFormQueryWithApproveForm, Formulaire.class);
        }

        query.setParameter("nom", formName);

        Formulaire formulaire = query.getSingleResult();
        JPAUtility.closeEntityManager(entityManager);

        LOGGER.info("Form " + formName);

        return formulaire;
    }

    @Override
    public Formulaire approveForm(long id) throws PfeAqsException {
        LOGGER.info("Approving Form with id: " + id);
        EntityManager entityManager = JPAUtility.openEntityManager();
        Formulaire form = entityManager.find(Formulaire.class, id);

        if (form != null) {
            if (form.getApprobation() > 0) {
                entityManager.getTransaction().begin();
                form.setApprobation(form.getApprobation() - 1);
                entityManager.getTransaction().commit();
            } else {
                throw new PfeAqsException("The form id" + id + " is always approve.");
            }
        } else {
            throw new PfeAqsException("The form id " + id + " dosen't exists in database.");
        }

        JPAUtility.closeEntityManager(entityManager);
        LOGGER.info("Form with id: " + id + " approved. Approbation level: " + form.getApprobation());

        return form;
    }

    @Override
    public Formulaire rejectForm(long id) throws PfeAqsException {
        LOGGER.info("Rejecting Form with id: " + id);
        EntityManager entityManager = JPAUtility.openEntityManager();
        Formulaire form = entityManager.find(Formulaire.class, id);

        if (form != null) {
            if (form.getApprobation() > 0) {
                entityManager.getTransaction().begin();
                form.setApprobation(ApprobationType.REJECTED.getTotalApprobation());
                entityManager.getTransaction().commit();
            } else {
                throw new PfeAqsException("The form id" + id + " is always approve.");
            }
        } else {
            throw new PfeAqsException("The form id " + id + " dosen't exists in database.");
        }

        JPAUtility.closeEntityManager(entityManager);
        LOGGER.info("Form with id: " + id + " rejected");

        return form;
    }

    @Override
    public Formulaire createForm(Formulaire form) throws PfeAqsException {
        LOGGER.info("Creating form with name: " + form.getNom());
        EntityManager entityManager = JPAUtility.openEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(form);
        entityManager.getTransaction().commit();
        JPAUtility.closeEntityManager(entityManager);

        LOGGER.info("Form with name: " + form.getNom() + " created");
        return form;
    }
}
