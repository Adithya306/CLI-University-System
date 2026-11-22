import java.util.List;

public class UniversityService {

    private final StudentRepository studentRepository;

    public UniversityService(StudentRepository studentRepository) {
        if(studentRepository == null) {
            throw new IllegalArgumentException("StudentRepository cannot be null");
        }
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student findByID(int id){
        validateID(id);
        return studentRepository.findById(id);
    }

    public boolean existsByID(int id){
        return studentRepository.existsById(id);
    }

    public long getTotalUserCount(){
        return studentRepository.count();
    }

    private static void validateID(int id){
        if(id <= 0) {
            throw new IllegalArgumentException("ID must be a positive integer");
        }
    }

    public Student registerStudent(String name){
        Student student = new Student(name);
        studentRepository.save(student);
        return student;
    }

    public Tutor registerTutor(String name, String specialization){
        Tutor tutor = new Tutor(name, specialization);
        studentRepository.save(tutor);
        return tutor;
    }
}
