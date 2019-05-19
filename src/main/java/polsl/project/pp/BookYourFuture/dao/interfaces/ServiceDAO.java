package polsl.project.pp.BookYourFuture.dao.interfaces;

import polsl.project.pp.BookYourFuture.entities.Service;

import java.util.List;

public interface ServiceDAO {
    public List<Service> findAll();

    public  Service findById(int theId);

    public void save( Service theService);

    public void deleteById(int theId);
}
