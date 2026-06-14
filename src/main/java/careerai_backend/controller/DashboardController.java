package careerai_backend.controller;

import careerai_backend.repository.ResumeHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private ResumeHistoryRepository resumeHistoryRepository;

@GetMapping("/stats")
public Map<String, Object> getStats() {

    Map<String, Object> stats = new HashMap<>();

    long totalResumes =
            resumeHistoryRepository.count();

    stats.put("totalResumes", totalResumes);

    stats.put("aiReviews", totalResumes);

    Integer latestScore = 0;

    if (resumeHistoryRepository
            .findTopByOrderByUploadDateDesc()
            .isPresent()) {

        latestScore =
                resumeHistoryRepository
                        .findTopByOrderByUploadDateDesc()
                        .get()
                        .getAtsScore();
    }

    stats.put("latestScore", latestScore);

    Integer bestScore =
            resumeHistoryRepository.findBestScore();

    Double averageScore =
            resumeHistoryRepository.findAverageScore();

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