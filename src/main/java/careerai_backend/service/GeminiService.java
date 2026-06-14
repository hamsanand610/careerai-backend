package careerai_backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

//@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder().build();

    public String analyzeResume(String resumeText) {
      if(apiKey == null || apiKey.isBlank()){
    return "AI Analysis currently unavailable";
}

        String prompt = """
                Analyze the following resume and provide:

                1. Strengths
                2. Weaknesses
                3. Missing Skills
                4. Improvement Suggestions

                Resume:
                """ + resumeText;

        String requestBody = """
                {
                  "contents": [{
                    "parts": [{
                      "text": "%s"
                    }]
                  }]
                }
                """.formatted(prompt.replace("\"", "\\\""));

        try {

            String response = webClient.post()
                    .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .retry(3)
                    .block();

            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(response);

            return root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

        } catch (Exception e) {

            e.printStackTrace();

            return """
            Gemini AI is temporarily unavailable.

            ATS Analysis is still available.

            Please try again in a few moments.
            """;
        }
    }
    public String askGemini(String prompt) {

    if (apiKey == null || apiKey.isBlank()) {
        return "Gemini API unavailable";
    }

    String requestBody = """
            {
              "contents": [{
                "parts": [{
                  "text": "%s"
                }]
              }]
            }
            """.formatted(
            prompt.replace("\"", "\\\"")
    );

    try {

        String response = webClient.post()
                .uri(
                        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key="
                                + apiKey
                )
                .header(
                        "Content-Type",
                        "application/json"
                )
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper mapper =
                new ObjectMapper();

        JsonNode root =
                mapper.readTree(response);

        return root
                .path("candidates")
                .get(0)
                .path("content")
                .path("parts")
                .get(0)
                .path("text")
                .asText();

    } catch (Exception e) {

        e.printStackTrace();

        return "Failed to generate Gemini response.";
    }
}
}