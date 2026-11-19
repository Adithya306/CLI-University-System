import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {


    private static final Path CONFIG_PATH = Path.of("db.properties");

    private static HikariDataSource dataSource;

    static {
        try {

            Properties props = loadProperties();

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            if (url == null || url.isBlank() || user == null || user.isBlank() || password == null) {
                throw new IllegalStateException("Invalid db.properties: 'db.url', 'db.user', and 'db.password' keys are required.");                                                                                    // <-- NEW
            }

            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(url.trim());
            config.setUsername(user.trim());
            config.setPassword(password);

            // Pool Sizing
            config.setMaximumPoolSize(10);        // Max 10 connections in pool
            config.setMinimumIdle(2);             // Keep 2 warm connections standby
            config.setIdleTimeout(30000);         // Drop idle connections after 30s
            config.setConnectionTimeout(20000);   // Wait max 20s for an available connection

            dataSource = new HikariDataSource(config);
            System.out.println("HikariCP Connection Pool initialized successfully.");

        } catch (Exception e) {
            System.err.println("Failed to initialize HikariCP Connection Pool: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    private static Properties loadProperties() throws IOException {
        if (!Files.exists(CONFIG_PATH) || !Files.isRegularFile(CONFIG_PATH)) {
            throw new IOException(
                    "Configuration file 'db.properties' not found. Copy 'db.properties.example' to 'db.properties'.");
        }

        Properties props = new Properties();
        try (InputStream input = Files.newInputStream(CONFIG_PATH)) {
            props.load(input);
        }
        return props;
    }

    // Exposes the initialized HikariCP connection pool (not a single connection)
    // so Main.java can pass it into repository constructors (Dependency Injection).
    public static HikariDataSource getDataSource() {
        return dataSource;
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

    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
