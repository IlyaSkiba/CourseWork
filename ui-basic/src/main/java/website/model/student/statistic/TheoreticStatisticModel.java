package website.model.student.statistic;

import com.bsu.server.theoretic.test.service.TheoreticTestService;
import com.bsu.server.theoretic.test.student.dto.StudentResultDto;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import website.model.global.UserModel;

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
@Named("theorStatModel")
public class TheoreticStatisticModel {
    private StreamedContent chart;
    private List<StatisticTable> result;
    @Autowired
    private TheoreticTestService theoreticTestService;
    @Autowired
    private UserModel userModel;

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
        List<StudentResultDto> results = theoreticTestService.getStudentResults(userModel.getUser());
        for (StudentResultDto resultDto : results) {
            dataset.setValue(resultDto.getResult(), Integer.valueOf(1), resultDto.getTestDto().getRelatedTheme().getName());
        }
        return dataset;
    }

    public List<StatisticTable> getResult() {
        result = new ArrayList<StatisticTable>();
        List<StudentResultDto> results = theoreticTestService.getStudentResults(userModel.getUser());
        for (StudentResultDto resultDto : results) {
            StatisticTable stRes = new StatisticTable();
            stRes.setTest(resultDto.getTestDto().getRelatedTheme().getName());
            stRes.setResult(resultDto.getResult());
            result.add(stRes);
        }
        return result;
    }
}
