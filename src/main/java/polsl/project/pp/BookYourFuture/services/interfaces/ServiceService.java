package polsl.project.pp.BookYourFuture.services.interfaces;

import polsl.project.pp.BookYourFuture.entities.Company;
import polsl.project.pp.BookYourFuture.entities.Service;
import polsl.project.pp.BookYourFuture.entities.ServiceCategory;

import java.util.List;

public interface ServiceService {
    public List<Service> findAll();

    public Service findById(int theId);

    public List<Service> findByCatSerId(ServiceCategory theId);

    public void save(Service theService);

    public void deleteById(int theId);

    public void updateService(int id, String name, int duration);

}
