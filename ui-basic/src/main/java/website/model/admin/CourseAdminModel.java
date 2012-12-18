package website.model.admin;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Named;

/**
 * @author Ilya SKiba
 * @created 16/11/12
 */

@Named
@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class CourseAdminModel {
    private String id;
    private String name;
    private String owner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void clear() {
        setId(StringUtils.EMPTY);
        setName(StringUtils.EMPTY);
        setOwner(null);
    }
}
