package careerai_backend.controller;

import careerai_backend.entity.ResumeHistory;
import careerai_backend.repository.ResumeHistoryRepository;
import careerai_backend.service.JobRecommendationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "http://localhost:5173")
public class JobRecommendationController {

    @Autowired
    private JobRecommendationService jobService;

    @Autowired
    private ResumeHistoryRepository resumeHistoryRepository;

    @GetMapping("/recommend")
    public String recommendJobs() {

        ResumeHistory latestResume =
                resumeHistoryRepository
                        .findTopByOrderByUploadDateDesc()
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