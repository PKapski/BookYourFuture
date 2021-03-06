package polsl.project.pp.BookYourFuture.services.interfaces;

import polsl.project.pp.BookYourFuture.entities.ServiceCategory;

import java.util.List;

public interface ServiceCategoryService {
    public List<ServiceCategory> findAll();

    public ServiceCategory findById(int theId);

    public List<ServiceCategory> findByName(String categoryName);

    public List<String> findAllName();

    public void save(ServiceCategory theService);

    public void deleteById(int theId);
}
