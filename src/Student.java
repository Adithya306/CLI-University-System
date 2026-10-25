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
}
