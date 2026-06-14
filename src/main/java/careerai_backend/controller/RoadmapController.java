package careerai_backend.controller;

import careerai_backend.entity.ResumeHistory;
import careerai_backend.repository.ResumeHistoryRepository;
import careerai_backend.service.ResumeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roadmap")
@CrossOrigin(origins = "http://localhost:5173")
public class RoadmapController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ResumeHistoryRepository resumeHistoryRepository;

    @GetMapping
    public String getRoadmap() {

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

        return resumeService.generateRoadmap(
                latestResume.getResumeText()
        );
    }
}