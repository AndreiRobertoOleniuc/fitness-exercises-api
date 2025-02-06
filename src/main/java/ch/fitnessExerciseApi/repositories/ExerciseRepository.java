package ch.fitnessExerciseApi.repositories;

import ch.fitnessExerciseApi.models.Exercise;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ExerciseRepository extends MongoRepository<Exercise, String> {

    @Query("{ '$or': [ " +
           " {'name': {$regex: ?0, $options: 'i'}}, " +
           " {'mechanic': {$regex: ?0, $options: 'i'}}, " +
           " {'equipment': {$regex: ?0, $options: 'i'}}, " +
           " {'primaryMuscles': {$regex: ?0, $options: 'i'}}, " +
           " {'secondaryMuscles': {$regex: ?0, $options: 'i'}}, " +
           " {'category': {$regex: ?0, $options: 'i'}} " +
           "] }")
    List<Exercise> search(String searchTerm);
}