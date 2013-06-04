package website.model.teacher.statistics;

import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.dto.StudentResultDto;
import com.bsu.service.api.global.admin.CourseGroupService;
import com.bsu.service.api.global.admin.CourseService;
import com.bsu.service.api.global.admin.dto.UserDto;
import com.bsu.service.api.theoretic.TheoreticTestService;
import com.google.common.collect.Lists;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;
import website.model.global.UserModel;
import website.model.student.statistic.StatisticTable;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * @author HomeUser
 *         Date: 4.6.13
 *         Time: 20.28
 */
@Named("teacherStudentStatistics")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class StudentStatistics implements Serializable {
    @Autowired
    private TheoreticTestService theoreticService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseGroupService groupService;
    @Autowired
    private UserModel userModel;
    private Integer selectedCourse;
    private Integer selectedUser;
    private CartesianChartModel model = null;
    private List<StatisticTable> tableData;

    private List<CourseDto> courses;
    private List<UserDto> users;

    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getPartialViewContext().isPartialRequest() &&
                !context.getPartialViewContext().isExecuteAll()) {
            return;
        }
        selectedCourse = null;
        selectedUser = null;
        model = null;
        tableData = null;
    }

    private void load() {
        if (courses == null || courses.isEmpty()) {
            courses = courseService.searchByOwnerId(userModel.getUser().getId());
        }
        if (selectedCourse != null) {
            CourseDto courseDto = new CourseDto();
            courseDto.setId(selectedCourse);
            users = groupService.getUsersForCourse(courseDto);
        }
    }

    public List<StatisticTable> getTableData() {
        synchronized (this) {
            if (tableData == null && selectedCourse != null && selectedUser != null) {
                List<StudentResultDto> results = theoreticService.getStudentResults(selectedUser);
                initModel(results);
            }
        }
        return tableData;
    }

    public CartesianChartModel getModel() {
        synchronized (this) {
            if (model == null && selectedCourse != null && selectedUser != null) {
                List<StudentResultDto> results = theoreticService.getStudentResults(selectedUser);
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
            StatisticTable themeModel = new StatisticTable();
            themeModel.setResult(result.getResultScore());
            themeModel.setTest(result.getTestName());
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
        setSelectedUser(null);
    }

    public Integer getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Integer selectedUser) {
        this.selectedUser = selectedUser;
        this.model = null;
    }

    public List<CourseDto> getCourses() {
        if (courses == null) {
            load();
        }
        return courses;
    }

    public List<UserDto> getUsers() {
        if (users == null) {
            load();
        }
        return users;
    }
}
