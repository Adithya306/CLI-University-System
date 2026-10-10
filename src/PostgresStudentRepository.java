import java.sql.*;

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

        // 1. The SQL string with ? placeholders
        String insertSQL = "INSERT INTO students (student_id, name) VALUES (?, ?);";

        try {
            Connection connection = DatabaseManager.getConnection();

            // 2. PreparedStatement securely locks the SQL structure in place
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            // 3. We fill in the placeholders (Index 1 is the first ?, Index 2 is the second)
            preparedStatement.setInt(1, student.getStudentID());
            preparedStatement.setString(2, student.getName());

            // 4. Send the data to the database
            preparedStatement.executeUpdate();

            System.out.println("Student safely stored in PostgreSQL!");
            connection.close();

        } catch (SQLException e) {
            System.out.println("Database write failed.");
            e.printStackTrace();
        }

    }

    @Override
    public void displayAll() {

        String selectSQL = "SELECT * FROM students;";

        try{
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(selectSQL);

            System.out.println("\n--- Registered Students (Database) ---");

            while (resultSet.next()){

                int studentID = resultSet.getInt("student_id");
                String name = resultSet.getString("name");

                System.out.println(studentID + " " + name);
            }

            connection.close();

        } catch (SQLException e) {
            System.out.println("Failed to fetch students from database.");
            e.printStackTrace();
        }

    }
}
