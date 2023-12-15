package app.integration;

import app.integration.models.entity.TransactionEntity;
import app.integration.models.enumiration.ExpenseCategoryDto;
import app.integration.repository.TransactionRepository;
import app.integration.service.impl.TransactionServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class IntegrationApplication {

    @Autowired
    private TransactionServiceImpl transactionService;

    public static void main(String[] args) {
        SpringApplication.run(IntegrationApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {

        };
    }
}
