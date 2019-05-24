package polsl.project.pp.BookYourFuture.dao.interfaces;

import polsl.project.pp.BookYourFuture.entities.Company;
import polsl.project.pp.BookYourFuture.entities.User;

import java.util.List;

public interface CompanyDAO {
    public List<Company> findAll();

    public List<Company> findAllByUser(User user);

    public Company findById(int theId);

    public void save(Company theCompany, String theUsername);

    public void deleteById(int theId);

}
