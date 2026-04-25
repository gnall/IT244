package edu.uofs.it244.capstone.api;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EchoController {

    @GetMapping("/echo")
    public Map<String, Object> echo(@RequestParam(defaultValue = "hello") String message) {
        return Map.of(
                "message", message
        );
    }
}

