import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionTest {

    public static void main(String[] args){

        String url = "jdbc:postgresql://localhost:5432/cli_university_db";
        String user = "garukaadithya";
        String password = "";

        try{
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Successfully connected to the PostgreSQL database!");

            connection.close();
        } catch (SQLException e){

            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}
