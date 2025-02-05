package ch.fitnessExerciseApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FitnessExercisesApi {

    public static void main(String[] args) {
        SpringApplication.run(FitnessExercisesApi.class, args);
    }

}
