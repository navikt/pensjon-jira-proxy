package nav.pen.jiraproxy

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/isalive")
class IsAlive {

    @GetMapping
    fun isAlive() = "I'm alive!"
}