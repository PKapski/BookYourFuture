package polsl.project.pp.BookYourFuture.dao.classes;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
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
    @Transactional
    public void updateUser(User theUser, String phone, String password){
        Session session = entityManager.unwrap(Session.class);
        int id_user = theUser.getId();
        password = "{noop}" + password;
        Query<User> theQuery = session.createQuery("update User SET phone=:phone, password=:password where id=:id_user");
        theQuery.setParameter("phone", phone);
        theQuery.setParameter("password", password);
        theQuery.setParameter("id_user", id_user);
        theQuery.executeUpdate();

    }

    @Override
    @Transactional
    public void updateUserPhone(User theUser, String phone){
        Session session = entityManager.unwrap(Session.class);
        int id_user = theUser.getId();
        Query<User> theQuery = session.createQuery("update User SET phone=:phone where id=:id_user");
        theQuery.setParameter("phone", phone);
        theQuery.setParameter("id_user", id_user);
        theQuery.executeUpdate();
    }

    @Override
    @Transactional
    public void updateUserPass(User theUser, String password){
        Session session = entityManager.unwrap(Session.class);
        int id_user = theUser.getId();
        password = "{noop}" + password;
        Query<User> theQuery = session.createQuery("update User SET password=:password where id=:id_user");
        theQuery.setParameter("password", password);
        theQuery.setParameter("id_user", id_user);
        theQuery.executeUpdate();
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
