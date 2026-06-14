package careerai_backend.service;

import org.springframework.stereotype.Service;

@Service
public class JobRecommendationService {

    public String generateRecommendations(
            String resumeText) {

        String lower =
                resumeText.toLowerCase();

        StringBuilder jobs =
                new StringBuilder();

        jobs.append(
                "💼 CAREERAI JOB RECOMMENDATIONS\n\n"
        );

        /*
         * JAVA DEVELOPER
         */

        if (lower.contains("java")) {

            jobs.append("""
                    ================================

                    ROLE:
                    Java Developer

                    MATCH SCORE:
                    92%

                    SALARY:
                    ₹5 - ₹12 LPA

                    MISSING SKILLS:
                    Docker
                    AWS

                    LEARNING RESOURCES:
                    Docker Roadmap
                    AWS Cloud Practitioner

                    ================================

                    """);
        }

        /*
         * BACKEND DEVELOPER
         */

        if (lower.contains("spring")
                || lower.contains("rest api")) {

            jobs.append("""
                    ROLE:
                    Backend Developer

                    MATCH SCORE:
                    88%

                    SALARY:
                    ₹6 - ₹14 LPA

                    MISSING SKILLS:
                    Microservices
                    Docker

                    LEARNING RESOURCES:
                    Spring Microservices
                    Docker Fundamentals

                    ================================

                    """);
        }

        /*
         * FRONTEND
         */

        if (lower.contains("react")) {

            jobs.append("""
                    ROLE:
                    Frontend Developer

                    MATCH SCORE:
                    90%

                    SALARY:
                    ₹5 - ₹11 LPA

                    MISSING SKILLS:
                    Redux
                    TypeScript

                    LEARNING RESOURCES:
                    Redux Toolkit
                    TypeScript Handbook

                    ================================

                    """);
        }

        /*
         * FULL STACK
         */

        if (lower.contains("java")
                && lower.contains("react")) {

            jobs.append("""
                    ROLE:
                    Full Stack Developer

                    MATCH SCORE:
                    95%

                    SALARY:
                    ₹8 - ₹18 LPA

                    MISSING SKILLS:
                    Docker
                    AWS

                    LEARNING RESOURCES:
                    Full Stack Roadmap
                    AWS Basics

                    ================================

                    """);
        }

        return jobs.toString();
    }
}