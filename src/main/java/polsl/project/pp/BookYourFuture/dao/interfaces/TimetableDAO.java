package polsl.project.pp.BookYourFuture.dao.interfaces;

import polsl.project.pp.BookYourFuture.entities.Timetable;

import java.util.List;

public interface TimetableDAO {
    public List<Timetable> findAll();

    public Timetable findById(int theId);

    public void save(Timetable theTimetable);

    public void deleteById(int theId);
}
