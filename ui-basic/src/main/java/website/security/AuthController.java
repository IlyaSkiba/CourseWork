package website.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import website.model.global.UserModel;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * TODO: Считать количество попыток и после 3-й выдавать капчу.
 * <p/>
 * User: Dmitry Leontyev
 * Date: 12.12.10
 * Time: 21:45
 */

@Scope("request")
@Named("authController")
public class AuthController {
    private static final Logger LOG = Logger.getLogger(AuthController.class.getName());

    @Autowired
    private UserModel userModel;

    /**
     * Обрабатывает запрос на аутентификацию.
     *
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public String doLogin() throws IOException, ServletException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                .getRequestDispatcher("/login_check");
        dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
        FacesContext.getCurrentInstance().responseComplete();
        userModel.initializeByUsername();
        return null;
    }

    /**
     * Выполняет завершение сеанса работы пользователя с системой.
     *
     * @return
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    public String doLogout() throws IOException, ServletException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                .getRequestDispatcher("/j_spring_security_logout");
        dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
        FacesContext.getCurrentInstance().responseComplete();
        return null;
    }
}