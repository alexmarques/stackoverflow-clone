package br.com.stackoverflowclone;

import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class StackoverflowCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(StackoverflowCloneApplication.class, args);
    }

    @Bean
    public CommandLineRunner preencherUsuarios(UserRepository userRepository) {
        return (args) -> {
            User user = new User();
            user.setBirthday(LocalDate.now());
            user.setEmail("email@email.com");
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setEnabled(true);
            user.setName("Alex");
            userRepository.save(user);
        };
    }


}
