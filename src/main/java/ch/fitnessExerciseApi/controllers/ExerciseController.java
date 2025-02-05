package ch.fitnessExerciseApi.controllers;

import ch.fitnessExerciseApi.models.Exercise;
import ch.fitnessExerciseApi.services.ExerciseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping
    @Operation(summary = "Create an Exercise", description = "Creates a new Exercise")
    public Exercise addExercise(@RequestBody Exercise exercise) {
        return exerciseService.save(exercise);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an Exercise", description = "Deletes an Exercise by id")
    public void deleteExercise(@PathVariable("id") String id) {
        exerciseService.delete(id);
    }
}