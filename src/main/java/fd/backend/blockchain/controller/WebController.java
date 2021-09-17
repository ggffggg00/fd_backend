package fd.backend.blockchain.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class WebController {


    @GetMapping("*")
    public String getAdminPage(){
        return "admin";
    }

}
