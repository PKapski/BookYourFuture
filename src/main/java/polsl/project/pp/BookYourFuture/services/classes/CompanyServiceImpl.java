package polsl.project.pp.BookYourFuture.services.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polsl.project.pp.BookYourFuture.dao.interfaces.CompanyDAO;
import polsl.project.pp.BookYourFuture.entities.Company;
import polsl.project.pp.BookYourFuture.entities.User;
import polsl.project.pp.BookYourFuture.services.interfaces.CompanyService;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyDAO companyDAO;

    @Autowired
    public CompanyServiceImpl(CompanyDAO thsCompanyDAO) {companyDAO = thsCompanyDAO;}
    @Override
    public List<Company> findAll() {
        return companyDAO.findAll();
    }
    @Override
    public List<Company> findAllByUserId(int id){return companyDAO.findAllByUserId(id); }

    @Override
    public Company findById(int theId) {
        return companyDAO.findById(theId);
    }

    @Override
    public void save(Company theCompany, String theUsername) {
        companyDAO.save(theCompany,theUsername);
    }

    @Override
    public void deleteById(int theId) {
        companyDAO.deleteById(theId);
    }
    @Override
    public Company findByNIP(String nip){
        return companyDAO.findByNIP(nip);
    }
    @Override
    public void updateCompany(int id,Company company){ //id is id of company that will be updated
        companyDAO.updateCompany(id,company);
    }
    @Override
    public Company findByName(String name){
        return companyDAO.findByName(name);
    }
    @Override
    public String checkHours(String open, String close){return companyDAO.checkHours(open,close);}
    @Override
    public List<polsl.project.pp.BookYourFuture.entities.Service> getServices(Company company){return companyDAO.getServices(company);}
    @Override
    public boolean hasEmptyValues(Company company){return companyDAO.hasEmptyValues(company);}
    @Override
    public String checkHours(String hour){return companyDAO.checkHours(hour);}
}

