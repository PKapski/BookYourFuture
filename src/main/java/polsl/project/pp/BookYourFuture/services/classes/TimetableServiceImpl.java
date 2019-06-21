package polsl.project.pp.BookYourFuture.services.classes;

import org.springframework.beans.factory.annotation.Autowired;
import polsl.project.pp.BookYourFuture.dao.interfaces.TimetableDAO;
import polsl.project.pp.BookYourFuture.entities.Service;
import polsl.project.pp.BookYourFuture.entities.Timetable;
import polsl.project.pp.BookYourFuture.entities.User;
import polsl.project.pp.BookYourFuture.services.interfaces.TimetableService;

import java.util.List;

@org.springframework.stereotype.Service
public class TimetableServiceImpl implements TimetableService {

    private TimetableDAO timetableDAO;

    @Autowired
    public TimetableServiceImpl(TimetableDAO theTimetableDAO) {timetableDAO = theTimetableDAO; }

    @Override
    public List<Timetable> findAll() {
        return timetableDAO.findAll();
    }

    @Override
    public Timetable findById(int theId) {
        return timetableDAO.findById(theId);
    }

    @Override
    public List<Timetable> findByService(polsl.project.pp.BookYourFuture.entities.Service service) {
        return timetableDAO.findByService(service);
    }

    @Override
    public List<Timetable> findByServiceAndDate(String theDate, Service service) {
        return timetableDAO.findByServiceAndDate(theDate,service);
    }

    @Override
    public void save(Timetable theTimetable) {
        timetableDAO.save(theTimetable);
    }

    @Override
    public void deleteById(int theId) {
        timetableDAO.deleteById(theId);
    }

    @Override
    public List<Timetable> getByUser(User user){
        return timetableDAO.getByUser(user);
    }

}
