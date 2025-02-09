package ch.fitnessExerciseApi.controllers;

import ch.fitnessExerciseApi.models.Exercise;
import ch.fitnessExerciseApi.services.ExerciseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exercise")
@Tag(name = "Exercise", description = "Exercise API")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    @Operation(summary = "Get all Exercises", description = "Returns all Exercises")
    public List<Exercise> getAllExercises() {
        return exerciseService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an Exercise", description = "Returns a specific Exercise by id")
    public Optional<Exercise> getExerciseById(@PathVariable("id") String id) {
        return exerciseService.findById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Search Exercises", description = "Search exercises by name, mechanic, equipment, muscles and category")
    public List<Exercise> searchExercises(@RequestParam("q") String query) {
        return exerciseService.search(query);
    }

    // New paginated endpoint to get exercises
    @GetMapping("/paginated")
    @Operation(summary = "Get paginated Exercises", description = "Returns a paginated list of Exercises")
    public Page<Exercise> getAllExercisesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return exerciseService.findAll(pageable);
    }

    // New paginated endpoint for search
    @GetMapping("/search/paginated")
    @Operation(summary = "Search paginated Exercises", description = "Returns a paginated list of Exercises matching the search term")
    public Page<Exercise> searchExercisesPaginated(
            @RequestParam("q") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return exerciseService.search(query, pageable);
    }
}