package careerai_backend;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CareeraiBackendApplication {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @PostConstruct
    public void printDbUrl() {

        System.out.println("=================================");
        System.out.println("DATABASE URL = " + dbUrl);
        System.out.println("=================================");

    }

    public static void main(String[] args) {
        SpringApplication.run(CareeraiBackendApplication.class, args);
    }
}