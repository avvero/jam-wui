package pw.avvero.jamwui;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
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

import java.util.LinkedList;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Controller
@AllArgsConstructor
public class RestController {

    private final JamService jamService;
    private final CacheManager cacheManager;
    private final LinkedList<String> history = new LinkedList<>();

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("history", history);
        return "index";
    }

    @GetMapping("/checkout")
    public String checkout(@RequestParam String issueCode, Model model) {
        String schema = jamService.checkout(issueCode);
        model.addAttribute("schema", schema);
        return "schema";
    }

    @GetMapping("/dependencies")
    public String dependencies(@RequestParam(name = "issueCode") String rowIssueCode,
                               @RequestParam(required = false) String from,
                               Model model) {
        String issueCode = extractCode(rowIssueCode);
        if (!hasText(issueCode)) {
            model.addAttribute("error", "Can't find issue by code: empty");
            model.addAttribute("history", history);
            return "index";
        }
        Cache cache = cacheManager.getCache("dependencies");
        String issueDependencies = cache.get(issueCode, String.class);
        if (issueDependencies == null) {
            Issue issue;
            try {
                issue = jamService.getIssueWithChildren(issueCode);
            } catch (Throwable t) {
                log.error(t.getLocalizedMessage(), t);
                model.addAttribute("message", t.getCause().getLocalizedMessage());
                model.addAttribute("stackTrace", ExceptionUtils.getStackTrace(t));
                return "error";
            }
            if (issue == null) {
                model.addAttribute("error", "Can't find issue by code: " + issueCode);
                model.addAttribute("history", history);
                return "index";
            }
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
            issueDependencies = GraphvizWriter.toString(issue, from);
            cache.put(issueCode, issueDependencies);
            history.push(issueCode);
        }
        model.addAttribute("dependencyGraph", issueDependencies);
        return "dependencies";
    }

    /**
     * In case is not issue code is passed:
     * - on null return null
     * - on full url to issue return code
     * @param rowIssueCode
     * @return
     */
    private String extractCode(String rowIssueCode) {
        if (rowIssueCode == null) return null;
        String[] parts = rowIssueCode.trim().split("/");
        return parts[parts.length-1];
    }
}
