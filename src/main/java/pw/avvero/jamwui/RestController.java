package pw.avvero.jamwui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestController {

    @GetMapping("/index")
    public String greeting(Model model) {
        return "index";
    }
}
