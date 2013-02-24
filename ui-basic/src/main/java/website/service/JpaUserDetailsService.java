package website.service;

import com.bsu.service.api.global.admin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

/**
 * User: Dmitry Leontyev
 * Date: 10.01.11
 * Time: 17:26
 */
@Service
public class JpaUserDetailsService implements UserDetailsService, IChangePassword {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;

        try {
            userDetails = userService.getUserByUserName(username);
        } catch (NoResultException nre) {
            throw new UsernameNotFoundException(null);
        }

        return userDetails;
    }

    @Override
    public void changePassword(String username, String newPassword) {
        //todo: implement this
    }


}
