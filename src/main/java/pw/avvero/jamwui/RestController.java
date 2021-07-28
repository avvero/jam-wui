package pw.avvero.jamwui;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pw.avvero.jam.JamService;

@Controller
@AllArgsConstructor
public class RestController {

    private JamService jamService;

    @GetMapping("/checkout")
    public String checkout(@RequestParam String issueCode, Model model) {
        String schema = jamService.checkout(issueCode);
        model.addAttribute("schema", schema);
        return "schema";
    }

    @GetMapping("/dependencies")
    public String dependencies(@RequestParam String issueCode, Model model) {
        String dependencyGraph = jamService.getDependenciesForIssueWithChildren(issueCode);
        model.addAttribute("dependencyGraph", dependencyGraph);
        return "dependencies";
    }
}
