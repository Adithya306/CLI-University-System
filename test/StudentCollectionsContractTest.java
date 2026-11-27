import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentCollectionsContractTest {
    @Test
    void hashSet_deduplicatesIdenticalEntitiesUsingHashCodeAndEquals() {
        Set<Student> uniqueUsers = new HashSet<>();
        uniqueUsers.add(new Student(1,"Kasun"));
        uniqueUsers.add(new Student(1, "Kasun"));   // Duplicate -> rejected
        uniqueUsers.add(new Tutor(2,"Nimal","Maths"));
        uniqueUsers.add(new Tutor(2,"Nimal","Maths"));  // Duplicate -> rejected
        uniqueUsers.add(new Tutor(2, "Nimal", "Physics"));  // Distinct specialization -> kept

        assertEquals(3, uniqueUsers.size());
    }
}
