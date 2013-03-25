package website.model.admin.user;

import com.bsu.service.api.global.admin.UserService;
import com.bsu.service.api.global.admin.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Named;

/**
 * @author HomeUser
 *         Date: 20.12.12
 *         Time: 21.21
 */
@Scope(WebApplicationContext.SCOPE_SESSION)
@Named
public class UserCardModel {
    private UserDto user;
    private String roleId;
    @Autowired
    private UserService userService;

    public void createUser() {
        user = new UserDto();
    }

    public void initUser(Integer userId) {
        user = userService.getById(userId);
    }

    public UserDto getUser() {
        if (user == null) {
            user = new UserDto();
        }
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
        if (user.getRoles() !=null){
            setRoleId(user.getRoles().getId());
        }
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
