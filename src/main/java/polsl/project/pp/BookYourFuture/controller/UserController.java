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

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.security.Principal;
import java.sql.Time;
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

    private int categoryId = 0;

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
    public String index(Model model, Principal principal) {
//        categories=new String[]{"Haircut","Massage","Squash","Dentist","Mechanic","Engraver"};
        if (categories == null) {
            List<String> categoriesList = serviceCategoryService.findAllName();
            categories = categoriesList.toArray(new String[0]);
        }
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("/registerUser")
    public String registerUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("errorText", "");
        return "registerUser";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam(name = "password2") String password2, Model model) {
        if (!user.getPassword().equals(password2)) {
            model.addAttribute("errorText", "Passwords are not equal!");
        } else if (!user.getLogin().equals("") && userService.findByUsername(user.getLogin()) != null) {
            model.addAttribute("errorText", "User with this login already exists!");
        } else if (!user.getEmail().equals("") && userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("errorText", "User with this email already exists!");
        } else if (!user.getPhone().equals("") && userService.findByPhone(user.getPhone()) != null) {
            model.addAttribute("errorText", "User with this phone number already exists!");
        } else if (!NumberUtils.isDigits(user.getPhone()) || user.getPhone().length() != 9) {
            model.addAttribute("errorText", "Phone number is incorrect!");
        } else if (userService.hasEmptyValues(user)) {
            model.addAttribute("errorText", "All fields must be filled!");
        } else {
            user.setPassword("{noop}" + user.getPassword());
            userService.save(user);
            return "redirect:/";
        }
        return "registerUser";
    }

    @GetMapping("/aboutUs")
    public String aboutUs() {
        return "aboutUs";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/choiceService")
    public String choiceService(Model model, @RequestParam(name = "category") String category, @RequestParam(name = "categories") String[] categories) {

        model.addAttribute("categories", categories);
        List<ServiceCategory> serviceCategory = serviceCategoryService.findByName(category);
        List<Company> companies = new ArrayList<>();
        Map<Company, List<Service>> servicesMap = new HashMap<>();
        for (ServiceCategory serviceCategory1 : serviceCategory) {
            companies.add(serviceCategory1.getCompany());
        }
        for (Company company : companies) {
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
    public String bookService(Model model, @PathVariable(value = "service_id") int service_id) {
        model.addAttribute("service_id", service_id);
        model.addAttribute("error", false);
        model.addAttribute("categories", categories);
        return "bookService";
    }

    @GetMapping("/bookedServices")
    public String bookedServices(Model model, @RequestParam(name = "id") int id) {
        //System.out.println("edit"+id);
        model.addAttribute("company_id", id);
        Company company = companyService.findById(id);
        List<Service> services = companyService.getServices(company);
        model.addAttribute("company", company);
        List<Timetable> timetableList = new ArrayList<>();
        for(int i =0;i<services.size();i++)
        {
            List<Timetable> timetableBuffer = timetableService.findByService(services.get(i));
            for(int j=0;j<timetableBuffer.size();j++)
            {
                timetableList.add(timetableBuffer.get(j));
            }
        }

        for(int i=timetableList.size();i>0;i--)
        {
            for(int j =0;j<i-1;j++)
            {
                String dateA[] = timetableList.get(j).getDate().split("-");
                String startTimeA[] = timetableList.get(j).getStartTime().split(":");
                String dateB[] = timetableList.get(j+1).getDate().split("-");
                String startTimeB[] = timetableList.get(j+1).getStartTime().split(":");
                if(Integer.parseInt(dateA[0])>Integer.parseInt(dateB[0]))
                {
                    Timetable buffer = timetableList.set(j,timetableList.get(j+1));
                    timetableList.set(j+1,buffer);
                }
                else if(Integer.parseInt(dateA[0])==Integer.parseInt(dateB[0]))
                {
                    if(Integer.parseInt(dateA[1])>Integer.parseInt(dateB[1]))
                    {
                        Timetable buffer = timetableList.set(j,timetableList.get(j+1));
                        timetableList.set(j+1,buffer);
                    }
                    else if(Integer.parseInt(dateA[1])==Integer.parseInt(dateB[1]))
                    {
                        if(Integer.parseInt(dateA[2])>Integer.parseInt(dateB[2]))
                        {
                            Timetable buffer = timetableList.set(j,timetableList.get(j+1));
                            timetableList.set(j+1,buffer);
                        }
                        else if(Integer.parseInt(dateA[2])==Integer.parseInt(dateB[2]))
                        {
                            if(Integer.parseInt(startTimeA[0])>Integer.parseInt(startTimeB[0]))
                            {
                                Timetable buffer = timetableList.set(j,timetableList.get(j+1));
                                timetableList.set(j+1,buffer);
                            }
                            else if(Integer.parseInt(startTimeA[0])==Integer.parseInt(startTimeB[0]))
                            {
                                if(Integer.parseInt(startTimeA[1])>Integer.parseInt(startTimeB[1]))
                                {
                                    Timetable buffer = timetableList.set(j,timetableList.get(j+1));
                                    timetableList.set(j+1,buffer);
                                }
                            }
                        }
                    }
                }

            }
        }


        model.addAttribute("timetable", timetableList);
        model.addAttribute("errorText", "");
        return "bookedServices";
    }


    //helpful class for storing possible dates of a given service
    class ServiceTime {
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
    public String bookServiceAction(Model model, @RequestParam("datetime") String datetime, @PathVariable("service_id") int service_id) {

        model.addAttribute("categories", categories);
        if (datetime.equals("")) {
            model.addAttribute("error", true);
            return "bookService";
        }
        LocalDate selectedDate = LocalDate.parse(datetime);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        LocalDate actDate = LocalDate.parse(date);
        if (datetime.equals("") || selectedDate.isBefore(actDate)) {
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

        List<Timetable> timetableList = new ArrayList<>();
        ServiceCategory serviceCategory = service.getServicesCategories();
        //list of services which are assigned to a specific company
        List<Service> serviceList = serviceService.findByCatSerId(serviceCategory);

        //searching for busy hours for the services of a given company
        for (Service theService : serviceList) {
            timetableList.addAll(timetableService.findByServiceAndDate(datetime, theService));
            System.out.println(timetableList);
        }

        boolean isBusy = false;

        //filling the board with possible hours
        while (companyStart.plusMinutes(serviceDuration).isBefore(companyEnd) || companyStart.plusMinutes(serviceDuration).equals(companyEnd)) {
            LocalTime companyEndTmp = companyStart.plusMinutes(serviceDuration);
            if (timetableList.size() > 0) {
                for (Timetable timetable : timetableList) {
                    LocalTime startTime = LocalTime.parse(timetable.getStartTime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
                    LocalTime endTime = LocalTime.parse(timetable.getEndTime(), DateTimeFormatter.ofPattern("HH:mm:ss"));

                    if (((companyStart.isBefore(startTime) || companyStart.equals(startTime))
                            && (companyEndTmp.isAfter(startTime)))
                            || ((companyStart.isBefore(endTime) || companyEndTmp.equals(endTime))
                            && (companyEndTmp.isAfter(endTime)))
                            || (((startTime.isBefore(companyStart) || startTime.equals(companyStart))
                            && (endTime.isAfter(companyEndTmp) || endTime.equals(companyEndTmp))))) {
                        isBusy = true;
                        companyStart = endTime;
                        break;
                    }
                }
                if (!isBusy) {
                    serviceTimeList.add(new ServiceTime(companyStart, companyEndTmp));
                    companyStart = companyEndTmp;
                }
                isBusy = false;
            } else {
                serviceTimeList.add(new ServiceTime(companyStart, companyEndTmp));
                companyStart = companyEndTmp;
            }
        }

        model.addAttribute("serviceTimeList", serviceTimeList);
        model.addAttribute("service_id", service_id);
        model.addAttribute("datetime", datetime);
        model.addAttribute("error", false);
        return "bookService2";
    }

    @GetMapping("/saveDate")
    public String saveDate(@RequestParam("datetime") String datetime, @RequestParam("serviceTimeId") int serviceTimeId, @RequestParam("service_id") int service_id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().toString().equals("anonymousUser")) {
            System.out.println("You must sign when you want to book date");
            return "redirect:/login";
        } else {
            User user = userService.findByUsername(authentication.getName());
            Service service = serviceService.findById(service_id);
            ServiceTime serviceTime = serviceTimeList.get(serviceTimeId);
            Timetable timetable = new Timetable(serviceTime.getStartTime().toString(), serviceTime.getEndTime().toString(), datetime);
            timetable.setServices(service);
            timetable.setUser(user);
            timetableService.save(timetable);
        }
//        ScriptEngineManager manager = new ScriptEngineManager();
//        ScriptEngine engine = manager.getEngineByName("JavaScript");
//        try {
//            engine.eval("https://unpkg.com/sweetalert/dist/sweetalert.min.js");
//            engine.eval("function alert(){\n" +
//                    "    swal({\n" +
//                    "        title: \"Success\",\n" +
//                    "        text: \"You registered\",\n" +
//                    "        icon: \"success\",\n" +
//                    "        button: \"Ok!\",\n" +
//                    "    });\n" +
//                    "};\n" +
//                    "alert(this.alert);");
//        } catch (ScriptException e) {
//            e.printStackTrace();
//        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }


    @GetMapping("/addCompany")
    public String addCompany(Model model) {
        model.addAttribute("company", new Company());
        model.addAttribute("errorText", "");
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        this.categoryId = 0;
        model.addAttribute("categoryId", this.categoryId);
        return "addCompany";
    }

    @GetMapping("/myCompanies")
    public String myCompanies(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("services", serviceService.findAll());
        model.addAttribute("serviceCategory", serviceCategoryService.findAll());
        model.addAttribute("companiesList", companyService.findAllByUserId(user.getId()));
        return "myCompanies";
    }

    @PostMapping("/saveCompany")
    public String saveCompany(@ModelAttribute("company") Company company, @RequestParam("categoryId") int categoryId, Model model) {
        String msg = "";
        System.out.println("categoryId " + categoryId);
        if (categoryId >= 0) {
            this.categoryId = categoryId;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        if (companyService.hasEmptyValues(company)) {
            model.addAttribute("errorText", "All fields must be filled!");
        } else if (!NumberUtils.isDigits(company.getNip())) {
            model.addAttribute("errorText", "NIP must only be digits!");
        } else if (company.getNip().length() != 10) {
            model.addAttribute("errorText", "NIP must be 10 digits long!");
        } else if (companyService.findByNIP(company.getNip()) != null) {
            model.addAttribute("errorText", "Company with that NIP already exists!");
        } else if (!(msg = companyService.checkHours(company.getOpenTime(), company.getCloseTime())).equals("")) {
            model.addAttribute("errorText", msg);
        } else {
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
        model.addAttribute("company", company);
        model.addAttribute("categoryId", this.categoryId);
        return "addCompany";

    }


    @GetMapping("/deleteCompany")
    public String deleteCompany(@RequestParam(name = "id") int id) {
        System.out.println("ID: -----" + id);
        companyService.deleteById(id);
        return "redirect:/myCompanies";
    }

    @GetMapping("/deleteService")
    public String deleteService(@RequestParam(name = "id") int id) {
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
    public String editAccount(Model model) {

/*        User u = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentLogin = authentication.getName();
            u = userService.findByUsername(currentLogin);
        }*/
        model.addAttribute("errorText", "");
        model.addAttribute("user", new User());
        return "editAccount";
    }

    //@RequestMapping(method = RequestMethod.POST, value = "/updateUser")
    @PostMapping("/updateUser")
    //@ResponseBody
    //public String updateUser(@RequestBody User user, @PathVariable String id){
    public String updateUser(@ModelAttribute("user") User user, Model model, @RequestParam("currentPassword") String currPass, @RequestParam("repeatPassword") String repPass) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getCredentials().equals(currPass)) {
            model.addAttribute("errorText", "Incorrect password!");
        } else if (!user.getPassword().equals(repPass)) {
            model.addAttribute("errorText", "Passwords are not equal!");
        } else if (!user.getLogin().equals("") && userService.findByUsername(user.getLogin()) != null) {
            model.addAttribute("errorText", "User with this login already exists!");
        } else if (!user.getEmail().equals("") && userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("errorText", "User with this email already exists!");
        } else if (!user.getPhone().equals("") && userService.findByPhone(user.getPhone()) != null) {
            model.addAttribute("errorText", "User with this phone number already exists!");
        } else if (!StringUtils.isBlank(user.getPhone()) && user.getPhone().length() != 9) {
            model.addAttribute("errorText", "Phone number is incorrect!");
        } else {
            userService.updateUser(userService.findByUsername(authentication.getName()).getId(), user);
            if (!user.getLogin().equals(""))
                return "redirect:/logout";
            return "redirect:/";
        }
        model.addAttribute("user", user);
        return "editAccount";

    }

    @GetMapping("/editService")
    public String editService(Model model, @RequestParam(name = "id") int id) {
        model.addAttribute("service_id", id);
        model.addAttribute("errorText", "");
        return "editService";
    }

    @PostMapping("/updateService")
    public String updateService(@RequestParam(name = "id") int id,
                                @RequestParam(name = "name") String name,
                                @RequestParam(name = "duration") int duration,
                                @RequestParam(name = "password") String password, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getCredentials().equals(password)) {
            model.addAttribute("errorText", "Incorrect password!");
        } else {
            serviceService.updateService(id, name, duration);
            return "redirect:/myCompanies";
        }
        model.addAttribute("service_id", id);
        return "editService";
    }

    @GetMapping("/editCompany")
    public String editCompany(Model model, @RequestParam(name = "id") int id) {
        //System.out.println("edit"+id);
        model.addAttribute("company_id", id);
        model.addAttribute("company", new Company());
        model.addAttribute("errorText", "");
        return "editCompany";
    }

    @PostMapping("/updateCompany")
    public String updateCompany(@RequestParam(name = "id") int id, @ModelAttribute(name = "company") Company company, @RequestParam(name = "password") String password, Model model) {
        //System.out.println(id);
        String msg = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getCredentials().equals(password)) {
            model.addAttribute("errorText", "Incorrect password!");
        } else if (company.getNip().length() != 0 && companyService.findByNIP(company.getNip()) != null) {
            model.addAttribute("errorText", "This NIP is already registered!");
        } else if (company.getNip().length() != 0 && !NumberUtils.isDigits(company.getNip())) {
                model.addAttribute("errorText", "NIP must only be digits!");
            } else if (company.getNip().length() != 0 && company.getNip().length() != 10) {
                model.addAttribute("errorText", "NIP must be 10 digits long!");
            }
        else if (!company.getOpenTime().equals("") && !(msg = companyService.checkHours(company.getOpenTime())).equals("") ){
            model.addAttribute("errorText",msg);
        }
        else if (!company.getCloseTime().equals("") && !(msg = companyService.checkHours(company.getCloseTime())).equals("") ){
            model.addAttribute("errorText",msg);
        } else {
            companyService.updateCompany(id, company);
            return "redirect:/myCompanies";
        }
        model.addAttribute("company_id", id);
        model.addAttribute("company", company);
        return "editCompany";
    }

    @GetMapping("/addServicee")
    public String addServicee(Model model, @RequestParam(name = "id") int id) {
        Service service = new Service();
        service.setDuration(1);
        model.addAttribute("service", service);
        model.addAttribute("company_id", id);
        model.addAttribute("errorText", "");
        //  model.addAttribute("company_id", String.valueOf(id));
        System.out.println("addService method " + id);
        return "addService";
    }

    @PostMapping("/saveService")
    public String saveService(@ModelAttribute("service") Service service, @RequestParam("id") int company_id, Model model) {
        if (StringUtils.isBlank(service.getName())) {
            model.addAttribute("errorText", "Service name cannot be empty!");
        } else {
            Company company = companyService.findById(company_id);
            List<ServiceCategory> serviceCategories = company.getServicesCategories();
            service.setServicesCategories(serviceCategories.get(0));
            serviceCategories.get(0).addService(service);
            serviceService.save(service);
            return "redirect:/myCompanies";
        }
        model.addAttribute("service", service);
        model.addAttribute("company_id", company_id);
        return "addService";

    }

    @GetMapping("/myReservations")
    public String myReservations(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());

        List<Timetable> timetableList = timetableService.getByUser(user);
        Collections.sort(timetableList);

        model.addAttribute("timeTableList", timetableList);

        return "myReservations";
    }

    @GetMapping("deleteReservation")
    public String deleteReservations(@RequestParam("id") int timeTableId){
        System.out.println("timetable id" + timeTableId);
        timetableService.deleteById(timeTableId);

        return "redirect:/myReservations";
    }
}
