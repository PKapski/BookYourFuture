package polsl.project.pp.BookYourFuture.services.interfaces;

import polsl.project.pp.BookYourFuture.entities.Category;
import polsl.project.pp.BookYourFuture.entities.ServiceCategory;

import java.util.List;

public interface CategoryService {
    public List<Category> findAll();

    public Category findById(int theId);

    public List<Category> findByName(String categoryName);

    public List<String> findAllName();

    public void save(Category theService);

    public void deleteById(int theId);
}
