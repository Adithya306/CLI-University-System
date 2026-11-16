public class UserCsvService {

    private final StudentRepository repository;

    public UserCsvService(StudentRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null.");
        }
        this.repository = repository;
    }

    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        boolean hasSpecialChars = value.contains(",") || value.contains("\"")
                || value.contains("\n") || value.contains("\r");
        if (hasSpecialChars) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
