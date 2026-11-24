import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UniversityServiceTest {

    private UniversityService universityService;

    @BeforeEach
    void setUp() {

        StudentRepository inMemoryRepository = new InMemoryRepository();
        universityService = new UniversityService(inMemoryRepository);
    }

    @Test
    void registerStudent_savesAndRetrievesStudent(){
        universityService.registerStudent("Kasun");
        Student saved = universityService.findByID(1);
        assertEquals(new Student(1, "Kasun"), saved);
        assertEquals(1, universityService.getTotalUserCount());
    }

    @Test
    void registerTutor_savesTutorWithSpecialization(){
        universityService.registerTutor("Nipun", "Maths");

        Student saved = universityService.findByID(1);
        assertInstanceOf(Tutor.class, saved);
        assertEquals(new Tutor(1,  "Nipun", "Maths"), saved);

    }
}