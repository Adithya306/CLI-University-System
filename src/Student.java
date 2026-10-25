import java.util.Objects;

public class Student {

    private String name;
    private int studentID;

    public Student(String name){
        this.name=name;
        this.studentID=studentID;
    }

    public Student(int studentID, String name){
        this.name=name;
        this.studentID=studentID;
    }


    public int getStudentID(){
        return studentID;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

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
        return this.studentID == student.studentID;
    }

    @Override
    public int hashCode() {
        // Generates an integer hash code based exclusively on the studentID
        return Objects.hash(studentID);
    }
}
