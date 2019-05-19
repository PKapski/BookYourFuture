package polsl.project.pp.BookYourFuture.services.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polsl.project.pp.BookYourFuture.dao.interfaces.TimetableDAO;
import polsl.project.pp.BookYourFuture.entities.Timetable;
import polsl.project.pp.BookYourFuture.services.interfaces.TimetableService;

import java.util.List;

@Service
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
    public void save(Timetable theTimetable) {
        timetableDAO.save(theTimetable);
    }

    @Override
    public void deleteById(int theId) {
        timetableDAO.deleteById(theId);
    }
}
