package software.ulpgc.control;

import software.ulpgc.model.Title;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class SQLiteTitleWriter implements TitleWriter{
    private PreparedStatement insertedStatement;
    private final Connection connection;
    private static final String createTable = """
            CREATE TABLE IF NOT EXISTS titles (
            if TEXT PRIMARY KEY,
            type TEXT NOT NULL,
            primaryTitle TEXT NOT NULL);
            """;

    public SQLiteTitleWriter(File file) throws IOException {
        this.connection = openConnection(file);
        prepareDatabase();
    }

    private Connection openConnection(File file) throws IOException {
        try {
            return DriverManager.getConnection("jdbc:sqlite" + file.getAbsolutePath());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    private void prepareDatabase() throws IOException {
        try {
            Statement statement = connection.createStatement();
            statement.execute(createTable);
            connection.setAutoCommit(false);
            String insertSQL = "INSERT INTO titles(id, type, primaryTitle) VALUES(?,?,?)";
            insertedStatement = connection.prepareStatement(insertSQL);
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }


    @Override
    public void write(Title title) throws IOException {
        try {
            insertedStatement.setString(1, title.id());
            insertedStatement.setString(2, title.titleType().name());
            insertedStatement.setString(3, title.primaryTitle());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public void closeConnection() throws IOException {
        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
