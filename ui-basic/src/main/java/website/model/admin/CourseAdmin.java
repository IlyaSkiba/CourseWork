package website.model.admin;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import website.model.admin.common.AdminUserModel;

import javax.inject.Named;
import java.util.Collections;
import java.util.Set;

/**
 * @author Ilya SKiba
 * @created 16/11/12
 */
@Named
@Component
@Scope("session")
public class CourseAdmin {
    private CourseModel currentModel;

    public CourseModel getCurrentModel() {
        return currentModel;
    }

    public void setCurrentModel(CourseModel currentModel) {
        this.currentModel = currentModel;
    }

    public void create() {

    }

    public void open(String id) {

    }

    public void save() {

    }

    public Set<AdminUserModel> getPossibleUsers() {
        return Collections.emptySet();
    }
}
