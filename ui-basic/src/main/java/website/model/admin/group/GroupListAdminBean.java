package website.model.admin.group;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Named;
import java.io.Serializable;

/**
 * @author HomeUser
 *         Date: 20.3.13
 *         Time: 23.47
 */

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
@Named
public class GroupListAdminBean implements Serializable {

}
