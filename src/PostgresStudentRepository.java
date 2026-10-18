import java.sql.*;

public class PostgresStudentRepository implements StudentRepository {

    public PostgresStudentRepository () {

        String createSequenceSQL = "CREATE SEQUENCE IF NOT EXISTS global_user_seq START 100;";
        String createStudentTableSQL = "CREATE TABLE IF NOT EXISTS students (" +
                "student_id INT PRIMARY KEY DEFAULT nextval('global_user_seq'), " +
                "name VARCHAR(100)" +
                ");";

        String createTutorTableSQL = "CREATE TABLE IF NOT EXISTS tutors ("+
                "tutor_id INT PRIMARY KEY DEFAULT nextval('global_user_seq'), "+
                "name VARCHAR(100)," +
                "specialization VARCHAR(100)" +
                ");";

        try {

            // Get the live connection from the manager we built earlier
            Connection connection = DatabaseManager.getConnection();

            // Create a statement object to carry our SQL string to the database
            Statement statement = connection.createStatement();

//            TEMPORARY LINE: Delete the broken table
//            statement.execute("DROP TABLE tutors;");
//            statement.execute("DROP TABLE students;");
//            statement.execute("DROP SEQUENCE IF EXISTS global_user_seq;");

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

    public void findUserById(int targetId){
        String searchSQL = "SELECT student_id AS id, name, 'Student' AS role, 'None' AS specialization FROM students WHERE student_id = ? " +
                "UNION " +
                "SELECT tutor_id AS id, name, 'Tutor' AS role, specialization FROM tutors WHERE tutor_id = ? ;";

        try {
            Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(searchSQL);

            preparedStatement.setInt(1,targetId);
            preparedStatement.setInt(2,targetId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                System.out.println("\n--- User Found ---");
                System.out.println("ID: " + resultSet.getInt("id") +
                        " | Name: " + resultSet.getString("name") +
                        " | Role: " + resultSet.getString("role") +
                        " | Specialization: " + resultSet.getString("specialization"));
            } else {
                System.out.println("User not found with ID: " + targetId);
            }

        } catch (SQLException e) {
            System.out.println("Failed to find user from database.");
            e.printStackTrace();
        }
    }



    // =========================================================================
    // [LEGACY / NON-TRANSACTIONAL] Auto-Commit Execution
    // Risk: Vulnerable to partial writes. If Statement 1 succeeds but
    // Statement 2 fails, the database becomes corrupt/inconsistent.
    // =========================================================================

//    public void deleteUserById(int targetId){
//
//        String deleteStudentSQL = "DELETE FROM students WHERE student_id = ? ;";
//        String deleteTutorSQL = "DELETE FROM tutors WHERE tutor_id = ? ;";
//
//        try {
//            Connection connection = DatabaseManager.getConnection();
//
//            PreparedStatement studentStatement = connection.prepareStatement(deleteStudentSQL);
//            studentStatement.setInt(1, targetId);
//            studentStatement.executeUpdate();
//
//            PreparedStatement tutorStatement = connection.prepareStatement(deleteTutorSQL);
//            tutorStatement.setInt(1,targetId);
//            tutorStatement.executeUpdate();
//
//
//            System.out.println("User deletion process completed");
//
//            connection.close();
//
//        } catch (SQLException e) {
//            System.out.println("Failed to delete user from database.");
//            e.printStackTrace();
//        }
//    }
//
//    public void updateUserById(int targetId, String newName){
//
//        String updateStudentSQL = "UPDATE students SET name = ? WHERE student_id =?;";
//        String updateTutorSQL = "UPDATE tutors SET name = ? WHERE tutor_id = ?;";
//
//        try {
//            Connection connection = DatabaseManager.getConnection();
//
//            PreparedStatement updateStudentStatement = connection.prepareStatement(updateStudentSQL);
//            updateStudentStatement.setString(1,newName);
//            updateStudentStatement.setInt(2,targetId);
//            updateStudentStatement.executeUpdate();
//
//            PreparedStatement updateTutorStatement = connection.prepareStatement(updateTutorSQL);
//            updateTutorStatement.setString(1,newName);
//            updateTutorStatement.setInt(2,targetId);
//            updateTutorStatement.executeUpdate();
//
//            System.out.println("User modification process completed");
//
//            connection.close();
//
//        } catch (SQLException e) {
//            System.out.println("Failed to update user from database.");
//            e.printStackTrace();
//        }
//    }
//}


        // =========================================================================
        // [ENTERPRISE / TRANSACTIONAL] ACID-Compliant Execution
        // Safety: Explicit transaction boundary. Disables auto-commit and enforces
        // atomic execution via commit() on success and rollback() on exception.
        // =========================================================================

public void updateUserById(int targetId, String newName){

            String updateStudentSQL = "UPDATE students SET name = ?, WHERE student_id = ? ;" ;
            String updateTutorSQL = "UPDATE tutors SET name = ? , WHERE tutor_id = ? ;" ;
            Connection connection = null;

            try {
                connection = DatabaseManager.getConnection();
                connection.setAutoCommit(false);  //Disable auto commit

                try {
                    PreparedStatement updateStudentStatement = connection.prepareStatement(updateStudentSQL);
                    PreparedStatement updateTutorStatement = connection.prepareStatement(updateTutorSQL);

                    updateStudentStatement.setString(1, newName);
                    updateStudentStatement.setInt(2,targetId);
                    updateStudentStatement.executeUpdate();

                    updateTutorStatement.setString(1,newName);
                    updateTutorStatement.setInt(2,targetId);
                    updateTutorStatement.executeUpdate();

                    connection.commit(); //All or nothing Save
                    System.out.println("User has been updated successfully!");
                } catch (SQLException e) {
                    if(connection != null){
                        connection.rollback(); //rollback the partial changes
                        System.out.println("Failed to update user!");
                    }
                    throw e;
                }
            } catch (SQLException e) {
                e.printStackTrace();

            } finally {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

    }
}
