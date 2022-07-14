package org.coblt.twochat.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerTest {

    @GetMapping("/")
    public String test() {
        return "This page appears as the default path has been input.d";
    }
}
