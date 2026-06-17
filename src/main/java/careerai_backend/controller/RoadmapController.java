package careerai_backend.controller;

import careerai_backend.entity.ResumeHistory;
import careerai_backend.repository.ResumeHistoryRepository;
import careerai_backend.service.ResumeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import careerai_backend.entity.User;
import careerai_backend.repository.UserRepository;
import careerai_backend.service.JwtService;

@RestController
@RequestMapping("/api/roadmap")
@CrossOrigin(origins = "http://localhost:5173")
public class RoadmapController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ResumeHistoryRepository resumeHistoryRepository;

    @Autowired
private UserRepository userRepository;

@Autowired
private JwtService jwtService;

@GetMapping
public String getRoadmap(
        @RequestHeader("Authorization") String token
) {

    token = token.replace("Bearer ", "");

    String email = jwtService.extractEmail(token);

    User user = userRepository.findByEmail(email);

    ResumeHistory latestResume =
            resumeHistoryRepository
                    .findTopByUserOrderByUploadDateDesc(user)
                    .orElse(null);

        if (latestResume == null) {

            return """
Welcome to CareerAI!

Upload your resume to receive:
• Personalized Career Roadmap
• ATS Analysis
• Job Recommendations
• AI Interview Questions

Your roadmap will be generated based on your skills and career goals.
                    """;
        }

        return resumeService.generateRoadmap(
                latestResume.getResumeText()
        );
    }
}