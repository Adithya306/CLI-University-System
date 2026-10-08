import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class PostgresStudentRepository implements StudentRepository {

    public PostgresStudentRepository () {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS students (" +
                "student_id INT PRIMARY KEY, " +
                "name VARCHAR(100)" +
                ");";

        try {

            // Get the live connection from the manager we built earlier
            Connection connection = DatabaseManager.getConnection();

            // Create a statement object to carry our SQL string to the database
            Statement statement = connection.createStatement();

            // Execute the SQL
            statement.execute(createTableSQL);

            System.out.println("Database table 'students' is ready.");
            connection.close();

        } catch (SQLException e) {

            System.out.println("Failed to create table!");
            e.printStackTrace();
        }
    }
    @Override
    public void save(Student student) {

    }

    @Override
    public void displayAll() {

    }
}
