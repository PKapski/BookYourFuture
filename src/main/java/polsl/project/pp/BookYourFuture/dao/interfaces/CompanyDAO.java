package polsl.project.pp.BookYourFuture.dao.interfaces;

import polsl.project.pp.BookYourFuture.entities.Company;

import java.util.List;

public interface CompanyDAO {
    public List<Company> findAll();

    public Company findById(int theId);

    public void save(Company theCompany);

    public void deleteById(int theId);
}
