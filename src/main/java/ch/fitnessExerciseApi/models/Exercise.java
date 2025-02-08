package ch.fitnessExerciseApi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Document("Exercise")
public class Exercise implements Serializable { // implements Serializable
    @Serial
    private static final long serialVersionUID = 1L; // added serialVersionUID

    @Id
    private final String id;
    private final String name;
    private final String force;
    private final String level;
    private final String mechanic;
    private final String equipment;
    private final List<String> primaryMuscles;
    private final List<String> secondaryMuscles;
    private final List<String> instructions;
    private final String category;
    private final List<String> images;

    public Exercise(String id,
                    String name,
                    String force,
                    String level,
                    String mechanic,
                    String equipment,
                    List<String> primaryMuscles,
                    List<String> secondaryMuscles,
                    List<String> instructions,
                    String category,
                    List<String> images) {
        this.id = id;
        this.name = name;
        this.force = force;
        this.level = level;
        this.mechanic = mechanic;
        this.equipment = equipment;
        this.primaryMuscles = primaryMuscles;
        this.secondaryMuscles = secondaryMuscles;
        this.instructions = instructions;
        this.category = category;
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getForce() {
        return force;
    }

    public String getLevel() {
        return level;
    }

    public String getMechanic() {
        return mechanic;
    }

    public String getEquipment() {
        return equipment;
    }

    public List<String> getPrimaryMuscles() {
        return primaryMuscles;
    }

    public List<String> getSecondaryMuscles() {
        return secondaryMuscles;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getImages() {
        return images;
    }
}
