package polsl.project.pp.BookYourFuture.dao.classes;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import polsl.project.pp.BookYourFuture.dao.interfaces.ServiceCategoryDAO;
import polsl.project.pp.BookYourFuture.entities.ServiceCategory;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ServiceCategoryDAOImpl implements ServiceCategoryDAO {

    public EntityManager entityManager;

    @Autowired
    public ServiceCategoryDAOImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public List<ServiceCategory> findAll() {
        Session session = entityManager.unwrap(Session.class);

        Query<ServiceCategory> theQuery = session.createQuery("from ServiceCategory", ServiceCategory.class);

        List<ServiceCategory> serviceCategories = theQuery.getResultList();

        return serviceCategories;
    }

    @Override
    public List<String> findAllName(){
        Session session = entityManager.unwrap(Session.class);

        Query<String> theQuery = session.createQuery("select distinct categoryName from ServiceCategory", String.class);

        List<String> serviceCategoriesName = theQuery.getResultList();

        return serviceCategoriesName;
    }

    @Override
    public ServiceCategory findById(int theId) {
        Session session = entityManager.unwrap(Session.class);

        ServiceCategory theServiceCategory = session.get(ServiceCategory.class, theId);

        return theServiceCategory;
    }

    @Override
    @Transactional
    public List <ServiceCategory> findByName(String categoryName) {
        Session session = entityManager.unwrap(Session.class);

        Query<ServiceCategory> theQuery = session.createQuery("from ServiceCategory where categoryName=:theCategoryName");

        theQuery.setParameter("theCategoryName", categoryName);

        return theQuery.getResultList();

    }

    @Override
    public void save(ServiceCategory theServiceCategory) {
        Session session = entityManager.unwrap(Session.class);

        session.save(theServiceCategory);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        Session session = entityManager.unwrap(Session.class);

        Query theQuery = session.createQuery("delete from ServiceCategory where id=:theId");

        theQuery.setParameter("theId", theId);

        theQuery.executeUpdate();
    }
}
