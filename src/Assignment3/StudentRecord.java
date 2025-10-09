package Assignment3;

/**
 * Represents an individual student's set of grades.
 * CS 483 - Assignment 3A-3B (Bug Hunt)
 * @author Chase Garnett & Fabrizio Guzzo
 * @version 1.0 10/07/2025
 */

import java.util.*;

public class StudentRecord {

    private String studentName;
    private Map<String, Double> assignmentScores;

    public StudentRecord(String studentName) {
        this.studentName = studentName;
        this.assignmentScores = new HashMap<>();
    }

    public void addAssignmentScore(String assignmentName, double score) {
        assignmentScores.put(assignmentName, score);
    }

    public Double getAssignmentScore(String assignmentName) {
        return assignmentScores.get(assignmentName);
    }

    public Map<String, Double> getAllAssignmentScores() {
        return assignmentScores;
    }

    public String getStudentName() {
        return studentName;
    }

    // BUG #10: No reset or clear method — stale data across runs
}
