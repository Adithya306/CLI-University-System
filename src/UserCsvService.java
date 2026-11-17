import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

    public int exportToCsv(Path filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("File path cannot be null.");
        }

        List<Student> users = repository.findAll();

        try {
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }

            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
                // Write CSV header
                writer.write("id,name,role,specialization");
                writer.newLine();

                for (Student student : users) {
                    String role;
                    String specialization;

                    if (student instanceof Tutor tutor) {
                        role = "Tutor";
                        specialization = tutor.getSpecialization();
                    } else {
                        role = "Student";
                        specialization = "-";
                    }

                    String line = String.join(",",
                            String.valueOf(student.getStudentID()),
                            escapeCsv(student.getName()),
                            role,
                            escapeCsv(specialization)
                    );

                    writer.write(line);
                    writer.newLine();
                }
            }

            return users.size();

        } catch (IOException e) {
            throw new FileStorageException("Failed to export users to CSV: " + filePath, e);
        }
    }


    private List<String> parseCsvLine(String line, int lineNumber) {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == ',') {
                fields.add(currentField.toString());
                currentField.setLength(0);
            } else {
                currentField.append(c);
            }
        }

        fields.add(currentField.toString());
        return fields;
    }
}
