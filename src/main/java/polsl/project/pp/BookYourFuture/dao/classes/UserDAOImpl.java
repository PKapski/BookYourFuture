package polsl.project.pp.BookYourFuture.dao.classes;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import polsl.project.pp.BookYourFuture.dao.interfaces.UserDAO;
import polsl.project.pp.BookYourFuture.entities.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

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
        theUser.setRole("GUEST");
        session.save(theUser);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        Session session = entityManager.unwrap(Session.class);

        Query theQuery = session.createQuery("delete from User where id=:userId");

        theQuery.setParameter("userId", theId);

        theQuery.executeUpdate();
    }

    @Override
    public User findByUsername(String theUsername){
        Session session = entityManager.unwrap(Session.class);

        Query<User> theQuery = session.createQuery("from User where login=:theUsername", User.class);

        theQuery.setParameter("theUsername", theUsername);
        try {
            User user = theQuery.getSingleResult();
            return user;
        }catch(Exception e){return null;}

    }
    @Override
    public User findByEmail(String email){
        Session session = entityManager.unwrap(Session.class);

        Query<User> theQuery = session.createQuery("from User where email=:email", User.class);

        theQuery.setParameter("email", email);

        try {
            User user = theQuery.getSingleResult();
            return user;
        }catch(Exception e){return null;}
    }
    @Override
    public User findByPhone(String phone){
        Session session = entityManager.unwrap(Session.class);

        Query<User> theQuery = session.createQuery("from User where phone=:phone", User.class);

        theQuery.setParameter("phone", phone);

        try {
            User user = theQuery.getSingleResult();
            return user;
        }catch(Exception e){return null;}
    }

    @Override
    public void updateUser(int id,User user){
        Session session = entityManager.unwrap(Session.class);
        Transaction tx=null;
        try {
            tx = session.beginTransaction();
            User currUser = session.get(User.class,id);
            if (!user.getFirstName().equals(""))
                currUser.setFirstName(user.getFirstName());
            if (!user.getLastName().equals(""))
                currUser.setLastName(user.getLastName());
            if (!user.getPhone().equals(""))
                currUser.setPhone(user.getPhone());
            if (!user.getEmail().equals(""))
                currUser.setEmail(user.getEmail());
            if (!user.getLogin().equals("")) {
                currUser.setLogin(user.getLogin());
            }
            if (!user.getPassword().equals("")){
                currUser.setPassword("{noop}"+ user.getPassword());
            }
            session.update(currUser);
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
    @Override
    public boolean hasEmptyValues(User user){
        if (StringUtils.isBlank(user.getFirstName())||
                StringUtils.isBlank(user.getLastName())||
                StringUtils.isBlank(user.getLogin())||
                StringUtils.isBlank( user.getPhone())||
                StringUtils.isBlank( user.getPassword())||
                StringUtils.isBlank(user.getEmail()))
            return true;
        return false;

    }
}
