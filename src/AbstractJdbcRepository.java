import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractJdbcRepository <T, ID> implements Repository<T, ID> {

    protected final HikariDataSource dataSource;

    public AbstractJdbcRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
