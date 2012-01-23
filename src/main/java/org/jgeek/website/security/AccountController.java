package org.jgeek.website.security;

import org.jgeek.website.service.IChangePassword;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * User: Dmitry Leontyev
 * Date: 26.12.10
 * Time: 22:57
 */
@Named
@Scope("request")
public class AccountController {

    @Inject
    private IChangePassword changePasswordDao;

    private String newPassword;

    public String submitChangePasswordPage() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.toString();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }

        changePasswordDao.changePassword(username, newPassword);
        SecurityContextHolder.clearContext();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Пароль успешно изменен.", null));

        return null;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
