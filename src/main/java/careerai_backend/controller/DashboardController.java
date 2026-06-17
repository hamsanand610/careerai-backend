package careerai_backend.controller;

import careerai_backend.entity.User;
import careerai_backend.repository.ResumeHistoryRepository;
import careerai_backend.repository.UserRepository;
import careerai_backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private ResumeHistoryRepository resumeHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/stats")
    public Map<String, Object> getStats(
            @RequestHeader("Authorization") String token
    ) {

        token = token.replace("Bearer ", "");

        String email = jwtService.extractEmail(token);

        User user = userRepository.findByEmail(email);

        Map<String, Object> stats = new HashMap<>();

        long totalResumes =
                resumeHistoryRepository.countByUser(user);

        stats.put("totalResumes", totalResumes);

        stats.put("aiReviews", totalResumes);

        Integer latestScore = 0;

        var latestResume =
                resumeHistoryRepository
                        .findTopByUserOrderByUploadDateDesc(user);

        if (latestResume.isPresent()) {
            latestScore =
                    latestResume.get().getAtsScore();
        }

        stats.put("latestScore", latestScore);

        Integer bestScore =
                resumeHistoryRepository
                        .findBestScoreByUser(user);

        Double averageScore =
                resumeHistoryRepository
                        .findAverageScoreByUser(user);

        stats.put(
                "bestScore",
                bestScore == null ? 0 : bestScore
        );

        stats.put(
                "averageScore",
                averageScore == null
                        ? 0
                        : Math.round(averageScore)
        );

        return stats;
    }
}