package br.com.stackoverflowclone;

import br.com.stackoverflowclone.model.Flag;
import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.repositories.FlagRepository;
import br.com.stackoverflowclone.repositories.QuestionRepository;
import br.com.stackoverflowclone.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.apache.tomcat.jni.Local;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
@EnableSpringDataWebSupport
public class StackoverflowCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(StackoverflowCloneApplication.class, args);
    }

//    @Bean
    public CommandLineRunner preencherUsuarios(UserRepository userRepository,
                                               QuestionRepository questionRepository,
                                               FlagRepository flagRepository) {
        return (args) -> {
            for(int i = 0; i < 20; i++) {
                User user = new User();
                user.setBirthday(LocalDate.now());
                user.setEmail(i + "email@email.com");
                user.setCreatedAt(LocalDateTime.now());
                user.setUpdatedAt(LocalDateTime.now());
                user.setEnabled(true);
                user.setName("Usuario"+i);
                userRepository.save(user);
            }

            for(int i = 1; i < 10; i++) {
                Question question = new Question();
                question.setUser(userRepository.findById(Integer.valueOf(i).longValue()).get());
                Flag flag = new Flag();
                flag.setCreatedAt(LocalDateTime.now());
                flag.setUpdatedAt(LocalDateTime.now());
                flag.setEnabled(Boolean.TRUE);
                flag.setDescription("flag"+i);
                Flag savedFlag = flagRepository.save(flag);
                question.setFlags(Arrays.asList(savedFlag));
                question.setComment("comment " + i);
                question.setResolved(Boolean.FALSE);
                question.setCreatedAt(LocalDateTime.now());
                question.setUpdatedAt(LocalDateTime.now());
                questionRepository.save(question);
            }
        };
    }
}
