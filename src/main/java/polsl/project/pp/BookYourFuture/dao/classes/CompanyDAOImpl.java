package polsl.project.pp.BookYourFuture.dao.classes;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import polsl.project.pp.BookYourFuture.dao.interfaces.CompanyDAO;
import polsl.project.pp.BookYourFuture.dao.interfaces.UserDAO;
import polsl.project.pp.BookYourFuture.entities.Company;
import polsl.project.pp.BookYourFuture.entities.Service;
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
    public List<Company> findAllByUserId(int id){
        Session session = entityManager.unwrap(Session.class);
        return session.createSQLQuery("select * from companies where user_id=:user_id")
                .addEntity(Company.class)
                .setParameter("user_id",id).getResultList();

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
    @Override
    public Company findByNIP(String nip){
        Session session = entityManager.unwrap(Session.class);

        Query<Company> theQuery = session.createQuery("from Company where nip=:nip", Company.class);

        theQuery.setParameter("nip", nip);

        try {
            Company company = theQuery.getSingleResult();
            return company;
        }catch(Exception e){return null;}
    }
    @Override
    public void updateCompany(int id,Company company){ //id is id of company that will be updated
        Session session = entityManager.unwrap(Session.class);
        Transaction tx=null;
        try {
            tx = session.beginTransaction();
            Company comp = session.get(Company.class,id);
            if (!company.getName().equals(""))
                comp.setName(company.getName());
            if (!company.getNip().equals(""))
                comp.setNip(company.getNip());
            if (!company.getAddress().equals(""))
                comp.setAddress(company.getAddress());
            if (!company.getOpenTime().equals(""))
                comp.setOpenTime(company.getOpenTime());
            if (!company.getCloseTime().equals(""))
                comp.setCloseTime(company.getCloseTime());
            session.update(comp);
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
    public Company findByName(String name){
        Session session = entityManager.unwrap(Session.class);

        Query<Company> theQuery = session.createQuery("from Company where name=:name", Company.class);

        theQuery.setParameter("name", name);
        try {
            Company company = theQuery.getSingleResult();
            return company;
        }catch(Exception e){return null;}

    }
    @Override
    public String checkHours(String open, String close){
        if (open.trim().length()!=5 || close.trim().length()!=5)
            return "Hours must be in format HH:MM";
        else if (open.charAt(2)!=':' || close.charAt(2)!=':')
            return "Hours must be in format HH:MM";
        else {
            try {
                if (Integer.parseInt(open.substring(0, 2)) > 24 || Integer.parseInt(open.substring(0, 2)) < 0)
                    return "Opening hours must be between 0 and 24";
                else if (Integer.parseInt(close.substring(0, 2)) > 24 || Integer.parseInt(close.substring(0, 2)) < 0)
                    return "Closing hours must be between 0 and 24";
                else if (Integer.parseInt(open.substring(3, 5)) > 59 || Integer.parseInt(open.substring(0, 2)) < 0)
                    return "Opening minutes must be between 0 and 59";
                else if (Integer.parseInt(close.substring(3, 5)) > 59 || Integer.parseInt(close.substring(0, 2)) < 0)
                    return "Opening minutes must be between 0 and 59";
            }catch (Exception e){
                return "Hours and minutes values must be numeric!";
            }
        }

        return "";
    }
    @Override
    public String checkHours(String hour){
        if (hour.trim().length()!=5)
            return "Hours must be in format HH:MM";
        else if (hour.charAt(2)!=':')
            return "Hours must be in format HH:MM";
        else {
            try {
                if (Integer.parseInt(hour.substring(0, 2)) > 24 || Integer.parseInt(hour.substring(0, 2)) < 0)
                    return "Hours must be between 0 and 24";
                else if (Integer.parseInt(hour.substring(3, 5)) > 59 || Integer.parseInt(hour.substring(0, 2)) < 0)
                    return "Minutes must be between 0 and 59";
            }catch (Exception e){
                return "Hours and minutes values must be numeric!";
            }
        }

        return "";
    }
    @Override
    public List<Service> getServices(Company company){
       return company.getServicesCategories().get(0).getServices();
    }
    @Override
    public boolean hasEmptyValues(Company company){
        if (StringUtils.isBlank(company.getName())||
                StringUtils.isBlank(company.getNip())||
                StringUtils.isBlank(company.getAddress())||
                StringUtils.isBlank(company.getOpenTime())||
                StringUtils.isBlank(company.getCloseTime())){
            return true;
        }
        return false;
    }

}
