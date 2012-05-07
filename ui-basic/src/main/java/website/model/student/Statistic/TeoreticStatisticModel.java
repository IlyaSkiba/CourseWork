package website.model.student.Statistic;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: HomeUser
 * Date: 5.5.12
 * Time: 14.51
 * To change this template use File | Settings | File Templates.
 */
@Scope("session")
@Named("teorStatModel")
public class TeoreticStatisticModel {
    private StreamedContent chart;
    private List<StatisticTable> result;

    public void dynamicImageController() {
        JFreeChart jfreechart = ChartFactory.createLineChart("Статистика по теоретическим тестам", "Тесты", "Баллы", createDataset(), PlotOrientation.VERTICAL, false, true, false);
        File chartFile = new File("dynamichart");
        try {
            ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 600, 300);
            chart = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StreamedContent getChart() {
        dynamicImageController();
        return chart;
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(100, Integer.valueOf(1), "Тест1");
        dataset.setValue(70, Integer.valueOf(1), "Тест2");
        dataset.setValue(85, Integer.valueOf(1), "Тест3");
        dataset.setValue(90, Integer.valueOf(1), "Тест4");
        return dataset;
        //@todo: извлечь из базы результаты теоретического тестирования студента
    }

    public List<StatisticTable> getResult() {
        result = new ArrayList<StatisticTable>();
        StatisticTable stRes = new StatisticTable();
        stRes.setTest("Тест1");
        stRes.setResult(100);
        result.add(0, stRes);
        stRes = new StatisticTable();
        stRes.setTest("Тест2");
        stRes.setResult(70);
        result.add(1, stRes);
        stRes = new StatisticTable();
        stRes.setTest("Тест3");
        stRes.setResult(85);
        result.add(2, stRes);
        stRes = new StatisticTable();
        stRes.setTest("Тест4");
        stRes.setResult(90);
        result.add(3, stRes);
        return result;
    }
}
