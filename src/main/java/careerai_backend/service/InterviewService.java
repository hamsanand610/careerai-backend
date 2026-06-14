package careerai_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import careerai_backend.service.AIService;

@Service
public class InterviewService {

    @Autowired
    private AIService aiService;

    public String generateQuestionsFromResume(
            String role,
            String difficulty,
            String resumeText
    ) {

        String prompt = """
                Act as a professional technical interviewer.

                Candidate Resume:

                %s

                Target Role:
                %s

                Difficulty Level:
                %s

                Generate 10 interview questions.

                Rules:

                1. Questions must be based on the resume skills.
                2. Questions must match the selected role.
                3. Questions must match the selected difficulty level.
                4. Mix fundamentals and practical scenarios.
                5. Number all questions.
                6. Do NOT provide answers.

                Output only interview questions.
                """
                .formatted(
                        resumeText,
                        role,
                        difficulty
                );

        return aiService.askAI(prompt);
    }
    public String evaluateAnswer(
        String question,
        String answer
) {

    String prompt = """
            Act as a senior technical interviewer.

            Interview Question:
            %s

            Candidate Answer:
            %s

            Evaluate the answer and provide:

            1. Score out of 10
            2. Strengths
            3. Weaknesses
            4. Missing Points
            5. Improved Answer

            Be constructive and professional.
            """
            .formatted(
                    question,
                    answer
            );

    return aiService.askAI(prompt);
}
}