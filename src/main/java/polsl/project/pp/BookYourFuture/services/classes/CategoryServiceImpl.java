package polsl.project.pp.BookYourFuture.services.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polsl.project.pp.BookYourFuture.dao.interfaces.CategoryDAO;
import polsl.project.pp.BookYourFuture.dao.interfaces.ServiceCategoryDAO;
import polsl.project.pp.BookYourFuture.entities.Category;
import polsl.project.pp.BookYourFuture.entities.ServiceCategory;
import polsl.project.pp.BookYourFuture.services.interfaces.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private polsl.project.pp.BookYourFuture.dao.interfaces.CategoryDAO categoryDAO;

    @Autowired
    public CategoryServiceImpl(CategoryDAO thsCompanyDAO) {categoryDAO = thsCompanyDAO;}

    @Override
    public List<Category> findAll() {
        return categoryDAO.findAll();
    }

    @Override
    public Category findById(int theId) {
        return categoryDAO.findById(theId);
    }

    @Override
    public List<Category> findByName(String categoryName) {
        return categoryDAO.findByName(categoryName);
    }

    public List<String> findAllName(){
        return categoryDAO.findAllName();
    }

    @Override
    public void save(Category theCategory) {
        categoryDAO.save(theCategory);
    }

    @Override
    public void deleteById(int theId) {
        categoryDAO.deleteById(theId);
    }
}
