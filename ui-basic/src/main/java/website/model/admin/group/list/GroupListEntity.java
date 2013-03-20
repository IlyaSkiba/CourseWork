package website.model.admin.group.list;

import java.io.Serializable;
import java.util.List;

/**
 * @author HomeUser
 *         Date: 20.3.13
 *         Time: 23.49
 */
public class GroupListEntity implements Serializable {
    private Integer id;
    private String name;
    private List<String> courses;
    private String displayCourses;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public String getDisplayCourses() {
        return displayCourses;
    }

    public void setDisplayCourses(String displayCourses) {
        this.displayCourses = displayCourses;
    }
}
