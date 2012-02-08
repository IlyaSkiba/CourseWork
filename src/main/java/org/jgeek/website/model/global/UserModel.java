package org.jgeek.website.model.global;

import org.jgeek.website.model.security.UserAccount;
import org.jgeek.website.service.JpaUserDetailsService;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by IntelliJ IDEA.
 * User: HomeUser
 * Date: 25.1.12
 * Time: 18.23
 * To change this template use File | Settings | File Templates.
 */
@Scope("session")
@Named
public class UserModel {

    @Inject
    JpaUserDetailsService userService;
    @Inject
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
