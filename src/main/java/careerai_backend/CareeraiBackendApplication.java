package careerai_backend;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CareeraiBackendApplication {
	

    @Value("${DB_URL:NOT_FOUND}")
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

    @Bean
    CommandLineRunner testEnv() {
        return args -> {
            System.out.println("DB_URL = " + dbUrl);
        };
    }
}