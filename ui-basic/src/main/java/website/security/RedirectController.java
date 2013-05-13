package website.security;

import com.bsu.service.api.global.admin.dto.RoleDto;
import com.bsu.service.api.global.admin.dto.UserDto;
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
            UserDto account = (UserDto) authentication.getPrincipal();
            RoleDto role = account.getRoles();
            if (role.getName().equals("ROLE_STUDENT")) {
                setDefaultTargetUrl("/student/main.xhtml");
            }
            if (role.getName().equals("ROLE_ADMIN")) {
                setDefaultTargetUrl("/admin/main.xhtml");
            }
            if (role.getName().equals("ROLE_TEACHER")) {
                setDefaultTargetUrl("/teacher/main.xhtml");
            }
            if (role.getName().equals("ROLE_LECTOR")) {
                setDefaultTargetUrl("/lecturer/main.xhtml");
            }

            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
