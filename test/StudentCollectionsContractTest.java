import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void treeSet_sortsPolymorphicUsersAndMaintainsAntiSymmetry(){
        Student student = new Student(1,"Kasun");
        Tutor sameIdTutor = new Tutor(1,"Kasun","Maths");

        assertTrue(student.compareTo(sameIdTutor) < 0);
        assertTrue(sameIdTutor.compareTo(student) > 0);

        Set<Student> sortedUsers = new TreeSet<>();
        sortedUsers.add(new Tutor(2, "Nimal", "Physics"));
        sortedUsers.add(student);
        sortedUsers.add(new Tutor(2, "Nimal", "Maths"));
        sortedUsers.add(new Tutor(2, "Nimal", "Maths")); // Duplicate -> dropped via compareTo == 0

        assertEquals(3, sortedUsers.size());

        List<Student> ordered = List.copyOf(sortedUsers);
        assertEquals(new Student(1, "Kasun"), ordered.get(0));
        assertEquals(new Tutor(2, "Nimal", "Maths"), ordered.get(1));
        assertEquals(new Tutor(2, "Nimal", "Physics"), ordered.get(2));
    }
}
