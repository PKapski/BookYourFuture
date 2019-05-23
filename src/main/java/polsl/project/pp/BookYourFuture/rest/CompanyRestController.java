package polsl.project.pp.BookYourFuture.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import polsl.project.pp.BookYourFuture.entities.Company;
import polsl.project.pp.BookYourFuture.services.interfaces.CompanyService;

import java.util.List;

@RestController
public class CompanyRestController {
    private CompanyService companyService;

    @Autowired
    public CompanyRestController(CompanyService theCompanyService){
        companyService = theCompanyService;
    }

    @GetMapping("/companies/findAll")
    public List<Company> findAll(){
        return companyService.findAll();
    }

    @GetMapping("/companies/findById")
    public Company findById(){
        return companyService.findById(3);
    }


    @GetMapping("companies/deleteById")
    public void deleteById(){
        companyService.deleteById(2);
        System.out.println("DELETED");
    }
}
