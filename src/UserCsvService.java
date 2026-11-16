public class UserCsvService {

    private final StudentRepository repository;

    public UserCsvService(StudentRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null.");
        }
        this.repository = repository;
    }
}
