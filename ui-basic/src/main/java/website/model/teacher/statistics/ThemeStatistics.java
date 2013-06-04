package website.model.teacher.statistics;

import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.dto.StudentResultDto;
import com.bsu.service.api.dto.ThemeDto;
import com.bsu.service.api.global.admin.CourseService;
import com.bsu.service.api.theoretic.ThemeService;
import com.bsu.service.api.theoretic.TheoreticTestService;
import com.google.common.collect.Lists;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import website.model.global.UserModel;
import website.model.teacher.statistics.model.ThemeModel;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * @author HomeUser
 *         Date: 3.6.13
 *         Time: 20.13
 */
@Scope("session")
@Named("teacherThemeStatitic")
public class ThemeStatistics implements Serializable {
    @Autowired
    private TheoreticTestService theoreticService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ThemeService themeService;
    @Autowired
    private UserModel userModel;
    private Integer selectedCourse;
    private Integer selectedTopic;
    private CartesianChartModel model = null;
    private List<ThemeModel> tableData;

    private List<CourseDto> courses;
    private List<ThemeDto> themes;

    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getPartialViewContext().isPartialRequest() &&
                !context.getPartialViewContext().isExecuteAll()) {
            return;
        }
        selectedCourse = null;
        selectedTopic = null;
        model = null;
        tableData = null;
    }

    public void load() {
        CourseDto courseDto = new CourseDto();
        if (courses == null || courses.isEmpty()) {
            courses = courseService.searchByOwnerId(userModel.getUser().getId());
        }
        if (selectedCourse != null) {
            courseDto.setId(selectedCourse);
            themes = themeService.getThemesForCourse(courseDto);
        }
    }

    public List<ThemeModel> getTableData() {
        synchronized (this) {
            if (tableData == null && selectedCourse != null && selectedTopic != null) {
                List<StudentResultDto> results = theoreticService.getThemeResults(selectedTopic);
                initModel(results);
            }
        }
        return tableData;
    }

    public CartesianChartModel getModel() {
        synchronized (this) {
            if (model == null && selectedCourse != null && selectedTopic != null) {
                List<StudentResultDto> results = theoreticService.getThemeResults(selectedTopic);
                initModel(results);
            }
        }
        return model;
    }

    private void initModel(List<StudentResultDto> results) {
        model = new CartesianChartModel();
        ChartSeries resultSeries = new ChartSeries("Результаты прохождения теста");
        tableData = Lists.newArrayList();
        int i = 1;
        for (StudentResultDto result : results) {
            ThemeModel themeModel = new ThemeModel(result, (i));
            resultSeries.set((i++) + "", result.getResultScore());
            tableData.add(themeModel);
        }
        model.addSeries(resultSeries);
    }

    public Integer getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(Integer selectedCourse) {
        this.selectedCourse = selectedCourse;
        setSelectedTopic(null);
    }

    public Integer getSelectedTopic() {
        return selectedTopic;
    }

    public void setSelectedTopic(Integer selectedTopic) {
        this.selectedTopic = selectedTopic;
        this.model = null;
    }

    public List<CourseDto> getCourses() {
        if (courses == null) {
            load();
        }
        return courses;
    }

    public List<ThemeDto> getThemes() {
        if (themes == null) {
            load();
        }
        return themes;
    }
}
