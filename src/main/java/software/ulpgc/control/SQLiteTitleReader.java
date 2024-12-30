package software.ulpgc.control;

import software.ulpgc.model.Title;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Iterator;

public class SQLiteTitleReader implements TitleReader{
    private final PreparedStatement selectedStatement;
    private final Connection connection;

    public SQLiteTitleReader(File dbFile) throws IOException {
        try {
            this.connection = openConnection(dbFile);
            this.selectedStatement = connection.prepareStatement("SELECT * FROM titles");
            selectedStatement.execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    private Connection openConnection(File dbFile) throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite" + dbFile.getAbsolutePath());
    }

    @Override
    public Iterator<Title> read() throws IOException {
        return new Iterator<>() {

            final ResultSet resultSet = executeQuery();
            @Override
            public boolean hasNext() {
                try {
                    return resultSet.next();
                } catch (SQLException e) {
                    return false;
                }
            }

            @Override
            public Title next() {
                try {
                    return new Title(resultSet.getString(1),
                            Title.TitleType.valueOf(resultSet.getString(2)),
                            resultSet.getString(3)
                            );
                } catch (SQLException e) { return null;}
            }
        };

    }

    private ResultSet executeQuery() throws IOException {
        try {
            return selectedStatement.executeQuery();
        } catch (SQLException e) {throw  new IOException(e);}
    }
}
