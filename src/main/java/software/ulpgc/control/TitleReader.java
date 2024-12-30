package software.ulpgc.control;

import software.ulpgc.model.Title;

import java.io.IOException;
import java.util.Iterator;

public interface TitleReader {
    Iterator<Title> read() throws IOException;
}
