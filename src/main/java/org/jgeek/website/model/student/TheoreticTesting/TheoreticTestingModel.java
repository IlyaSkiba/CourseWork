package org.jgeek.website.model.student.TheoreticTesting;

import org.jgeek.website.model.student.StudentMainModel;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: HomeUser
 * Date: 24.1.12
 * Time: 18.54
 */

@Scope("session")
@Named("theoretic")
public class TheoreticTestingModel {
    private static final String TEMPLATE_URL = "teoretic_main.xhtml";
    @Inject
    private StudentMainModel studentMainModel;
    private List<String> courses = Collections.emptyList();
    private Map<String, List<String>> topics = new HashMap<String, List<String>>();
    private String selectedCourse;
    private String selectedTopic;

    //@TODO: переделать под сервисы
    public void load() {
        courses = Arrays.asList("Course1", "Course2", "Course3");
        topics.clear();
        for (String course : courses) {
            topics.put(course, Arrays.asList(course + "->Topic1", course + "->Topic2"));
        }
    }

    public List<String> getCourses() {
        load();
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public List<String> getTopics() {
        load();
        return topics.get(selectedCourse);
    }

    public void setSelectedCourse(String selectedCourse) {
        this.selectedCourse = selectedCourse;
        selectedTopic = null;
    }

    public String getSelectedCourse() {
        return selectedCourse;
    }

    public String getSelectedTopic() {
        return selectedTopic;
    }

    public void setSelectedTopic(String selectedTopic) {
        this.selectedTopic = selectedTopic;
    }
}

