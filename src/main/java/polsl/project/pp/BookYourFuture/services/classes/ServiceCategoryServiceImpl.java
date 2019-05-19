package polsl.project.pp.BookYourFuture.services.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polsl.project.pp.BookYourFuture.dao.interfaces.ServiceCategoryDAO;
import polsl.project.pp.BookYourFuture.entities.ServiceCategory;
import polsl.project.pp.BookYourFuture.services.interfaces.ServiceCategoryService;

import java.util.List;
@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

    private ServiceCategoryDAO serviceCategoryDAO;

    @Autowired
    public ServiceCategoryServiceImpl(ServiceCategoryDAO thsCompanyDAO) {serviceCategoryDAO = thsCompanyDAO;}

    @Override
    public List<ServiceCategory> findAll() {
        return serviceCategoryDAO.findAll();
    }

    @Override
    public ServiceCategory findById(int theId) {
        return serviceCategoryDAO.findById(theId);
    }

    @Override
    public void save(ServiceCategory theServiceCategory) {
        serviceCategoryDAO.save(theServiceCategory);
    }

    @Override
    public void deleteById(int theId) {
        serviceCategoryDAO.deleteById(theId);
    }
}
