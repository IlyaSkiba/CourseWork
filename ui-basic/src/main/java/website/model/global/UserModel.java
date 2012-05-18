package website.model.global;

import com.bsu.server.dto.security.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.inject.Named;
import java.io.Serializable;

/**
 * User: HomeUser
 * Date: 25.1.12
 * Time: 18.23
 */
@Scope("session")
@Named
public class UserModel implements Serializable {

    @Autowired
    private UserDetailsService userService;
    @Autowired
    private ShaPasswordEncoder passwordEncoder;

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
    }
}
