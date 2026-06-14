package careerai_backend.controller;

import careerai_backend.entity.ResumeHistory;
import careerai_backend.repository.ResumeHistoryRepository;
import careerai_backend.service.AIService;
import careerai_backend.service.ResumeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins = "http://localhost:5173")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private AIService aiService;

    @Autowired
    private ResumeHistoryRepository resumeHistoryRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResume(
            @RequestParam("file") MultipartFile file) {

        try {

            String resumeText =
                    resumeService.extractText(file);

            String atsReport =
                    resumeService.generateATSReport(resumeText);

            String roadmap =
                    resumeService.generateRoadmap(resumeText);

            int atsScore =
                    resumeService.calculateATSScore(resumeText);
                ResumeHistory history =
                        new ResumeHistory();

                history.setAtsScore(atsScore);
                history.setResumeText(resumeText);
                history.setUploadDate(LocalDateTime.now());
            resumeHistoryRepository.save(history);

          String aiFeedback =
        aiService.askAI(
                
                """
                Analyze this software developer resume.

                Provide:

                1. Strengths
                2. Weaknesses
                3. Missing Skills
                4. ATS Improvements
                5. Interview Readiness Score

                Resume:

                %s
                """
                
                .formatted(
                resumeText.length() > 1000
                        ? resumeText.substring(0, 1000)
                        : resumeText
                )
                
        );

            return ResponseEntity.ok(

                    atsReport

                    + "\n\n====================\n\n"

                    + roadmap

                    + "\n\n====================\n"
                    
                    + "AI RESUME ANALYSIS\n"

                    + "====================\n\n"

                    + aiFeedback

            );

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .badRequest()
                    .body("Error reading PDF");

        }
    }

    @GetMapping("/history")
    public List<ResumeHistory> getHistory() {

        return resumeHistoryRepository
                .findAllByOrderByUploadDateDesc();

    }
    @PostMapping("/extract")
public ResponseEntity<String> extractResume(
        @RequestParam("file")
        MultipartFile file
) {

    try {

        String resumeText =
                resumeService.extractText(file);
        System.out.println("========== RESUME TEXT ==========");
System.out.println(resumeText);
System.out.println("=================================");

        return ResponseEntity.ok(
                resumeText
        );

    } catch (Exception e) {

        e.printStackTrace();

        return ResponseEntity
                .badRequest()
                .body(
                    "Failed to extract resume"
                );
    }
}
}