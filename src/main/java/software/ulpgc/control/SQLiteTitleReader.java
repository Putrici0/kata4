package software.ulpgc.control;

import software.ulpgc.model.Title;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Iterator;

public class SQLiteTitleReader implements TitleReader {
	private final File dbFile;

    public SQLiteTitleReader(File dbFile) throws IOException {
		this.dbFile = dbFile;

		// Verifica que la base de datos es accesible al crear el objeto
		try (Connection connection = openConnection()) {
			try (Statement statement = connection.createStatement()) {
				statement.execute("PRAGMA busy_timeout = 5000;"); // Configura tiempo de espera
			}
		} catch (SQLException e) {
			throw new IOException("No se pudo abrir la base de datos.", e);
		}
	}

	@Override
	public Iterator<Title> read() throws IOException {


		return new Iterator<>() {
			private final Connection connection;
			private final PreparedStatement selectStatement;
			private final ResultSet resultSet;

			{
				try {
                    try {
                        connection = openConnection();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    selectStatement = prepareSelectStatement(connection);
					resultSet = executeQuery(selectStatement);
				} catch (IOException e) {
					close();
					throw new RuntimeException(e);
				}
			}

			@Override
			public boolean hasNext() {
				try {
					return resultSet != null && resultSet.next();
				} catch (SQLException e) {
					close();
					return false;
				}
			}

			@Override
			public Title next() {
				try {
					return new Title(
							resultSet.getString(1),
							Title.TitleType.valueOf(resultSet.getString(2)),
							resultSet.getString(3)
					);
				} catch (SQLException e) {
					close();
					throw new RuntimeException("Error leyendo el siguiente registro.", e);
				}
			}

			private void close() {
				try {
					if (resultSet != null) resultSet.close();
					if (selectStatement != null) selectStatement.close();
					if (connection != null) connection.close();
				} catch (SQLException ignored) {
				}
			}
		};
	}

	private Connection openConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
	}

	private PreparedStatement prepareSelectStatement(Connection connection) throws IOException {
		try {
			return connection.prepareStatement("SELECT * FROM titles");
		} catch (SQLException e) {
			throw new IOException("Error al preparar la consulta.", e);
		}
	}

	private ResultSet executeQuery(PreparedStatement selectStatement) throws IOException {
        try {
			return selectStatement.executeQuery();
		} catch (SQLException e) {
			throw new IOException("Error al ejecutar la consulta.", e);
		}
	}

}
