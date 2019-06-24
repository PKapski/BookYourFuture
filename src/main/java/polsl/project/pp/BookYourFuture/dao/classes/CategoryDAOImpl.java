package polsl.project.pp.BookYourFuture.dao.classes;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import polsl.project.pp.BookYourFuture.dao.interfaces.CategoryDAO;
import polsl.project.pp.BookYourFuture.entities.Category;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

    public EntityManager entityManager;

    @Autowired
    public CategoryDAOImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public List<Category> findAll() {
        Session session = entityManager.unwrap(Session.class);

        Query<Category> theQuery = session.createQuery("from Category", Category.class);

        List<Category> categories = theQuery.getResultList();

        return categories;
    }

    @Override
    public List<String> findAllName(){
        Session session = entityManager.unwrap(Session.class);

        Query<String> theQuery = session.createQuery("select distinct categoryName from Category", String.class);

        List<String> categoriesName = theQuery.getResultList();

        return categoriesName;
    }

    @Override
    public Category findById(int theId) {
        Session session = entityManager.unwrap(Session.class);

        Category theCategory = session.get(Category.class, theId);

        return theCategory;
    }

    @Override
    @Transactional
    public List<Category> findByName(String categoryName) {
        Session session = entityManager.unwrap(Session.class);

        Query<Category> theQuery = session.createQuery("from Category where categoryName=:theCategoryName");

        theQuery.setParameter("theCategoryName", categoryName);

        return theQuery.getResultList();

    }

    @Override
    public void save(Category theCategory) {
        Session session = entityManager.unwrap(Session.class);

        session.save(theCategory);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        Session session = entityManager.unwrap(Session.class);

        Query theQuery = session.createQuery("delete from Category where id=:theId");

        theQuery.setParameter("theId", theId);

        theQuery.executeUpdate();
    }
}
