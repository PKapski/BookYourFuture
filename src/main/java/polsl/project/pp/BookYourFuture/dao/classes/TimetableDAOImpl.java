package polsl.project.pp.BookYourFuture.dao.classes;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import polsl.project.pp.BookYourFuture.dao.interfaces.TimetableDAO;
import polsl.project.pp.BookYourFuture.entities.Company;
import polsl.project.pp.BookYourFuture.entities.Service;
import polsl.project.pp.BookYourFuture.entities.Timetable;
import polsl.project.pp.BookYourFuture.entities.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class TimetableDAOImpl implements TimetableDAO {

    public EntityManager entityManager;

    @Autowired
    public TimetableDAOImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public void save(Timetable theTimetable) {
        Session session = entityManager.unwrap(Session.class);

        session.save(theTimetable);
    }

    @Override
    public void deleteById(int theId) {
        Session session = entityManager.unwrap(Session.class);

        session.beginTransaction();

        session.delete(findById(theId));

        session.getTransaction().commit();

    }

    @Override
    public List<Timetable> findAll() {
        Session session = entityManager.unwrap(Session.class);

        Query<Timetable> theQuery = session.createQuery("from Timetable", Timetable.class);

        List<Timetable> timetables = theQuery.getResultList();

        return timetables;
    }

    @Override
    public Timetable findById(int theId) {
        Session session = entityManager.unwrap(Session.class);

        Timetable theTimetable = session.get(Timetable.class, theId);

        return theTimetable;
    }
    public List<Timetable> findByService(Service service) {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from Timetable where service=:service")
                .setParameter("service",service).getResultList();

    }

    @Override
    public List<Timetable> findByServiceAndDate(String theDate, Service service) {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from Timetable where service=:service and date=:date")
                .setParameter("date", theDate)
                .setParameter("service", service)
                .getResultList();

    }

    @Override
    public List<Timetable> getByUser(User user){
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from Timetable where user=:user")
                .setParameter("user", user)
                .getResultList();
    }
}
