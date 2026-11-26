import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    void getAllStudents_returnsAllRegisteredUsers() {
        universityService.registerStudent("Nuwan");
        universityService.registerTutor("Nimal", "English");

        List<Student> users = universityService.getAllStudents();
        assertEquals(2, users.size());
    }

    @Test
    void existsByID_returnsTrueWhenPresentAndFalseWhenMissing() {
        universityService.registerStudent("Kasun");

        assertTrue(universityService.existsByID(1));
        assertFalse(universityService.existsByID(99));
    }

    @Test
    void deleteByID_removesExistingUserAndThrowsForInvalidOrMissingInput(){

        universityService.registerStudent("saman");
        universityService.registerTutor("Nipun", "English");

        Student savedStu = universityService.findByID(1);

        Student savedTut = universityService.findByID(2);
        assertInstanceOf(Tutor.class, savedTut);

        assertEquals(new Student(1, "saman"), savedStu);
        assertEquals(new Tutor(2,"Nipun", "English"), savedTut);

        universityService.deleteById(1);
        universityService.deleteById(2);

        assertFalse(universityService.existsByID(1));
        assertFalse(universityService.existsByID(2));
        assertEquals(0, universityService.getTotalUserCount());

        assertThrows(UserNotFoundException.class, () -> universityService.findByID(1));
        assertThrows(UserNotFoundException.class, () -> universityService.deleteById(99));
        assertThrows(IllegalArgumentException.class, () -> universityService.findByID(0));
        assertThrows(IllegalArgumentException.class, () -> universityService.registerStudent("   "));

    }

    @Test
    void updateUser_updatesNameAndPreservesTutorSpecialization() {
        universityService.registerStudent("Kasun");
        universityService.registerTutor("Nimal", "Databases");

        universityService.updateUser(1, "Kasun Perera");
        universityService.updateUser(2, "Nimal Silva");

        assertEquals(new Student(1, "Kasun Perera"), universityService.findByID(1));

        Student updatedTutor = universityService.findByID(2);
        assertInstanceOf(Tutor.class, updatedTutor);
        assertEquals(new Tutor(2, "Nimal Silva", "Databases"), updatedTutor);
    }
}