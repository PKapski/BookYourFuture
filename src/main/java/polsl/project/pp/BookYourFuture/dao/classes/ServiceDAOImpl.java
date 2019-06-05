package polsl.project.pp.BookYourFuture.dao.classes;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import polsl.project.pp.BookYourFuture.dao.interfaces.ServiceDAO;
import polsl.project.pp.BookYourFuture.entities.Service;

import javax.persistence.EntityManager;
import org.hibernate.Query;
import polsl.project.pp.BookYourFuture.entities.ServiceCategory;

import java.util.List;

@Repository
public class ServiceDAOImpl implements ServiceDAO {

    public EntityManager entityManager;

    @Autowired
    public ServiceDAOImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public List<Service> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<Service> theQuery = session.createQuery("from Service", Service.class);
        List <Service> services = theQuery.getResultList();

        return services;
    }

    @Override
    public Service findById(int theId) {
        Session session = entityManager.unwrap(Session.class);
        Service service = session.get(Service.class, theId);
        return service;
    }

    @Override
    public List<Service> findByCatSerId(ServiceCategory theId) {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from Service where servicesCategories=:theId")
                .setParameter("theId",theId).getResultList();
    }

    @Override
    public void save(Service theService) {
        Session session = entityManager.unwrap(Session.class);
        session.save(theService);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        Session session = entityManager.unwrap(Session.class);
        Query theQuery = session.createQuery("delete from Service where id=:theId");
        theQuery.setParameter("theId", theId);
        theQuery.executeUpdate();
    }
}
