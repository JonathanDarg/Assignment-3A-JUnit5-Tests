package A3B_Testing.A3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GradeBookTest {

    private GradeBook gradeBook;

    @BeforeEach
    void setUp() {
        gradeBook = new GradeBook();
    }
    
    
    /**
     * This Test uses the demo grades to make sure that the letter grade is an A
     * @author JonathanDargakis
     */
    @Test
    void LetterGradeA() {
    	 gradeBook.setAssignmentWeight("HW1", 0.2);
         gradeBook.setAssignmentWeight("HW2", 0.3);
         gradeBook.setAssignmentWeight("Exam", 0.5);

         gradeBook.addStudentGrade("Alice", "HW1", 99);
         gradeBook.addStudentGrade("Alice", "HW2", 99);
         gradeBook.addStudentGrade("Alice", "Exam", 99);
         
         String letterGrade = gradeBook.getLetterGrade("Alice");
         
         assertEquals("A", letterGrade);
    }
    
    
    /**
     * This Test ensures that the final calculation of the grade is 99.
     * Making sure that the logic is correct and not being cut off early (off by one)
     * @author JonathanDargakis
     */
    @Test
    void FinalGrade99() {
    	 gradeBook.setAssignmentWeight("HW1", 0.2);
         gradeBook.setAssignmentWeight("HW2", 0.3);
         gradeBook.setAssignmentWeight("Exam", 0.5);

         gradeBook.addStudentGrade("Alice1", "HW1", 99);
         gradeBook.addStudentGrade("Alice1", "HW2", 99);
         gradeBook.addStudentGrade("Alice1", "Exam", 99);
         
         //average: 90*0.2 + 90*0.3 + 90*.5 = 99
         //Missing Last Grade: 90*0.2 + 90*0.3 = 49
         double grade = gradeBook.calculateFinalGrade("Alice1");
         
         
         assertEquals(99, grade);
    }
    
    
    /**
     * This Tests makes sure that the normalization of the weight works properly with a lower weight
     * * Making sure that the weight is not assumed to be 1
     * @author JonathanDargakis
     */
    @Test
    void MissingWeight() {
    	 gradeBook.setAssignmentWeight("HW1", 0.1);
         gradeBook.setAssignmentWeight("HW2", 0.1);
    	 gradeBook.setAssignmentWeight("HW3", 0.3);

         gradeBook.addStudentGrade("Alice2", "HW1", 99);
         gradeBook.addStudentGrade("Alice2", "HW2", 99);
         gradeBook.addStudentGrade("Alice2", "HW3", 99);
                  
         double grade = gradeBook.calculateFinalGrade("Alice2");
         assertEquals(99, grade);
    }
    
    
    /**
     * This test tests the normalization of the with with an excess amount - over 1
     * Making sure that the weight is not assumed to be 1
     * @author JonathanDargakis
     */
    @Test
    void ExtraWeight() {
    	gradeBook.setAssignmentWeight("Hw1", 0.2);
    	gradeBook.setAssignmentWeight("Exam", 0.9);
    	
    	gradeBook.addStudentGrade("Alice4", "Hw1", 99);
        gradeBook.addStudentGrade("Alice4", "Exam", 70);
        
        double grade = gradeBook.calculateFinalGrade("Alice4");
        assertEquals(75.3, grade);
    	
    	 
    } 
    
    
    /**
     * This test makes sure that when calling an older student from another test a grade is not returned
     * Ensuring there isnt any leftover stale data 
     * @author JonathanDargakis
     */
    @Test
    void StaleDataThrowsException() {
        //calling a method that should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            gradeBook.getLetterGrade("Alice"); 
        });
    }
    
    
    /**
     * This test Ensures that the grade bounds are working properly
     * As 90 should be an A
     * @author JonathanDargakis
     */
    @Test
    void Rounding() {
    	gradeBook.setAssignmentWeight("Hw1", 0.2);
    	gradeBook.setAssignmentWeight("Hw2", 0.3);
    	gradeBook.setAssignmentWeight("Exam", 0.5);
    	gradeBook.addStudentGrade("Alice5", "Hw1", 90);
    	gradeBook.addStudentGrade("Alice5", "Hw2", 90);
    	gradeBook.addStudentGrade("Alice5", "Exam", 90);
    	
    	String letterGrade = gradeBook.getLetterGrade("Alice5"); 
    	assertEquals("A", letterGrade);
    }
    
    
    /**
     * This test makes sure that after removing a student that the student is not enrolled and you cannot calculate their old grade
     * @author JonathanDargakis
     */
    @Test
    void GradeCache() {
    	gradeBook.setAssignmentWeight("Hw1", 0.5);
    	gradeBook.setAssignmentWeight("Hw2", 0.5);
    	gradeBook.setAssignmentWeight("Exam", 0.5); //random 3rb because off by one 
    	gradeBook.addStudentGrade("Alice6", "Hw1", 90);
    	gradeBook.addStudentGrade("Alice6", "Hw2", 90);
    	gradeBook.addStudentGrade("Alice6", "Exam", 90);
   
    	
    	gradeBook.removeStudentFromGradebook("Alice6");
    
    	assertFalse(gradeBook.isStudentEnrolled("Alice6"));
    	assertThrows(IllegalArgumentException.class, () -> {
            gradeBook.calculateFinalGrade("Alice6");
        });
    	 
    }
    
    /**
     * This test makes sure that it sets invalid scores to 0 and can handle doubles
     * @author JonathanDargakis
     */
    @Test 
    void AbnormalScores() {
    	gradeBook.setAssignmentWeight("Hw1", 0.2);
    	gradeBook.setAssignmentWeight("Hw2", 0.3);
    	gradeBook.setAssignmentWeight("Exam", 0.5);
    	gradeBook.addStudentGrade("Alice7", "Hw1", -70);
    	gradeBook.addStudentGrade("Alice7", "Hw2", 101);
    	gradeBook.addStudentGrade("Alice7", "Exam", 75.5);
    	
    	double grade = gradeBook.calculateFinalGrade("Alice7");
    	assertEquals(37.8, grade);
    	
    }

    /**
     * This test makes sure they you can remove a student by using another string object
     * Ensuring that the comparison is between strings and not objects 
     * @author JonathanDargakis
     */
    @Test
    void RemoveStudent() {
    	gradeBook.setAssignmentWeight("Hw1", 1);
    	gradeBook.addStudentGrade("Alice8", "Hw1", 75);
    	
    	String studentToRemove = new String("Alice8"); // another string object to compare
        gradeBook.removeStudentFromGradebook(studentToRemove);
    	
    	assertFalse(gradeBook.isStudentEnrolled("Alice8"));
    	
    }
    
    
    /**
     * This tests makes sure that all of the grades bounds are working properly
     * @author JonathanDargakis
     */
    @Test
    void AllGrades() {
    	gradeBook.setAssignmentWeight("Hw1", 1);
    	gradeBook.addStudentGrade("Alice9", "Hw1", 90);
    	
    	String letterGradeA = gradeBook.getLetterGrade("Alice9"); 
    	assertEquals("A", letterGradeA);
    	
    	
    	gradeBook.addStudentGrade("Bob", "Hw1", 80);
    	
    	String letterGradeB = gradeBook.getLetterGrade("Bob"); 
    	assertEquals("B", letterGradeB);
    	
    	gradeBook.addStudentGrade("Cat", "Hw1", 70);
    	
    	String letterGradeC = gradeBook.getLetterGrade("Cat"); 
    	assertEquals("C", letterGradeC);
    	
    	gradeBook.addStudentGrade("Darek", "Hw1", 60);
    	
    	String letterGradeD = gradeBook.getLetterGrade("Darek"); 
    	assertEquals("D", letterGradeD);
    	
    	gradeBook.addStudentGrade("Eric", "Hw1", 50);
    	
    	String letterGradeF = gradeBook.getLetterGrade("Eric"); 
    	assertEquals("F", letterGradeF);
    	
    }
    
    
    /**
     * This test ensures that you are able to generate all of the grades at once
     * Showing the number of all the grades and each student that is in the mapping. 
     * @author JonathanDargakis
     */
    @Test
    void GenerateAllGrades () {
    	 // Step 1: Set assignment weights
        gradeBook.setAssignmentWeight("HW1", 0.5);
        gradeBook.setAssignmentWeight("HW2", 0.5);

        // Step 2: Add students and grades
        gradeBook.addStudentGrade("Alice", "HW1", 80.0);
        gradeBook.addStudentGrade("Alice", "HW2", 90.0);

        gradeBook.addStudentGrade("Bob", "HW1", 70.0);
        gradeBook.addStudentGrade("Bob", "HW2", 60.0);

        // Step 3: Generate all final grades
        Map<String, Double> allGrades = gradeBook.generateAllFinalGrades();

        // Step 4: Verify map contains all students
        assertEquals(2, allGrades.size());
        assertTrue(allGrades.containsKey("Alice"));
        assertTrue(allGrades.containsKey("Bob"));
    	
    }
    
   
    /**
     * This ensures that when a grade is null it is being treated as an int adding 0 instead of null
     * @author JonathanDargakis
     */
    @Test
    void NullScoreAsZero() {
     
        gradeBook.setAssignmentWeight("HW1", 0.5);
        gradeBook.setAssignmentWeight("HW2", 0.5);

        gradeBook.addStudentGrade("Alice10", "HW1", 80.0);
        //since HW2 is missing score = null = 0
  
        double finalGrade = gradeBook.calculateFinalGrade("Alice10");
        
        // = 80*0.5 + 0*0.5 = 40.0
        assertEquals(40.0, finalGrade);
    }
    
}
