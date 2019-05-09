package polsl.project.pp.BookYourFuture;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestClass {
    @RequestMapping("/")
    public String index(){
        return "Hello world! XD";
    }
}
