import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository implements StudentRepository{

    //***Array list implementation of InMemoryRepository***

//    private ArrayList<Student> students = new ArrayList<>();
//
//    @Override
//    public void save(Student student) {
//
//        students.add(student);
//        System.out.println(" User successfully saved to memory");
//    }
//
//    @Override
//    public void displayAll(){
//
//        System.out.println("\n--- Registered Users ---");
//        for (Student s : students){
//            System.out.println(s.toString());
//        }
//    }
//
//    @Override
//    public void findUserById(int targetId) {
//
//    }
//
//    @Override
//    public void deleteUserById(int targetId) {
//
//    }
//
//    @Override
//    public void updateUserById(int targetId, String newName) {
//
//    }


    //***Hash Map implementation of InMemoryRepository***

    // Key: Student ID (Integer) | Value: Student Object
    private final Map<Integer,Student> studentMap = new HashMap<>();

    @Override
    public void  save(Student student) {

        // map.put(key, value) stores or updates the entry in O(1) time
        studentMap.put(student.getStudentID(), student);
        System.out.println("Student saved in RAM (HashMap).");

    }

    @Override
    public void displayAll() {
        System.out.println("\n--- Registered Users (In-Memory Map) ---");
        if (studentMap.isEmpty()) {
            System.out.println("No users found in memory.");
            return;
        }

        // studentMap.values() returns a Collection view of all Student objects
        for (Student student : studentMap.values()) {
            System.out.println(student);
        }
    }

    public void findUserById(int targetId) {
        // O(1) Direct Lookup
        Student student = studentMap.get(targetId);

        if (student != null) {
            System.out.println("\n--- User Found ---");
            System.out.println(student);
        } else {
            // Throwing our custom domain exception when an ID is missing
            throw new UserNotFoundException("User with ID " + targetId + " was not found in memory.");
        }
    }

    public void deleteUserById(int targetId) {
        // map.remove(key) returns the removed Value, or null if the key did not exist
        Student removedStudent = studentMap.remove(targetId);

        if (removedStudent == null) {
            throw new UserNotFoundException("User with ID " + targetId + " was not found to delete.");
        }

        System.out.println("User " + targetId + " removed from memory.");
    }

    public void updateUserById(int targetId, String newName) {

    }
}

