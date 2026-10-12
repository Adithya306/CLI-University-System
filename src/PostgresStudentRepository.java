import java.sql.*;

public class PostgresStudentRepository implements StudentRepository {

    public PostgresStudentRepository () {

        String createSequenceSQL = "CREATE SEQUENCE IF NOT EXISTS global_user_seq START 100;";
        String createStudentTableSQL = "CREATE TABLE IF NOT EXISTS students (" +
                "student_id INT PRIMARY KEY, " +
                "name VARCHAR(100)" +
                ");";

        String createTutorTableSQL = "CREATE TABLE IF NOT EXISTS tutors ("+
                "tutor_id INT PRIMARY KEY, "+
                "name VARCHAR(100)," +
                "specialization VARCHAR(100)" +
                ");";

        try {

            // Get the live connection from the manager we built earlier
            Connection connection = DatabaseManager.getConnection();

            // Create a statement object to carry our SQL string to the database
            Statement statement = connection.createStatement();

//            TEMPORARY LINE: Delete the broken table
            statement.execute("DROP TABLE tutors;");
            statement.execute("DROP TABLE students;");

            // Execute the SQL
            statement.execute(createSequenceSQL);
            statement.execute(createStudentTableSQL);
            statement.execute(createTutorTableSQL);

            System.out.println("Database tables and sequence are ready");
            connection.close();

        } catch (SQLException e) {

            System.out.println("Failed to create table!");
            e.printStackTrace();
        }
    }
    @Override
    public void save(Student student) {

        if (student instanceof Tutor){

            Tutor tutor = (Tutor) student;

            String insertSQL = "INSERT INTO tutors (name, specialization) VALUES (?,?);" ;

            try {
                Connection connection = DatabaseManager.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

//                preparedStatement.setInt(1,tutor.getStudentID());
                preparedStatement.setString(1, tutor.getName());
                preparedStatement.setString(2,tutor.getSpecialization());

                preparedStatement.executeUpdate();

                System.out.println("Tutor has been saved successfully!");
                connection.close();

            } catch (SQLException e){
                System.out.println("Failed to save tutor!");
                e.printStackTrace();
            }

            } else {

            // 1. The SQL string with ? placeholders
            String insertSQL = "INSERT INTO students (name) VALUES (?);";

            try {
                Connection connection = DatabaseManager.getConnection();

                // 2. PreparedStatement securely locks the SQL structure in place
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

                // 3. We fill in the placeholders (Index 1 is the first ?, Index 2 is the second)
//                preparedStatement.setInt(1, student.getStudentID());
                preparedStatement.setString(1, student.getName());

                // 4. Send the data to the database
                preparedStatement.executeUpdate();

                System.out.println("Student safely stored in PostgreSQL!");
                connection.close();

            } catch (SQLException e) {
                System.out.println("Database write failed.");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void displayAll() {

        String selectSQL = "SELECT student_id AS id, name, 'Student' AS Role, 'None' AS specialization FROM students " +
                            "UNION " +
                            "SELECT tutor_id AS id, name, 'Tutor' AS Role, specialization FROM tutors;";


        try{
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(selectSQL);

            System.out.println("\n--- Registered users (Database) ---");

            while (resultSet.next()){

//                int studentID = resultSet.getInt("student_id");
//                String name = resultSet.getString("name");

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String role = resultSet.getString("Role");
                String specialization = resultSet.getString("specialization");

                System.out.println(id + " " + name+ " " + role + " " + specialization);
            }

            connection.close();

        } catch (SQLException e) {
            System.out.println("Failed to fetch students from database.");
            e.printStackTrace();
        }

    }
}
