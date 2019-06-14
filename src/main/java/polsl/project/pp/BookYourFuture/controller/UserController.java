package polsl.project.pp.BookYourFuture.controller;

import org.apache.commons.lang3.StringUtils;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Controller
public class UserController {

    private UserService userService;

    private CompanyService companyService;

    private ServiceCategoryService serviceCategoryService;

    private ServiceService serviceService;

    private TimetableService timetableService;

    private CategoryService categoryService;

    private List<ServiceTime> serviceTimeList;

    private String[] categories;

    @Autowired
    public UserController(UserService userService, CompanyService companyService, ServiceCategoryService serviceCategoryService, ServiceService serviceService, TimetableService timetableService, CategoryService categoryService) {
        this.userService = userService;
        this.companyService = companyService;
        this.serviceCategoryService = serviceCategoryService;
        this.serviceService = serviceService;
        this.timetableService = timetableService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String index(Model model, Principal principal){
//        categories=new String[]{"Haircut","Massage","Squash","Dentist","Mechanic","Engraver"};
        if(categories==null){
            List<String> categoriesList = serviceCategoryService.findAllName();
            categories = categoriesList.toArray(new String[0]);
        }
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
           // servicesArray = company.getServicesCategories().get(0).getServices();
            servicesArray = companyService.getServices(company);
            servicesMap.put(company, servicesArray);
//            for(Service service: servicesArray){
//                servicesMap.put(company, service);
//            }
        }
        model.addAttribute("companies", companies);
        model.addAttribute("servicesMap", servicesMap);

        return "choiceService";
    }

    @GetMapping("/bookService/{service_id}")
    public String bookService(Model model, @PathVariable(value="service_id") int service_id){
        model.addAttribute("service_id", service_id);
        model.addAttribute("error", false);
        model.addAttribute("categories", categories);
        return "bookService";
    }

    //helpful class for storing possible dates of a given service
    class ServiceTime{
        LocalTime startTime;
        LocalTime endTime;

        public ServiceTime(LocalTime startTime, LocalTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalTime startTime) {
            this.startTime = startTime;
        }

        public LocalTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalTime endTime) {
            this.endTime = endTime;
        }

        @Override
        public String toString() {
            return "ServiceTime{" +
                    "startTime=" + startTime +
                    ", endTime=" + endTime +
                    '}';
        }
    }

    @GetMapping("/bookServiceAction/{service_id}")
    public String bookServiceAction(Model model, @RequestParam("datetime") String datetime, @PathVariable("service_id") int service_id){

        model.addAttribute("categories", categories);
        if(datetime.equals("")){
            model.addAttribute("error", true);
            return "bookService";
        }
        LocalDate selectedDate = LocalDate.parse(datetime);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        LocalDate actDate = LocalDate.parse(date);
        if(datetime.equals("") || selectedDate.isBefore(actDate)){
            System.out.println("You must choose a future date");
            model.addAttribute("error", true);
            return "bookService";
        }
        Service service = serviceService.findById(service_id);
        Company company = service.getServicesCategories().getCompany();
        //getting start and end open time for the specific company
        LocalTime companyStart = LocalTime.parse(company.getOpenTime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime companyEnd = LocalTime.parse(company.getCloseTime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
        serviceTimeList = new ArrayList<>();
        int serviceDuration = service.getDuration();

        //filling the board with possible hours
        while(companyStart.isBefore(companyEnd)){
            LocalTime companyEndTmp = companyStart.plusMinutes(serviceDuration);
            serviceTimeList.add(new ServiceTime(companyStart, companyEndTmp));
            companyStart = companyEndTmp;
        }

        List<Timetable> timetableList = new ArrayList<>();
        ServiceCategory serviceCategory = service.getServicesCategories();
        //list of services which are assigned to a specific company
        List<Service> serviceList = serviceService.findByCatSerId(serviceCategory);

        //searching for busy hours for the services of a given company
        for(Service theService :serviceList){
            timetableList.addAll(timetableService.findByServiceAndDate(datetime, theService));
            System.out.println(timetableList);
        }

        if(timetableList.size()>0){
            for(Timetable timetable :timetableList){

                //start and end time of service which is booked
                LocalTime startTime = LocalTime.parse(timetable.getStartTime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
                LocalTime endTime = LocalTime.parse(timetable.getEndTime(), DateTimeFormatter.ofPattern("HH:mm:ss"));

                //
                //iterating through the list of available hours for a specific service
                Iterator<ServiceTime> serviceTimeIterator = serviceTimeList.iterator();
                while(serviceTimeIterator.hasNext()){
                    ServiceTime serviceTime = serviceTimeIterator.next();
                    if(((serviceTime.getStartTime().isBefore(startTime) || serviceTime.getStartTime().equals(startTime))
                            && (serviceTime.getEndTime().isAfter(startTime)))
                    || ((serviceTime.getStartTime().isBefore(endTime) || serviceTime.getEndTime().equals(endTime))
                            && (serviceTime.getEndTime().isAfter(endTime)))
                            || ((startTime.isBefore(serviceTime.getStartTime()) && endTime.isAfter(serviceTime.getEndTime())))){

                            //deletion of the term when it coincides with the date of the reserved service
                            serviceTimeIterator.remove();
                            System.out.println("ServiceTime removed! " + serviceTime);
                    }
                }
            }
        }else{
            System.out.println("Timetable is empty in that date");
        }
        model.addAttribute("serviceTimeList", serviceTimeList);
        model.addAttribute("service_id", service_id);
        model.addAttribute("datetime", datetime);
        model.addAttribute("error", false);
        return "bookService2";
    }

    @GetMapping("/saveDate")
    public String saveDate(@RequestParam("datetime") String datetime, @RequestParam("serviceTimeId") int serviceTimeId, @RequestParam("service_id") int service_id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal().toString().equals("anonymousUser")){
            System.out.println("You must sign when you want to book date");
            return "redirect:/login";
        }else{
            User user=userService.findByUsername(authentication.getName());
            Service service = serviceService.findById(service_id);
            ServiceTime serviceTime = serviceTimeList.get(serviceTimeId);
            Timetable timetable = new Timetable(serviceTime.getStartTime().toString(), serviceTime.getEndTime().toString(), datetime);
            timetable.setServices(service);
            timetable.setUser(user);
            timetableService.save(timetable);
        }
        return "redirect:/";
    }


    @GetMapping("/addCompany")
    public String addCompany(Model model){
        model.addAttribute("company" , new Company());
        model.addAttribute("errorText","");
        //int categoryId;
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        //model.addAttribute("categoryId", categoryId);
        return "addCompany";
    }
    @GetMapping("/myCompanies")
    public String myCompanies(Model model){
        // model.addAttribute("serviceCategory", new ServiceCategory());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByUsername(authentication.getName());
        model.addAttribute("services",serviceService.findAll());
        model.addAttribute("serviceCategory",serviceCategoryService.findAll());
        model.addAttribute("companiesList" , companyService.findAllByUserId(user.getId()));
        return "myCompanies";
    }

    @PostMapping("/saveCompany")
    public String saveCompany(@ModelAttribute("company")Company company,@RequestParam("categoryId") int categoryId,Model model){
        String msg="";
        System.out.println("categoryId " + categoryId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        if(companyService.hasEmptyValues(company)){
            model.addAttribute("errorText","All fields must be filled!");
        }
        else if(!NumberUtils.isDigits(company.getNip())){
            model.addAttribute("errorText","NIP must only be digits!");
        }else if(company.getNip().length()!=10){
            model.addAttribute("errorText","NIP must be 10 digits long!");
        }
        else if(companyService.findByNIP(company.getNip())!=null){
            model.addAttribute("errorText","Company with that NIP already exists!");
        }
        else if(!(msg = companyService.checkHours(company.getOpenTime(), company.getCloseTime())).equals("")){
            model.addAttribute("errorText",msg);
        }
        else {
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                companyService.save(company, authentication.getName());
                ServiceCategory serviceCategory;
                Category category = new Category(categoryService.findById(categoryId).getCategoryName());
                serviceCategory = new ServiceCategory();
                serviceCategory.setCategoryName(category.getCategoryName());
                serviceCategory.setCompany(company);
                company.addServiceCategory(serviceCategory);
                serviceCategoryService.save(serviceCategory);
            }
            List<String> categoriesList = serviceCategoryService.findAllName();
            categories = categoriesList.toArray(new String[0]);
            return "redirect:/myCompanies";
        }
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("company" , company);
        return "addCompany";

    }


    @GetMapping("/deleteCompany")
    public String deleteCompany(@RequestParam(name="id")int id){
        System.out.println("ID: -----"+id);
        companyService.deleteById(id);
        return "redirect:/myCompanies";
    }
    @GetMapping("/deleteService")
    public String deleteService(@RequestParam(name="id") int id){
        serviceService.deleteById(id);
        return "redirect:/myCompanies";
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

 @GetMapping("/editAccount")
 public String editAccount(Model model){

/*        User u = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentLogin = authentication.getName();
            u = userService.findByUsername(currentLogin);
        }*/
     model.addAttribute("errorText","");
     model.addAttribute("user", new User());
     return "editAccount";
 }
 //@RequestMapping(method = RequestMethod.POST, value = "/updateUser")
 @PostMapping("/updateUser")
 //@ResponseBody
    //public String updateUser(@RequestBody User user, @PathVariable String id){
     public String updateUser(@ModelAttribute("user")User user,Model model, @RequestParam("currentPassword")String currPass,@RequestParam("repeatPassword")String repPass){
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     if (!authentication.getCredentials().equals(currPass)) {
         model.addAttribute("errorText", "Incorrect password!");
     }
     else if (!user.getPassword().equals(repPass)) {
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
     else if(!StringUtils.isBlank(user.getPhone()) && user.getPhone().length()!=9){
         model.addAttribute("errorText","Phone number is incorrect!");
     }
     else{
            userService.updateUser(userService.findByUsername(authentication.getName()).getId(),user);
            if (!user.getLogin().equals(""))
                return "redirect:/logout";
            return "redirect:/";
     }
     model.addAttribute("user", user);
    return "editAccount";

 }
    @GetMapping("/editService")
    public String editService(Model model, @RequestParam(name="id")int id){
     model.addAttribute("service_id",id);
     model.addAttribute("errorText","");
        return "editService";
    }
    @PostMapping("/updateService")
    public String updateService(@RequestParam(name="id") int id,
                                @RequestParam(name="name")String name,
                                @RequestParam(name="duration")int duration,
                                @RequestParam(name="password")String password,Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getCredentials().equals(password)) {
            model.addAttribute("errorText", "Incorrect password!");
        } else if (duration < 0){
            model.addAttribute("errorText", "Duration must be a positive number!");
        }else{
            serviceService.updateService(id,name,duration);
            return "redirect:/myCompanies";
        }
        model.addAttribute("service_id",id);
        return "editService";
    }

    @GetMapping("/editCompany")
    public String editCompany(Model model,@RequestParam(name="id")int id){
        //System.out.println("edit"+id);
        model.addAttribute("company_id",id);
        model.addAttribute("company",new Company());
        model.addAttribute("errorText","");
        return "editCompany";
    }
@PostMapping("/updateCompany")
public String updateCompany( @RequestParam(name="id") int id,@ModelAttribute(name="company")Company company,@RequestParam(name="password")String password, Model model){
    //System.out.println(id);
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     if (!authentication.getCredentials().equals(password)){
        model.addAttribute("errorText","Incorrect password!");
     }else if(companyService.findByNIP(company.getNip())!=null){
         model.addAttribute("errorText","This NIP is already registered!");
    }
     else{
        companyService.updateCompany(id,company);
        return "redirect:/myCompanies";
     }
     model.addAttribute("company_id", id);
     model.addAttribute("company", company);
     return "editCompany";
}
    @GetMapping("/addServicee")
    public String addServicee(Model model, @RequestParam(name="id") int id){
        model.addAttribute("service" , new Service());
        model.addAttribute("company_id",id);
        model.addAttribute("errorText","");
      //  model.addAttribute("company_id", String.valueOf(id));
        System.out.println("addService method " + id);
        return "addService";
    }

    @PostMapping("/saveService")
    public String saveService(@ModelAttribute("service")Service service, @RequestParam("id") int company_id,Model model){
        if (StringUtils.isBlank(service.getName())){
            model.addAttribute("errorText","Service name cannot be empty!");
        }
        else if (service.getDuration()<=0){
            model.addAttribute("errorText", "Duration must be a positive number!");
        }
        else{
            Company company = companyService.findById(company_id);
            List<ServiceCategory> serviceCategories = company.getServicesCategories();
            service.setServicesCategories(serviceCategories.get(0));
            serviceCategories.get(0).addService(service);
            serviceService.save(service);
            return "redirect:/myCompanies";
        }
        model.addAttribute("service" , service);
        model.addAttribute("company_id",company_id);
        return "addService";

    }
}
