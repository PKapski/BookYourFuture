package polsl.project.pp.BookYourFuture.services.interfaces;

import polsl.project.pp.BookYourFuture.entities.Company;

import java.util.List;

public interface CompanyService {
    public List<Company> findAll();

    public Company findById(int theId);

    public void save(Company theCompany);

    public void deleteById(int theId);
}
