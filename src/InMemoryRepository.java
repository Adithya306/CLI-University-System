import java.util.ArrayList;

public class InMemoryRepository implements StudentRepository{

    private ArrayList<Student> students = new ArrayList<>();

    @Override
    public void save(Student student) {

        students.add(student);
        System.out.println(" User successfully saved to memory");
    }

    @Override
    public void displayAll(){

        System.out.println("\n--- Registered Users ---");
        for (Student s : students){
            System.out.println(s.toString());
        }
    }

}
