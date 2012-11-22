package website.model.teacher;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Named;

/**
 * User: HomeUser
 * Date: 23.4.12
 * Time: 11.45
 */
@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
@Named
public class TeacherMainModel {
}
