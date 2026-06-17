package careerai_backend.controller;

import careerai_backend.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class TestController {

    @Autowired
    private AIService aiService;

    @GetMapping
    public String testAI() {

        return aiService.askAI(
                "Give me 3 Java interview questions."
        );
    }
}