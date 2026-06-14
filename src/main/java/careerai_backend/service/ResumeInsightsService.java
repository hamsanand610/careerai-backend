package careerai_backend.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResumeInsightsService {

    private final String[] skills = {
            "java",
            "spring boot",
            "react",
            "mysql",
            "git",
            "github",
            "rest api",
            "html",
            "css",
            "javascript",
            "jwt",
            "docker",
            "aws",
            "kubernetes"
    };

    public List<String> getFoundSkills(
            String resumeText) {

        List<String> foundSkills =
                new ArrayList<>();

        String lower =
                resumeText.toLowerCase();

        for (String skill : skills) {

            if (lower.contains(skill)) {

                foundSkills.add(skill);
            }
        }

        return foundSkills;
    }

    public List<String> getMissingSkills(
            String resumeText) {

        List<String> missingSkills =
                new ArrayList<>();

        String lower =
                resumeText.toLowerCase();

        for (String skill : skills) {

            if (!lower.contains(skill)) {

                missingSkills.add(skill);
            }
        }

        return missingSkills;
    }
}