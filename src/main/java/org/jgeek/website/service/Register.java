package org.jgeek.website.service;

import org.jgeek.website.captcha.validator.Captcha;
import org.jgeek.website.model.security.UserAccount;
import org.jgeek.website.model.security.UserAuthority;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

/**
 * TODO: Сделать капчу.
 * TODO: Обрабатывать исключение при нарушении уникальности.
 * <p/>
 * User: Dmitry Leontyev
 * Date: 11.12.10
 * Time: 23:25
 */
@Scope("request")
@Named("registerService")
public class Register {
    private static final Logger LOG = Logger.getLogger(Register.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Inject
    private PasswordEncoder passwordEncoder;

//    @Inject
//    private MailService mailService;

    private UserAccount account;

    @Captcha
    private String captchaValue;

    @PostConstruct
    private void retrieveTempAccount() {
        UserAccount flashAccount = (UserAccount) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("registerAccount");
        if (flashAccount != null) {
            account = (UserAccount) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("registerAccount");
            LOG.fine("UserAccount object already create. Use it.");
        } else {
            account = new UserAccount();
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("registerAccount", account);
            LOG.fine("UserAccount object not found. Create new UserAccount object.");
        }
    }


    @Transactional
    public String doRegister() {
        UserAuthority userRole = new UserAuthority();
        userRole.setAuthority(UserAuthority.ROLE_USER);

        // todo: Вместо null добавлять Salt
        account.setPassword(passwordEncoder.encodePassword(account.getPassword(), null));
        account.getUserAuthorities().add(userRole);

        em.persist(account);

//        try {
//            mailService.postMail(account);
//        } catch (MessagingException me) {
//            LOG.log(Level.SEVERE, "Ошибка при отправлении почты.", me);
//        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Регистрация прошла успешно. Теперь Вы можете войти в систему как зарегистрированный пользователь",
                null));

        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        return "login?faces-redirect=true";
    }

    public UserAccount getAccount() {
        return account;
    }

    public void setAccount(UserAccount account) {
        this.account = account;
    }

    public String getCaptchaValue() {
        return captchaValue;
    }

    public void setCaptchaValue(String captchaValue) {
        this.captchaValue = captchaValue;
    }
}