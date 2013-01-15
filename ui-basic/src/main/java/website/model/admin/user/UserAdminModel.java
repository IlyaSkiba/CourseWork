package website.model.admin.user;

import com.bsu.service.api.global.admin.UserService;
import com.bsu.service.api.global.admin.dto.RoleDto;
import com.bsu.service.api.global.admin.dto.UserDto;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;
import website.model.common.ServletUtils;

import javax.annotation.Nullable;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author HomeUser
 *         Date: 20.12.12
 *         Time: 20.55
 */
@Named
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserAdminModel {
    @Autowired
    private UserService userService;
    @Autowired
    private UserCardModel userModel;
    private List<UserDto> userList;
    private Integer userId;

    public List<UserDto> getUsers() {
        if (userList == null) {
            userList = new ArrayList<>(userService.getUsers());
        }
        return userList;
    }

    public void create() throws IOException {
        if (StringUtils.isNotEmpty(userModel.getRoleId())) {
            Collection<RoleDto> roleCollection = Collections2.filter(getAvailableRoles(), new Predicate<RoleDto>() {
                @Override
                public boolean apply(@Nullable RoleDto input) {
                    return input != null && StringUtils.equals(input.getId(), userModel.getRoleId());
                }
            });
            if (!roleCollection.isEmpty()) {
                userModel.getUser().setRoles(roleCollection.iterator().next());
            }
        }
        userService.create(userModel.getUser());
        userList =  new ArrayList<>(userService.getUsers());
        FacesContext.getCurrentInstance().getExternalContext().redirect(
                ServletUtils.buildPath("/admin/user/user_list.xhtml"));
    }

    public void update() {
        if (StringUtils.isNotEmpty(userModel.getRoleId())) {
            Collection<RoleDto> roleCollection = Collections2.filter(getAvailableRoles(), new Predicate<RoleDto>() {
                @Override
                public boolean apply(@Nullable RoleDto input) {
                    return input != null && StringUtils.equals(input.getId(), userModel.getRoleId());
                }
            });
            if (!roleCollection.isEmpty()) {
                userModel.getUser().setRoles(roleCollection.iterator().next());
            }
        }
        userService.update(userModel.getUser());
        userList =  new ArrayList<>(userService.getUsers());
    }

    public void delete() {
        userService.delete(userId);
        userList =  new ArrayList<>(userService.getUsers());
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String viewRoles(UserDto user) {
        RoleDto roles = user.getRoles();
        if (roles == null) {
            roles = new RoleDto();
        }

        return roles.getName();
    }

    public List<RoleDto> getAvailableRoles() {
        return userService.getRoles();
    }
}
