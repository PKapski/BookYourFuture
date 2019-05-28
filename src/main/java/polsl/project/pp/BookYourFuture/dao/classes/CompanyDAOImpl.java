package polsl.project.pp.BookYourFuture.dao.classes;

import org.hibernate.Session;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import polsl.project.pp.BookYourFuture.dao.interfaces.CompanyDAO;
import polsl.project.pp.BookYourFuture.dao.interfaces.UserDAO;
import polsl.project.pp.BookYourFuture.entities.Company;
import polsl.project.pp.BookYourFuture.entities.User;
import polsl.project.pp.BookYourFuture.services.classes.UserServiceImpl;
import polsl.project.pp.BookYourFuture.services.interfaces.UserService;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CompanyDAOImpl implements CompanyDAO {
    public EntityManager entityManager;

    @Autowired
    public CompanyDAOImpl(EntityManager theEntityManager) {entityManager = theEntityManager;}

    @Override
    public List<Company> findAll(){
        Session session = entityManager.unwrap(Session.class);
        Query<Company> theQuery = session.createQuery("from Company", Company.class);
        List <Company> companies = theQuery.getResultList();
        return companies;
    }
    @Override
    public List<Company> findAllByUser(User user){
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from Company C where C.user=:user")
                .setParameter("user",user).getResultList();

    }
    @Override
    public Company findById(int theId) {
        Session session = entityManager.unwrap(Session.class);
        Company company = session.get(Company.class, theId);
        return company;
    }

    @Override
    public void save(Company theCompany, String theUsername) {
        Session session = entityManager.unwrap(Session.class);
        UserService userService = new UserServiceImpl(new UserDAOImpl(entityManager));
        User user = userService.findByUsername(theUsername);
        theCompany.setUser(user);
        session.save(theCompany);

    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        Session session = entityManager.unwrap(Session.class);
        Query<Company> theQuery = session.createQuery("DELETE from Company where id= :theId");
        theQuery.setParameter("theId", theId);
        theQuery.executeUpdate();

    }

}
