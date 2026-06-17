package careerai_backend.controller;

import careerai_backend.entity.ResumeHistory;
import careerai_backend.repository.ResumeHistoryRepository;
import careerai_backend.service.JobRecommendationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import careerai_backend.entity.User;
import careerai_backend.repository.UserRepository;
import careerai_backend.service.JwtService;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobRecommendationController {

    @Autowired
    private JobRecommendationService jobService;

    @Autowired
    private ResumeHistoryRepository resumeHistoryRepository;

    @Autowired
private UserRepository userRepository;

@Autowired
private JwtService jwtService;

@GetMapping("/recommend")
public String recommendJobs(
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
                    No Resume Found.

                    Please upload a resume first.
                    """;
        }

        return jobService.generateRecommendations(
                latestResume.getResumeText()
        );
    }
}