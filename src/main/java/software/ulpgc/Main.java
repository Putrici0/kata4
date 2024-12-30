package software.ulpgc;

import software.ulpgc.control.TitleLoader;
import software.ulpgc.control.TitleTypeHistogram;
import software.ulpgc.model.Histogram;
import software.ulpgc.view.MainFrame;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        Files.createFile(Path.of("title.db"));
        File tsvFile = new File("title.basics.tsv");
        File dbFile = new File("title.db");
        new TitleLoader().loadTitles(tsvFile, dbFile);
        SQLiteTitleReader titleReader = new SQLiteTitleReader(dbFile);
        Histogram histogram = new TitleTypeHistogram(new SQLiteTitleReader(dbFile));
        MainFrame mainFrame = new MainFrame();
        mainFrame.displayHistogram(histogram);
        mainFrame.setTitleReader(titleReader);
        mainFrame.setVisible(true);
    }
}
