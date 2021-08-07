package assignment.db;

import com.gojek.ApplicationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbManager {

    private static final String JDBC_BASE_URL = "jdbc:postgresql://";

    private final ApplicationConfiguration appConfig;
    private final Logger logger = LoggerFactory.getLogger(DbManager.class);

    public DbManager(ApplicationConfiguration appConfig) {
        this.appConfig = appConfig;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            String dbHost = appConfig.getValueAsString("DB_HOST");
            String dbPort = appConfig.getValueAsString("DB_PORT");
            String dbName = appConfig.getValueAsString("DB_NAME");
            String dbUsername = appConfig.getValueAsString("DB_USERNAME");
            String dbPassword = appConfig.getValueAsString("DB_PASSWORD");
            String url = constructUrl(dbHost, dbPort, dbName);
            connection = DriverManager.getConnection(url, dbUsername, dbPassword);
        } catch (SQLException e) {
            logger.error("Got db connection exception", e);
        }
        return connection;
    }

    private String constructUrl(String dbHost, String dbPort, String dbName) {
        return JDBC_BASE_URL + dbHost + ":" + dbPort + "/" + dbName;
    }
}
