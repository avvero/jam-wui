package pw.avvero.jamwui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pw.avvero.jam.JamService;
import pw.avvero.jam.core.IssueDataProvider;
import pw.avvero.jam.jira.HttpApiClient;
import pw.avvero.jam.jira.JiraApiDataProvider;

@Configuration
public class JamConfig {

    @Bean
    public JamService jamService(IssueDataProvider issueDataProvider) {
        return new JamService(issueDataProvider);
    }

    @Bean
    IssueDataProvider issueDataProvider(@Value("${app.jira.host}") String host,
                                        @Value("${app.jira.username}") String username,
                                        @Value("${app.jira.password}") String password) {
        return new JiraApiDataProvider(new HttpApiClient(host, username, password));
    }

}
