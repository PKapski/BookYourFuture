package polsl.project.pp.BookYourFuture.dao.classes;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
        return session.createQuery("from Service where category_service_id=:theId")
                .setParameter("theId",theId.getId()).getResultList();
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
    @Override
    public void updateService(int id, String name, int duration){
        Session session = entityManager.unwrap(Session.class);
        Transaction tx=null;
        try {
            tx = session.beginTransaction();
            Service service=session.get(Service.class,id);
            if (!name.equals(""))
                service.setName(name);
            if (duration>=0)
                service.setDuration(duration);
            session.update(service);
            tx.commit();
        }
        catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            throw e;
        }
        finally {
            session.close();
        }
    }
}
