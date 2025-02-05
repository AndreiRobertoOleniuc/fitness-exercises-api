package ch.fitnessExerciseApi.repositories;

import ch.fitnessExerciseApi.models.Exercise;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExerciseRepository extends MongoRepository<Exercise, String> {
}