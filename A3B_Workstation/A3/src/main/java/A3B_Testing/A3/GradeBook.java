package A3B_Testing.A3;

/**
 * Our gradebook system that calculates final grades and letter grades for students
 * CS 483 - Assignment 3A-3B (Bug Hunt)
 * @author Chase Garnett & Fabrizio Guzzo
 * @version 1.0 10/07/2025
 */

import java.util.*;

public class GradeBook {

    private Map<String, StudentRecord> allStudents;
    private Map<String, Double> assignmentWeights;
    private Map<String, Double> gradeCache;

    public GradeBook() {
        allStudents = new HashMap<>();
        assignmentWeights = new HashMap<>();
        gradeCache = new HashMap<>();
    }

    public void addStudentGrade(String studentName, String assignment, double score) {
        // BUG #8: Allows negative scores
        if (!allStudents.containsKey(studentName)) {
            allStudents.put(studentName, new StudentRecord(studentName));
        }
        if (score < 0 || score > 100) score = 0; //Fix: Set bounds for Score to not be over 100 or negative
        allStudents.get(studentName).addAssignmentScore(assignment, score);
    }

    public void setAssignmentWeight(String assignment, double weight) {
        assignmentWeights.put(assignment, weight);
    }

    public double calculateFinalGrade(String studentName) {
        // BUG #4: Null handling — may crash if student has no record
        StudentRecord student = allStudents.get(studentName);
        if (student == null) {
        	throw new IllegalArgumentException("Student not found"); // Fix: if student is not throw and error
        }
        
        // BUG #6: Cached value not cleared when student removed
        if (gradeCache.containsKey(studentName)) {
            return gradeCache.get(studentName);
        }

        double totalPoints = 0.0;
        double weightTotal = 0.0;
        int assignmentCount = 0;

        for (Map.Entry<String, Double> weightEntry : assignmentWeights.entrySet()) {
            String assignmentName = weightEntry.getKey();
            double assignmentWeight = weightEntry.getValue();
            weightTotal += assignmentWeight;

            // BUG #1: Off-by-one — skip last assignment
            //if (assignmentCount == assignmentWeights.size() - 1) break; //Fix: Stop it from breaking the loop on the last index
            assignmentCount++;

            Double studentScore = student.getAssignmentScore(assignmentName);
            if (studentScore != null) {
                totalPoints += studentScore * assignmentWeight;
            } else {
            	totalPoints += 0 * assignmentWeight; // fix: studentScore should count as zero, not nothing when null
            }
            // BUG #5: Missing assignment ignored (should count as zero)
        }

        // BUG #7: Fails to normalize weight sum (assumes it's 1)
        double finalGrade;
        if (weightTotal > 0) {
            finalGrade = totalPoints / weightTotal;  //Fix instead of directly assuming the weighted sum to be 1, normalize weight to be over/under 1
        } else {
            finalGrade = 0.0;
        }
        
        // BUG #2: Truncates instead of rounding
        finalGrade = Math.round(finalGrade * 10) / 10.0; //Fix: round to the nearest tenth's place


        gradeCache.put(studentName, finalGrade);
        return finalGrade;
    }

    public String getLetterGrade(String studentName) {
        double numericGrade = calculateFinalGrade(studentName);
        // BUG #3: Cutoff uses '>' not '>='
        if (numericGrade >= 90) return "A";
        if (numericGrade >= 80) return "B";
        if (numericGrade >= 70) return "C";
        if (numericGrade >= 60) return "D";
        return "F";
    }

    public void removeStudentFromGradebook(String studentName) {
        // BUG #9: Uses == for String comparison (fails on new String)
        for (String currentStudent : allStudents.keySet()) {
            if (currentStudent.equals(studentName)) { // Should be equals() Fix: instead of comparing objects compare strings using .equals()
                allStudents.remove(currentStudent);
                gradeCache.remove(currentStudent);  //Fix: removes student and cache 
                break;
            }
        }
    }

    public Map<String, Double> generateAllFinalGrades() {
        Map<String, Double> finalGrades = new HashMap<>();
        for (String student : allStudents.keySet()) {
            finalGrades.put(student, calculateFinalGrade(student));
        }
        return finalGrades;
    }

    public boolean isStudentEnrolled(String studentName) {
        return allStudents.containsKey(studentName);
    }
}

