package website.model.student.statistic;

import com.bsu.service.api.dto.StudentResultDto;
import com.bsu.service.api.theoretic.TheoreticTestService;
import com.google.common.collect.Lists;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import website.model.global.UserModel;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * User: HomeUser
 * Date: 5.5.12
 * Time: 14.51
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Component
@Named("theorStatModel")
public class TheoreticStatisticModel {
    private List<StatisticTable> result = Lists.newArrayList();
    @Inject
    private TheoreticTestService theoreticTestService;
    @Inject
    private UserModel userModel;
    private CartesianChartModel marksModel = null;

    public List<StatisticTable> getResult() {
        if (result.size() == 0) {
            result = new ArrayList<>();
            List<StudentResultDto> results = theoreticTestService.getStudentResults(userModel.getUser().getId());
            for (StudentResultDto resultDto : results) {
                StatisticTable stRes = new StatisticTable();
                stRes.setTest(resultDto.getTestName());
                stRes.setResult(resultDto.getResultScore());
                result.add(stRes);
            }
        }
        return result;
    }

    public CartesianChartModel getMarksModel() {
        if (marksModel == null) {
            marksModel = new CartesianChartModel();
            List<StatisticTable> results = getResult();
            ChartSeries test = new ChartSeries();
            test.setLabel("Результаты прохождения тестов");
            for (StatisticTable statisticTable : results) {
                test.set(statisticTable.getTest(), statisticTable.getResult());
            }
            marksModel.addSeries(test);
        }
        return marksModel;
    }
}
