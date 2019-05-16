package polsl.project.pp.BookYourFuture.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import polsl.project.pp.BookYourFuture.entities.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO{

    //define field for EntityManager
    public EntityManager entityManager;

    //set up constructor incjection
    @Autowired
    public UserDAOImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public List<User> findAll() {
        Session session = entityManager.unwrap(Session.class);

        Query<User> theQuery = session.createQuery("from User", User.class);

        List<User> users = theQuery.getResultList();

        return users;
    }

    @Override
    public User findById(int theId) {
        Session session = entityManager.unwrap(Session.class);

        User theUser = session.get(User.class, theId);

        return theUser;
    }

    @Override
    public void save(User theUser) {
        Session session = entityManager.unwrap(Session.class);

        session.save(theUser);
    }

    @Override
    public void deleteById(int theId) {
        Session session = entityManager.unwrap(Session.class);

        Query theQuery = session.createQuery("delete from User where id=:userId");

        theQuery.setParameter("userId", theId);

        theQuery.executeUpdate();
    }
}
