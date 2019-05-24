package polsl.project.pp.BookYourFuture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import polsl.project.pp.BookYourFuture.entities.ServiceCategory;
import polsl.project.pp.BookYourFuture.entities.User;
import polsl.project.pp.BookYourFuture.entities.Company;
import polsl.project.pp.BookYourFuture.services.interfaces.CompanyService;
import polsl.project.pp.BookYourFuture.services.interfaces.ServiceCategoryService;
import polsl.project.pp.BookYourFuture.services.interfaces.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private UserService userService;

    private CompanyService companyService;

    private ServiceCategoryService serviceCategoryService;

    @Autowired
    public UserController(UserService theUserService, CompanyService theCompanyService, ServiceCategoryService theServiceCategoryService){
        userService = theUserService;
        companyService = theCompanyService;
        serviceCategoryService = theServiceCategoryService;
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
       // model.addAttribute("serviceCategory", new ServiceCategory());
        model.addAttribute("company" , new Company());
        return "addCompany";
    }
    @GetMapping("/myCompanies")
    public String myCompanies(Model model){
        // model.addAttribute("serviceCategory", new ServiceCategory());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByUsername(authentication.getName());
        model.addAttribute("companiesList" , companyService.findAllByUser(user));
        return "myCompanies";
    }

    @PostMapping("/saveCompany")
    public String saveCompany(@ModelAttribute("company")Company company,@RequestParam("categoryName") String categoryName){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            companyService.save(company,authentication.getName());
             ServiceCategory serviceCategory;// = serviceCategoryService.findByName(categoryName);
//            if(serviceCategory==null){
//                System.out.println("created serviceCategory");
                serviceCategory = new ServiceCategory();
                serviceCategory.setCategoryName(categoryName);
                serviceCategory.setCompany(company);
//            }
            serviceCategoryService.save(serviceCategory);
        }


        return "redirect:/";
    }

    @GetMapping("/editAccount")
    public String editAccount(Model model){

/*        User u = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentLogin = authentication.getName();
            u = userService.findByUsername(currentLogin);
        }*/
        model.addAttribute("user", new User());
        return "editAccount";
    }

 /*   @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("updateUser")User user){
        user.setPassword("{noop}"+user.getPassword());
        //userService.save(user);
        System.out.println(user.getLogin());
        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        System.out.println(user.getPassword());

        return "redirect:/";
    }*/
 //@RequestMapping(method = RequestMethod.POST, value = "/updateUser")
 @PostMapping("/updateUser")
 //@ResponseBody
    //public String updateUser(@RequestBody User user, @PathVariable String id){
     public String updateUser(
             @RequestParam("phone") String phone,
             @RequestParam("currentPassword") String currPass,
             @RequestParam("password") String password,
             @RequestParam("repeatPassword") String repeatPassword){

     User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
     currPass = "{noop}" + currPass;
     if(user.getPassword().equals(currPass)){
         if ((password.equals("") || repeatPassword.equals(""))) {
             userService.updateUserPhone(user, phone);
             System.out.println("changed only phone");
         }
         else if (phone.equals("") && password.equals(repeatPassword)) {
             userService.updateUserPass(user, password);
             System.out.println("changed only password");
         }
         else if (password.equals(repeatPassword)) {
             userService.updateUser(user, phone, password);
             System.out.println("changed phone and password");
         }
     }
     else {
         System.out.println("Invalid password"); // TO DO
     }

     return "redirect:/";
 }
}
