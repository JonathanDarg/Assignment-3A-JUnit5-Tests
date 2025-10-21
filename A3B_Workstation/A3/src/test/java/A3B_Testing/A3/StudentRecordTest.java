package A3B_Testing.A3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

class StudentRecordTest {

    private StudentRecord student;

    @BeforeEach
    void setUp() {
        student = new StudentRecord("Jonathan");
    }

    
    /**
     * THis test makes sure that the student name is Jonathan
     * @author JonathanDargakis
     */
    @Test
    void GetStudentName() {
        assertEquals("Jonathan", student.getStudentName());
    }
    
    
    /**
     * This test ensures that you are able to add assignments and later call them
     * @author JonathanDargakis
     */
    @Test
    void AddAndGetAssignmentScore() {
        student.addAssignmentScore("HW1", 80);
        student.addAssignmentScore("Exam", 76);

        assertEquals(80, student.getAssignmentScore("HW1"));
        assertEquals(76, student.getAssignmentScore("Exam"));

        // Test getting score for assignment not added
        assertNull(student.getAssignmentScore("HW2")); // Score should be null for missing assignment
    }
    
    
    /**
     * This test ensures that a missing assignment score is Null
     * later turned into 0
     * @author JonathanDargakis
     */
    @Test
    void MissingAssignmentScore() {
    	student.addAssignmentScore("HW1", 80);
        student.addAssignmentScore("Exam", 76);

        assertNull(student.getAssignmentScore("HW2"));
    } 
    

    /**
     * This test makes sure that you are able to get all of the assignment scores at once
     * verifying the size and contents 
     * @author JonathanDargakis
     */
    @Test
    void GetAllAssignmentScores() {
        student.addAssignmentScore("HW1", 56);
        student.addAssignmentScore("HW2", 79);

        Map<String, Double> allScores = student.getAllAssignmentScores();
        assertEquals(2, allScores.size());
        assertEquals(56, allScores.get("HW1"));
        assertEquals(79, allScores.get("HW2"));
    }
    
    
    /**
     * This test makes sure that all of the Assignments are removed and cannot be called later
     * @author JonathanDargakis
     */
    @Test
    void ClearAssignments() {
        student.addAssignmentScore("HW1", 89);
        student.addAssignmentScore("Exam", 82);

        assertEquals(2, student.getAllAssignmentScores().size());

        student.clearAssignments(); 

        assertEquals(0, student.getAllAssignmentScores().size());
        assertNull(student.getAssignmentScore("HW1"));
        assertNull(student.getAssignmentScore("Exam"));
    }

   
}
