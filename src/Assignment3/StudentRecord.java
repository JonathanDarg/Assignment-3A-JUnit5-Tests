package Assignment3;

/**
 * Represents an individual student's set of grades.
 * CS 483 - Assignment 3A-3B (Bug Hunt)
 * @author Chase Garnett & Fabrizio Guzzo
 * @version 1.0 10/07/2025
 */

import java.util.*;

public class StudentRecord {

    private String name;
    private Map<String, Double> grades;

    public StudentRecord(String name) {
        this.name = name;
        this.grades = new HashMap<>();
    }

    public void addGrade(String assignment, double score) {
        grades.put(assignment, score);
    }

    public Double getScore(String assignment) {
        return grades.get(assignment);
    }

    public Map<String, Double> getAllGrades() {
        return grades;
    }

    public String getName() {
        return name;
    }

    // BUG #10: No reset or clear method — stale data across runs
}
