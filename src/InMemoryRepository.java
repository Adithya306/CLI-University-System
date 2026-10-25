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
    private final Map<Integer,Student> studentsMap = new HashMap<>();

    @Override
    public void  save(Student student) {

        // map.put(key, value) stores or updates the entry in O(1) time
        studentsMap.put(student.getStudentID(), student);
        System.out.println("Student saved in RAM (HashMap).");

    }

    @Override
    public void displayAll() {
        System.out.println("\n--- Registered Users (In-Memory Map) ---");
        if (studentsMap.isEmpty()) {
            System.out.println("No users found in memory.");
            return;
        }

        // studentMap.values() returns a Collection view of all Student objects
        for (Student student : studentsMap.values()) {
            System.out.println(student);
        }
    }

    public void findUserById(int targetId) {
        // O(1) Direct Lookup
        Student foundStudent = studentsMap.get(targetId);

        if (foundStudent != null) {
            System.out.println("\n--- User Found ---");
            System.out.println(foundStudent);
        } else {
            // Throwing our custom domain exception when an ID is missing
            throw new UserNotFoundException("User with ID " + targetId + " was not found in memory.");
        }
    }

    public void deleteUserById(int targetId) {
        // map.remove(key) returns the removed Value, or null if the key did not exist
        Student removedStudent = studentsMap.remove(targetId);

        if (removedStudent == null) {
            throw new UserNotFoundException("User with ID " + targetId + " was not found to delete.");
        }

        System.out.println("User " + targetId + " removed from memory.");
    }

    public void updateUserById(int targetId, String newName) {

        Student existingStudent = studentsMap.get(targetId);

        if (existingStudent != null) {

            existingStudent.setName(newName);

//            If your domain objects are designed to be strictly immutable (no setters in student.java class),
//            you would instantiate a new Student object and overwrite the key entry using below steps

            // 1. Create a NEW Student object with the updated name (retaining the same ID)
//            Student updatedStudent = new Student(targetId, newName);
//
            // 2. Overwrite the old reference in the Map with the new Student object
//              studentsMap.put(targetId, updatedStudent);
//
            System.out.println("User " + targetId + " updated to " + newName);

        } else {
            throw new UserNotFoundException("User with ID " + targetId + " was not found in memory.");
        }
    }
}

