import java.util.Objects;

public class Student implements Comparable<Student> {

    private final String name;
    private final int studentID;

    public Student(String name){
        this.name = validateName(name);
        this.studentID = 0;

    }

    public Student(int studentID, String name){

        if (studentID < 0) {
            throw new IllegalArgumentException("Student ID cannot be negative.");
        }
        this.name=validateName(name);
        this.studentID=studentID;
    }

    private String validateName(String name){
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be null or empty.");
        }
        return name.trim();
    }


    public int getStudentID(){
        return studentID;
    }

    public String getName(){
        return name;
    }

//    public void setName(String name){
//        this.name=name;
//    }

    @Override
    public String toString(){
        return "Student Name : " + this.name + ", Student ID : " + this.studentID;
    }

    @Override
    public boolean equals(Object other) {

        // 1. Reflexivity check: Are both references pointing to the exact same Heap address?
        if (this == other) {
            return true;
        }

        // 2. Null and Type check: Is the other object null or from a different class?
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        // 3. Downcast and Compare: Compare the unique business identity field (studentID)
        Student student = (Student) other;
        return this.studentID == student.studentID && Objects.equals(this.name, student.name);
    }

    @Override
    public int hashCode() {
        // Generates an integer hash code based exclusively on the studentID
        return Objects.hash(studentID, name);
    }

    @Override
    public int compareTo(Student other) {
        int idCompare = Integer.compare(studentID, other.studentID);
        if(idCompare != 0){
            return idCompare;
        }
        int nameCompare = this.name.compareTo(other.name);
        if(nameCompare != 0){
            return nameCompare;
        }
        return (other instanceof Tutor) ?-1:0;
    }
}
