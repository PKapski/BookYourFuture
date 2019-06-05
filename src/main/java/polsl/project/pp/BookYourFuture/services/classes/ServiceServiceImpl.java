package polsl.project.pp.BookYourFuture.services.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polsl.project.pp.BookYourFuture.dao.interfaces.ServiceDAO;
import polsl.project.pp.BookYourFuture.entities.ServiceCategory;
import polsl.project.pp.BookYourFuture.services.interfaces.ServiceService;

import java.util.List;

@Service
public class ServiceServiceImpl implements ServiceService {
    private ServiceDAO serviceDao;

    @Autowired
    public ServiceServiceImpl(ServiceDAO theServiceDao) {serviceDao = theServiceDao;}

    @Override
    public List<polsl.project.pp.BookYourFuture.entities.Service> findAll() {
        return serviceDao.findAll();
    }

    @Override
    public polsl.project.pp.BookYourFuture.entities.Service findById(int theId) {
        return serviceDao.findById(theId);
    }

    @Override
    public List<polsl.project.pp.BookYourFuture.entities.Service> findByCatSerId(ServiceCategory theId) {
        return serviceDao.findByCatSerId(theId);
    }

    @Override
    public void save(polsl.project.pp.BookYourFuture.entities.Service theService) {
        serviceDao.save(theService);
    }

    @Override
    public void deleteById(int theId) {
        serviceDao.deleteById(theId);
    }
}
