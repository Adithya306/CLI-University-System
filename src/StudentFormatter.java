import java.util.List;

public final class StudentFormatter {

    // Prevents instantiation of this stateless utility class
    private StudentFormatter() {
    }

    public static String formatTable(List<Student> users) {
        if (users == null || users.isEmpty()) {
            return "No users found." + System.lineSeparator();
        }

        StringBuilder stringBuilder = new StringBuilder();
        String rowFormat = "%-6s | %-22s | %-10s | %-20s%n";
        String divider = "-".repeat(66) + System.lineSeparator();

        stringBuilder.append(divider);
        stringBuilder.append(String.format(rowFormat, "ID", "Name", "Role", "Specialization"));
        stringBuilder.append(divider);

        for (Student user : users) {
            String role;
            String specialization;

            if (user instanceof Tutor tutor) {
                role = "Tutor";
                specialization = tutor.getSpecialization();
            } else {
                role = "Student";
                specialization = "-";
            }

            stringBuilder.append(String.format(
                    rowFormat,
                    user.getStudentID(),
                    user.getName(),
                    role,
                    specialization
            ));
        }

        stringBuilder.append(divider);
        return stringBuilder.toString();
    }
}
