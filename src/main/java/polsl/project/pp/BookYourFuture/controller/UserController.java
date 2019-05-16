package polsl.project.pp.BookYourFuture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import polsl.project.pp.BookYourFuture.entities.User;
import polsl.project.pp.BookYourFuture.services.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService theUserService){
        userService = theUserService;
    }

    @GetMapping("/")
    public String index(Model model, Principal principal){
       // model.addAttribute("loggedInUser",principal);
        return "index";
    }
    @GetMapping("/registerUser")
    public String registerUser(Model model){
        model.addAttribute("user",new User());
        return "registerUser";
    }
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user")User user){
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
}
