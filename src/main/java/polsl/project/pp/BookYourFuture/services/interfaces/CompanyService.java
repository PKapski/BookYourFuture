package polsl.project.pp.BookYourFuture.services.interfaces;

import polsl.project.pp.BookYourFuture.entities.Company;
import polsl.project.pp.BookYourFuture.entities.User;

import java.util.List;

public interface CompanyService {
    public List<Company> findAll();

    public List<Company> findAllByUser(User user);

    public Company findById(int theId);

    public void save(Company theCompany, String theUsername);

    public void deleteById(int theId);

    public Company findByNIP(String nip);

    public Company findByName(String name);

    public void updateCompany(int id,Company company);//id is id of company that will be updated

    public String checkHours(String open, String close);
}
