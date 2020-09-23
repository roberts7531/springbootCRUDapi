package com.roberts7531.spring;

import com.roberts7531.spring.Models.Task;
import com.roberts7531.spring.Repositories.TaskRepository;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.slf4j.Logger;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(TaskRepository repository){
        return args -> {
            log.info("Preloading " + repository.save(new Task("Task 1", "This is a test task Nr1")));
            log.info("Preloading " + repository.save(new Task("Task 2", "This is a test task Nr2")));
        };
    }
}
