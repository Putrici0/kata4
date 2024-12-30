package software.ulpgc.view;

import software.ulpgc.model.Histogram;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    JFreeChartHistogramDisplay display;
    SQLiteTitleReader titleReader;

    public MainFrame() throws HeadlessException {
        this.setTitle("Histogram Display");
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.display = new JFreeChartHistogramDisplay();
        add(display);
    }

    public void displayHistogram(Histogram histogram) {
        display.display(histogram);
    }

    public void setTitleReader(SQLiteTitleReader titleReader) {
        this.titleReader = titleReader;
    }
}
