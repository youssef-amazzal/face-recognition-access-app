package org.glsid.facerecognitionaccessapp.infrastructure.db.sqlite;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteConnection {
    private static final Logger LOGGER = Logger.getLogger(SQLiteConnection.class.getName());
    private Connection connection;

    public SQLiteConnection() {
        try {
            String appDataPath = getAppDataPath();
            String dbPath = getDatabasePath();
            copyDatabaseIfNotExists(dbPath, appDataPath + "/apartment.sqlite");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + appDataPath + "/apartment.sqlite");
        } catch (SQLException | IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize SQLite connection", e);
        }
    }

    private String getAppDataPath() {
        String appDataPath = System.getenv("APPDATA");
        if (appDataPath == null) {
            throw new IllegalStateException("APPDATA environment variable is not set.");
        }
        return appDataPath + "/FaceLockApp/db";
    }

    private String getDatabasePath() {
        URL resource = getClass().getResource("/org/glsid/facerecognitionaccessapp/db/apartment.sqlite");
        if (resource == null) {
            throw new IllegalStateException("Database resource not found.");
        }
        return resource.getPath().substring(1).replace('/', '\\');
    }

    private void copyDatabaseIfNotExists(String sourcePath, String targetPath) throws IOException {
        File targetFile = new File(targetPath);
        if (!targetFile.exists()) {
            Files.createDirectories(Paths.get(targetFile.getParent()));
            Files.copy(Path.of(sourcePath), Paths.get(targetPath));
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try (Connection conn = connection) {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Failed to close SQLite connection", e);
            }
        }
    }
}