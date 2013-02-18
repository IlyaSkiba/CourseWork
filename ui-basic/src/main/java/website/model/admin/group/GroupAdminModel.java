package website.model.admin.group;

import com.bsu.service.api.global.admin.UserService;
import com.bsu.service.api.global.admin.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HomeUser
 *         Date: 29.1.13
 *         Time: 23.20
 */
@Named
@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class GroupAdminModel implements Serializable {
    private String name;
    private GroupUsersModel users;
    private List<UserDto> selectedUsers;
    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        users = new GroupUsersModel(userService);
        selectedUsers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroupUsersModel getUsers() {
        return users;
    }

    public List<UserDto> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(List<UserDto> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }
}
