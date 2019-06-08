package polsl.project.pp.BookYourFuture.controller;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import polsl.project.pp.BookYourFuture.entities.*;
import polsl.project.pp.BookYourFuture.services.interfaces.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    private UserService userService;

    private CompanyService companyService;

    private ServiceCategoryService serviceCategoryService;

    private ServiceService serviceService;

    private TimetableService timetableService;

    @Autowired
    public UserController(UserService userService, CompanyService companyService, ServiceCategoryService serviceCategoryService, ServiceService serviceService, TimetableService timetableService) {
        this.userService = userService;
        this.companyService = companyService;
        this.serviceCategoryService = serviceCategoryService;
        this.serviceService = serviceService;
        this.timetableService = timetableService;
    }

    @GetMapping("/")
    public String index(Model model, Principal principal){
        String[] categories={"Haircut","Massage","Squash","Dentist","Mechanic","Engraver"};
        model.addAttribute("categories" , categories);
        return "index";
    }

    @GetMapping("/registerUser")
    public String registerUser(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("errorText","");
        return "registerUser";
    }
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user")User user, @RequestParam(name="password2") String password2, Model model){
        if (!user.getPassword().equals(password2)) {
             model.addAttribute("errorText", "Passwords are not equal!");
        } else if (userService.findByUsername(user.getLogin())!=null){
                model.addAttribute("errorText","User with this login already exists!");
        }
        else if (userService.findByEmail(user.getEmail())!=null){
                model.addAttribute("errorText","User with this email already exists!");
        }
        else if (userService.findByPhone(user.getPhone())!=null){
            model.addAttribute("errorText","User with this phone number already exists!");
        }
        else if(!NumberUtils.isDigits(user.getPhone()) || user.getPhone().length()!=9){
            model.addAttribute("errorText","Phone number is incorrect!");
        }
        else if (userService.hasEmptyValues(user)){
            model.addAttribute("errorText","All fields must be filled!");
        }
        else {
            user.setPassword("{noop}" + user.getPassword());
            userService.save(user);
            return "redirect:/";
        }
        return "registerUser";
    }
    @GetMapping("/aboutUs")
    public String aboutUs(){
        return "aboutUs";
    }
    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }

    @GetMapping("/choiceService")
    public String choiceService(Model model, @RequestParam(name="category") String category, @RequestParam(name="categories") String[] categories){

        model.addAttribute("categories" , categories);
        List <ServiceCategory>  serviceCategory = serviceCategoryService.findByName(category);
        List<Company> companies = new ArrayList<>();
        Map<Company, List<Service>> servicesMap = new HashMap<>();
        for(ServiceCategory serviceCategory1 : serviceCategory){
            companies.add(serviceCategory1.getCompany());
        }
        for(Company company: companies){
            List<Service> servicesArray;
            servicesArray = company.getServicesCategories().get(0).getServices();
            servicesMap.put(company, servicesArray);
//            for(Service service: servicesArray){
//                servicesMap.put(company, service);
//            }
        }
        model.addAttribute("companies", companies);
        model.addAttribute("servicesMap", servicesMap);

        return "choiceService";
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
            System.out.println("OPEN TIME " + company.getOpenTime());
            System.out.println("CLOSE TIME " + company.getCloseTime());
            companyService.save(company,authentication.getName());
            System.out.println(authentication.getCredentials());
             ServiceCategory serviceCategory;// = serviceCategoryService.findByName(categoryName);
//            if(serviceCategory==null){
//                System.out.println("created serviceCategory");
                serviceCategory = new ServiceCategory();
                serviceCategory.setCategoryName(categoryName);
                serviceCategory.setCompany(company);
                company.addServiceCategory(serviceCategory);
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
    @GetMapping("/deleteCompany")
    public String deleteCompany(@RequestParam(name="id")int id){
        System.out.println("ID: -----"+id);
        companyService.deleteById(id);
        return "redirect:/";
    }
    @GetMapping("/editCompany")
    public String editCompany(){
        return "editCompany";
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

    @GetMapping("/addServicee")
    public String addServicee(Model model, @RequestParam(name="id") int id){
        model.addAttribute("service" , new Service());
        model.addAttribute("company_id",id);
      //  model.addAttribute("company_id", String.valueOf(id));
        System.out.println("addService method " + id);
        return "addService";
    }

    @PostMapping("/saveService")
    public String saveService(@ModelAttribute("service")Service service, @RequestParam("id") int company_id){

        if(service!=null)
            System.out.println("Service is not null");
        System.out.println("Company_id: " + company_id);
        if(!service.getName().equals("") && service.getDuration()!=0){
            Company company = companyService.findById(company_id);
            List<ServiceCategory> serviceCategories = company.getServicesCategories();
            service.setServicesCategories(serviceCategories.get(0));
            serviceCategories.get(0).addService(service);
            serviceService.save(service);
        }

        return "redirect:/myCompanies";
    }
}
