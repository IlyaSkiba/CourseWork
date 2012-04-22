package website.model.global;

import com.bsu.server.dto.security.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import website.service.JpaUserDetailsService;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * User: HomeUser
 * Date: 25.1.12
 * Time: 18.23
 */
@Scope("session")
@Named
public class UserModel {

    @Autowired
    JpaUserDetailsService userService;
    @Autowired
    ShaPasswordEncoder passwordEncoder;

    private String userName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String oldPassword;
    private String newPassword;

    public void initializeByUsername() {
        UserAccount userDto = (UserAccount) userService.loadUserByUsername(userName);
        firstName = userDto.getFirstName();
        middleName = userDto.getMiddleName();
        lastName = userDto.getLastName();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserAccount getUser() {
        return (UserAccount) userService.loadUserByUsername(userName);
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void changePassword() {
        if (passwordEncoder.isPasswordValid(userService.loadUserByUsername(userName).getPassword(), oldPassword, null)) {

            userService.changePassword(userName, newPassword);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Пароль успешно сменен", null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Введенный пароль не верен", null));
        }
    }
}
