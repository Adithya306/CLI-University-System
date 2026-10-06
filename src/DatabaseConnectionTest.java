public class DatabaseConnectionTest {

    public static void main(String[] args){

        String url = "jdbc:postgresql://localhost:5432/cli_university_db";
        String user = "garukaadithya";
        String password = "";

        try{
            java.sql.Connection connection = java.sql.DriverManager.getConnection(url, user, password);
            System.out.println("Successfully connected to the PostgreSQL database!");

            connection.close();
        } catch (java.sql.SQLException e){

            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}
