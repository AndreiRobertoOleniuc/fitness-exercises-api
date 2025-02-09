package ch.fitnessExerciseApi.services;

import ch.fitnessExerciseApi.models.Exercise;
import ch.fitnessExerciseApi.repositories.ExerciseRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // Existing non-paginated search remains unchanged if needed
    @Cacheable(value = "exerciseCache", key = "#searchTerm")
    public List<Exercise> search(String searchTerm) {
        return exerciseRepository.search(searchTerm, Pageable.unpaged()).getContent();
    }

    // Paginated findAll with caching
    @Cacheable(value = "exerciseCache", key = "'findAll-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<Exercise> findAll(Pageable pageable) {
        return exerciseRepository.findAll(pageable);
    }

    // Paginated search with caching
    @Cacheable(value = "exerciseCache", key = "'search-' + #searchTerm + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<Exercise> search(String searchTerm, Pageable pageable) {
        return exerciseRepository.search(searchTerm, pageable);
    }
}