package website.model.admin.common;

import com.bsu.service.api.global.admin.dto.RoleDto;

import java.util.Set;

/**
 * @author Ilya SKiba
 * @created 16/11/12
 */
public class AdminUserModel {
    private String id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Set<RoleDto> assignedRoles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getDisplayName() {
        return firstName;
    }

    public Set<RoleDto> getAssignedRoles() {
        return assignedRoles;
    }
}
