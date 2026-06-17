package careerai_backend.controller;

import careerai_backend.dto.JobMatchRequest;
import careerai_backend.service.JobMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job-match")
@CrossOrigin(origins = "*")
public class JobMatchController {

    @Autowired
    private JobMatchService jobMatchService;

    @PostMapping
    public String matchJob(
            @RequestBody JobMatchRequest request
    ) {

        return jobMatchService.matchJob(
        request.getResumeText(),
        request.getJobDescription()
);
    }
}