package polsl.project.pp.BookYourFuture.services.interfaces;

import polsl.project.pp.BookYourFuture.entities.Service;

import java.util.List;

public interface ServiceService {
    public List<Service> findAll();

    public Service findById(int theId);

    public void save(Service theService);

    public void deleteById(int theId);
}
