package software.ulpgc.control;

import software.ulpgc.model.Title;

import java.io.File;
import java.util.Iterator;

public class TitleLoader {
    public TitleLoader(File source, File target) {
        TitleReader reader = new TsvTitleReader(source);
        SQLiteTitleWriter dbWriter = new SQLiteTitleWriter(target);
        Iterator<Title> titles = reader.read();
        while (titles.hasNext()) {
            dbWriter.write(titles.next());
        }
        dbWriter.closeOperation();
    }
}
