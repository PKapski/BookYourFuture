package polsl.project.pp.BookYourFuture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import polsl.project.pp.BookYourFuture.entities.User;
import polsl.project.pp.BookYourFuture.entities.Company;
import polsl.project.pp.BookYourFuture.services.interfaces.CompanyService;
import polsl.project.pp.BookYourFuture.services.interfaces.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private UserService userService;

    private CompanyService companyService;

    @Autowired
    public UserController(UserService theUserService, CompanyService theCompanyService){
        userService = theUserService;
        companyService = theCompanyService;
    }

    @GetMapping("/")
    public String index(Model model, Principal principal){
        //model.addAttribute("loggedInUser",principal);
        return "index";
    }
    @GetMapping("/registerUser")
    public String registerUser(Model model){
        model.addAttribute("user",new User());
        return "registerUser";
    }
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user")User user){
        user.setPassword("{noop}"+user.getPassword());
        userService.save(user);
        System.out.println(user.getLogin());

        return "redirect:/";
    }
    @GetMapping("/aboutUs")
    public String aboutUs(){
        return "aboutUs";
    }
    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }

    @GetMapping("/addCompany")
    public String addCompany(Model model){
        model.addAttribute("company" , new Company());
        return "addCompany";
    }

    @PostMapping("/saveCompany")
    public String saveCompany(@ModelAttribute("company")Company company){

        //retrieve only user name :((((
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            System.out.println(currentUserName + " ");
        }

        User u = userService.findById(1);
        company.setUser(u);
        companyService.save(company);
        return "redirect:/";
    }
}
