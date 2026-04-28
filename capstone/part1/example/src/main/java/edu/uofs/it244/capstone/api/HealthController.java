package edu.uofs.it244.capstone.api;

import java.time.Instant;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {

    // Intentional lint issue:
    // Checkstyle config requires Javadoc on public methods. This public method has none.
    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "ok",
                "timestamp", Instant.now().toString(),
                "note", "This line is intentionally very long so a typical Java linter can flag it."
        );
    }
}

