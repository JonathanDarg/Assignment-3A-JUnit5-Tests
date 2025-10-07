package Assignment3;

/**
 * Demo class for the BuggyGradeBook project.
 * CS 483 - Assignment 3A-3B (Bug Hunt)
 * @author Chase Garnett & Fabrizio Guzzo
 * @version 1.0 10/07/2025
 */

public class GradeBookDemo {
    public static void main(String[] args) {
        GradeBook gb = new GradeBook();

        gb.setWeight("HW1", 0.2);
        gb.setWeight("HW2", 0.3);
        gb.setWeight("Exam", 0.5);

        gb.addGrade("Alice", "HW1", 90);
        gb.addGrade("Alice", "HW2", 80);
        gb.addGrade("Alice", "Exam", 85);

        gb.addGrade("Bob", "HW1", 100);
        gb.addGrade("Bob", "HW2", 90);
        gb.addGrade("Bob", "Exam", 70);

        System.out.println("Alice final: " + gb.getFinalGrade("Alice"));
        System.out.println("Alice letter: " + gb.getLetterGrade("Alice"));

        System.out.println("Bob final: " + gb.getFinalGrade("Bob"));
        System.out.println("Bob letter: " + gb.getLetterGrade("Bob"));
    }
}

