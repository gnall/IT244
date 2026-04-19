package com.example.apis.transform;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transform")
public class TransformController {

  public record TransformRequest(@NotBlank String input) {}

  @PostMapping("/uppercase")
  public Map<String, String> uppercase(@Valid @RequestBody TransformRequest req) {
    return Map.of("input", req.input(), "output", req.input().toUpperCase());
  }

  // TODO: POST /transform/reverse
  // TODO: POST /transform/trim
  // TODO: POST /transform/stats  (return length + wordCount)
}

