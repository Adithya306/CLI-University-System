public class Student {

    private String name;
    private int studentID;

    public Student(String name , int studentID){
        this.name=name;
        this.studentID=studentID;
    }

    public int getStudentID(){
        return studentID;
    }

    @Override
    public String toString(){
        return "Student Name : " + this.name + ", Student ID : " + this.studentID;
    }
}
