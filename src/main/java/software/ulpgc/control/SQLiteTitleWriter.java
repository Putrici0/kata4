package software.ulpgc.control;

import software.ulpgc.model.Title;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class SQLiteTitleWriter implements TitleWriter {
	private final Connection connection;
	private static final String createTable = """
            CREATE TABLE IF NOT EXISTS titles (
                id TEXT PRIMARY KEY,
                type TEXT NOT NULL,
                primaryTitle TEXT NOT NULL);
            """;
	private PreparedStatement insertStatement;

	public SQLiteTitleWriter(File file) throws IOException {
		this.connection = openConnection(file);
		prepareDatabase();
	}

	@Override
	public void write(Title title) throws IOException {
		try {
			insertStatement.setString(1, title.id());
			insertStatement.setString(2, title.titleType().name());
			insertStatement.setString(3, title.primaryTitle());
			insertStatement.executeUpdate();
		} catch (SQLException e) {
			throw new IOException("Error al agregar el título al lote.", e);
		}
    }


	private void prepareDatabase() throws IOException {
		try {
			Statement statement = connection.createStatement();
			statement.execute(createTable);  // Crear tabla si no existe
			connection.setAutoCommit(false);  // Deshabilitar auto commit para usar transacciones manuales
			String insertSQL = "INSERT INTO titles(id, type, primaryTitle) VALUES(?,?,?);";
			insertStatement = connection.prepareStatement(insertSQL);
		} catch (SQLException e) {
			throw new IOException("Error al preparar la base de datos.", e);
		}
	}

	private Connection openConnection(File file) throws IOException {
		try {
			return DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
		} catch (SQLException e) {
			throw new IOException("Error al abrir la conexión a la base de datos.", e);
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
