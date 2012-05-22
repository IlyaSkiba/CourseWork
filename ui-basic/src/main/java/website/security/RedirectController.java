package website.security;

import com.bsu.server.dto.security.UserAccount;
import com.bsu.server.dto.security.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ilya Skiba
 *         * Date: 22.4.12
 *         Time: 15.25
 */
@Service
public class RedirectController extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        if (authentication != null) {
            UserAccount account = (UserAccount) authentication.getPrincipal();
            for (UserRole role : account.getUserRoles()) {
                if (role.getRoleName().equals("ROLE_STUDENT")) {
                    setDefaultTargetUrl("/student/main.xhtml");
                }
                if (role.getRoleName().equals("ROLE_ADMIN")) {
                    setDefaultTargetUrl("/admin/main.xhtml");
                }
                if (role.getRoleName().equals("ROLE_TEACHER")) {
                    setDefaultTargetUrl("/teacher/main.xhtml");
                }
                if (role.getRoleName().equals("ROLE_LECTOR")) {
                    setDefaultTargetUrl("/lecturer/main.xhtml");
                }

            }

            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
