package microservices.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "microservices.microservices.repository")
public class MicroservicesApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroservicesApplication.class, args);
        System.out.println("✅ Application started with MongoDB!");
    }
}
