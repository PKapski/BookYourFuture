package polsl.project.pp.BookYourFuture.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {
    @GetMapping("/")
    public String indexx(Model model, Principal principal){
        //model.addAttribute("loggedInUser",principal.getName());
        return "index";
    }
}
