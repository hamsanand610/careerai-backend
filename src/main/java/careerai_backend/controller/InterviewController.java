package careerai_backend.controller;

import careerai_backend.entity.ResumeHistory;
import careerai_backend.repository.ResumeHistoryRepository;
import careerai_backend.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import careerai_backend.dto.InterviewAnswerRequest;
import careerai_backend.entity.User;
import careerai_backend.repository.UserRepository;
import careerai_backend.service.JwtService;

@RestController
@RequestMapping("/api/interview")
@CrossOrigin(origins = "http://localhost:5173")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private ResumeHistoryRepository resumeHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

@GetMapping("/generate")
public String generateQuestions(
        @RequestHeader("Authorization") String token,
        @RequestParam String role,
        @RequestParam String difficulty
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
                No resume found.

                Please upload a resume first.
                """;
    }

return interviewService
        .generateQuestionsFromResume(
                role,
                difficulty,
                latestResume.getResumeText()
        );
}
@PostMapping("/evaluate")
public String evaluateAnswer(
        @RequestBody InterviewAnswerRequest request
) {

    return interviewService.evaluateAnswer(
            request.getQuestion(),
            request.getAnswer()
    );
}
    }
