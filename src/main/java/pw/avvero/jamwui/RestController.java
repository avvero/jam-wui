package pw.avvero.jamwui;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pw.avvero.jam.JamService;
import pw.avvero.jam.core.Issue;
import pw.avvero.jam.core.IssueLink;
import pw.avvero.jam.graphviz.GraphvizWriter;

@Slf4j
@Controller
@AllArgsConstructor
public class RestController {

    private final JamService jamService;
    private final CacheManager cacheManager;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/checkout")
    public String checkout(@RequestParam String issueCode, Model model) {
        String schema = jamService.checkout(issueCode);
        model.addAttribute("schema", schema);
        return "schema";
    }

    @GetMapping("/dependencies")
    public String dependencies(@RequestParam String issueCode, Model model) {
        Cache cache = cacheManager.getCache("dependencies");
        String issueDependencies = cache.get(issueCode, String.class);
        if (issueDependencies == null) {
            Issue issue = jamService.getIssueWithChildren(issueCode);
            // TODO custom jira structure specifique
            if ("Initiative".equalsIgnoreCase(issue.getType()) && issue.getLinks() != null) {
                for (IssueLink link : issue.getLinks()) {
                    if ("is parent task of".equals(link.getType())) {
                        log.debug("Take details from linked issue: {} {}", link.getType(), link.getIssue().getKey());
                        Issue enriched = jamService.getIssueWithChildren(link.getIssue().getKey());
                        issue.getChildren().add(enriched);
                    }
                }
            }
            issueDependencies = GraphvizWriter.toString(issue);
            cache.put(issueCode, issueDependencies);
        }
        model.addAttribute("dependencyGraph", issueDependencies);
        return "dependencies";
    }
}
