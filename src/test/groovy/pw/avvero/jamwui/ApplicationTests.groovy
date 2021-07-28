package pw.avvero.jamwui


import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles(profiles = "test")
class ApplicationTests extends Specification {

    def "Context loads"() {
        expect:
        1 == 1
    }

}
