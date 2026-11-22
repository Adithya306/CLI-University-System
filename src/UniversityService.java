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
}
