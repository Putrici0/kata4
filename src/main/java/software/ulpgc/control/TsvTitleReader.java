package software.ulpgc.control;

import software.ulpgc.model.Title;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class TsvTitleReader implements TitleReader{

    private final File source;
    private final TsvTitleDeserializer deserializer;

    public TsvTitleReader(File source) {
        this.source = source;
        this.deserializer = new TsvTitleDeserializer();
    }

    @Override
    public Iterator<Title> read() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(source));
        readHeaderWith(reader);
        return readTitlesWith(reader);
    }

    private void readHeaderWith(BufferedReader reader) throws IOException {
        reader.readLine();
    }

    private Iterator<Title> readTitlesWith(BufferedReader reader) throws IOException {
        return new Iterator<>() {
            String line = reader.readLine();
            @Override
            public boolean hasNext() {
                return line != null;
            }

            @Override
            public Title next() {
                try {
                    Title title = line == null ? null : titleOf(line);
                    line = reader.readLine();
                    if (line == null) {
                        reader.close();
                    }
                    return title;
                } catch (IOException e) {
                    return null;
                }
            }
        };
    }

    private Title titleOf(String line) {
        return deserializer.deserialize(line);
    }


}
