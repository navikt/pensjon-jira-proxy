package nav.pen.jiraproxy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.Properties

@SpringBootApplication
class JiraProxyApplication

fun main(args: Array<String>) {
    runApplication<JiraProxyApplication>(*args)
}
