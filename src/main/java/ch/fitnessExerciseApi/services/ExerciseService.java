package ch.fitnessExerciseApi.services;

import ch.fitnessExerciseApi.models.Exercise;
import ch.fitnessExerciseApi.repositories.ExerciseRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Cacheable(value = "exerciseCache")
    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }
    
    @Cacheable(value = "exerciseCache", key = "#id")
    public Optional<Exercise> findById(String id) {
        return exerciseRepository.findById(id);
    }
    
    @CacheEvict(value = "exerciseCache", allEntries = true)
    public Exercise save(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }
    
    @CacheEvict(value = "exerciseCache", key = "#id")
    public void delete(String id) {
        exerciseRepository.deleteById(id);
    }
    
    @Cacheable(value = "exerciseCache", key = "#searchTerm")
    public List<Exercise> search(String searchTerm) {
        return exerciseRepository.search(searchTerm);
    }
}