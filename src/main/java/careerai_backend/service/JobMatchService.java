package careerai_backend.service;

import careerai_backend.entity.ResumeHistory;
import careerai_backend.repository.ResumeHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobMatchService {

    @Autowired
    private AIService aiService;

    public String matchJob(
        String resumeText,
        String jobDescription
) {

    if (resumeText.length() > 1500) {

    resumeText =
            resumeText.substring(
                    0,
                    1500
            );
}
        /*
         * Limit Resume Size
         */
        if (resumeText.length() > 1500) {

            resumeText =
                    resumeText.substring(
                            0,
                            1500
                    );
        }

        /*
         * Limit Job Description Size
         */
        if (jobDescription.length() > 1500) {

            jobDescription =
                    jobDescription.substring(
                            0,
                            1500
                    );
        }

        String prompt = """
                Compare this resume with the job description.

                Resume:
                %s

                Job Description:
                %s

                Give:

                Match Score (0-100)

                Matching Skills

                Missing Skills

                Improvement Suggestions

                Hiring Recommendation

                Keep response short and structured.
                """
                .formatted(
                        resumeText,
                        jobDescription
                );

        return aiService.askAI(prompt);
    }
}