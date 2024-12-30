package software.ulpgc.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import software.ulpgc.model.Histogram;

import javax.swing.*;

public class JFreeChartHistogramDisplay extends JPanel implements HistogramDisplay {
    @Override
    public void display(Histogram histogram) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String key : histogram.keys())
            dataset.addValue(histogram.valueOf(key), "Frequency", key);
        JFreeChart barchart = ChartFactory.createBarChart(
                histogram.title(),
                "Categories",
                "Fequency",
                dataset
        );
        ChartPanel comp = new ChartPanel(barchart);
        comp.setSize(1000, 800);
        add(comp);
    }
}
