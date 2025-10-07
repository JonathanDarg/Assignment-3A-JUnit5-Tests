package Assignment3;

/**
 * This is our code! Its goal is to compute final grades and letter grades for students.
 * CS 483 - Assignment 3A-3B (Bug Hunt)
 * @author Chase Garnett & Fabrizio Guzzo
 * @version 1.0 10/07/2025
 */

import java.util.*;

public class GradeBook {

    private Map<String, StudentRecord> students;
    private Map<String, Double> weights;
    private Map<String, Double> cachedFinals;

    public GradeBook() {
        students = new HashMap<>();
        weights = new HashMap<>();
        cachedFinals = new HashMap<>();
    }

    public void addGrade(String student, String assignment, double score) {
        // BUG #8: Allows negative scores
        if (!students.containsKey(student)) {
            students.put(student, new StudentRecord(student));
        }
        students.get(student).addGrade(assignment, score);
    }

    public void setWeight(String assignment, double weight) {
        weights.put(assignment, weight);
    }

    public double getFinalGrade(String student) {
        // BUG #4: Null handling — may crash if student has no record
        StudentRecord record = students.get(student);

        // BUG #6: Cached value not cleared when student removed
        if (cachedFinals.containsKey(student)) {
            return cachedFinals.get(student);
        }

        double total = 0.0;
        double weightSum = 0.0;
        int index = 0;

        for (Map.Entry<String, Double> entry : weights.entrySet()) {
            String assignment = entry.getKey();
            double weight = entry.getValue();
            weightSum += weight;

            // BUG #1: Off-by-one — skip last assignment
            if (index == weights.size() - 1) break;
            index++;

            Double score = record.getScore(assignment);
            if (score != null) {
                total += score * weight;
            }
            // BUG #5: Missing assignment ignored (should count as zero)
        }

        // BUG #7: Fails to normalize weight sum (assumes it’s 1)
        double finalGrade = total; 

        // BUG #2: Truncates instead of rounding
        finalGrade = (int) finalGrade; 

        cachedFinals.put(student, finalGrade);
        return finalGrade;
    }

    public String getLetterGrade(String student) {
        double grade = getFinalGrade(student);
        // BUG #3: Cutoff uses '>' not '>='
        if (grade > 90) return "A";
        if (grade > 80) return "B";
        if (grade > 70) return "C";
        if (grade > 60) return "D";
        return "F";
    }

    public void removeStudent(String student) {
        // BUG #9: Uses == for String comparison (fails on new String)
        for (String key : students.keySet()) {
            if (key == student) { // Should be equals()
                students.remove(key);
                break;
            }
        }
    }

    public Map<String, Double> getAllFinalGrades() {
        Map<String, Double> results = new HashMap<>();
        for (String student : students.keySet()) {
            results.put(student, getFinalGrade(student));
        }
        return results;
    }

    public boolean hasStudent(String student) {
        return students.containsKey(student);
    }
}
