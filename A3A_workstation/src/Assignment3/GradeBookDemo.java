package Assignment3;

/**
 * Demo class for the BuggyGradeBook project.
 * CS 483 - Assignment 3A-3B (Bug Hunt)
 * @author Chase Garnett & Fabrizio Guzzo
 * @version 1.0 10/07/2025
 */

public class GradeBookDemo {
    public static void main(String[] args) {
        GradeBook gradeBook = new GradeBook();

        gradeBook.setAssignmentWeight("HW1", 0.2);
        gradeBook.setAssignmentWeight("HW2", 0.3);
        gradeBook.setAssignmentWeight("Exam", 0.5);

        gradeBook.addStudentGrade("Alice", "HW1", 90);
        gradeBook.addStudentGrade("Alice", "HW2", 80);
        gradeBook.addStudentGrade("Alice", "Exam", 85);

        gradeBook.addStudentGrade("Bob", "HW1", 100);
        gradeBook.addStudentGrade("Bob", "HW2", 90);
        gradeBook.addStudentGrade("Bob", "Exam", 70);

        System.out.println("Alice's final grade: " + gradeBook.calculateFinalGrade("Alice"));
        System.out.println("Alice's letter grade: " + gradeBook.getLetterGrade("Alice"));

        System.out.println("Bob's final grade: " + gradeBook.calculateFinalGrade("Bob"));
        System.out.println("Bob's letter grade: " + gradeBook.getLetterGrade("Bob"));

    }
}