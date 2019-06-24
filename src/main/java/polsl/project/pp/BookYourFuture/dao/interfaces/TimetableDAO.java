package polsl.project.pp.BookYourFuture.dao.interfaces;

import polsl.project.pp.BookYourFuture.entities.Company;
import polsl.project.pp.BookYourFuture.entities.Service;
import polsl.project.pp.BookYourFuture.entities.Timetable;
import polsl.project.pp.BookYourFuture.entities.User;

import java.sql.Time;
import java.util.List;

public interface TimetableDAO {
    public List<Timetable> findAll();

    public Timetable findById(int theId);

    public List<Timetable> findByService(Service service);

    public List<Timetable> findByServiceAndDate(String theDate, Service service);

    public void save(Timetable theTimetable);

    public void deleteById(int theId);

    public List<Timetable> getByUser(User user);
}
