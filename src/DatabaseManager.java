import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String URL = "jdbc:postgresql://localhost:5432/cli_university_db";
    private static final String USER = "garukaadithya";
    private static final String PASSWORD = "";

    private static HikariDataSource dataSource;

    static {
        try {
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(URL);
            config.setUsername(USER);
            config.setPassword(PASSWORD);

            // Pool Sizing
            config.setMaximumPoolSize(10);        // Max 10 connections in pool
            config.setMinimumIdle(2);             // Keep 2 warm connections standby
            config.setIdleTimeout(30000);         // Drop idle connections after 30s
            config.setConnectionTimeout(20000);   // Wait max 20s for an available connection

            dataSource = new HikariDataSource(config);
            System.out.println("HikariCP Connection Pool initialized successfully.");

        } catch (Exception e) {
            System.out.println("Failed to initialize HikariCP Connection Pool.");
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closeConnection() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("Connection closed successfully.");
        }
    }
}
