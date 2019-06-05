package polsl.project.pp.BookYourFuture.dao.interfaces;

import polsl.project.pp.BookYourFuture.entities.ServiceCategory;

import java.util.List;

public interface ServiceCategoryDAO {
    public List<ServiceCategory> findAll();

    public ServiceCategory findById(int theId);

    public List<ServiceCategory> findByName(String categoryName);

    public void save(ServiceCategory theService);

    public void deleteById(int theId);
}
