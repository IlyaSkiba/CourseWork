package org.jgeek.website.service;

import org.jgeek.website.model.security.UserAccount;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * TODO: Вынести все настройки, такие как SMTP-сервер, логин и пароль для него, порты и т.д. в админку и хранить в базе.
 * <p/>
 * TODO: Сделать HTML-шаблоны сообщений.
 * <p/>
 * User: Dmitry Leontyev
 * Date: 12.12.10
 * Time: 18:41
 */
@Scope("request")
@Named("mailService")
public class MailService {

    private final static String EMAIL_FROM_ADDRESS = "email@here.org";

    private final static String EMAIL_ENCODING = "UTF-8";

    private final static String SMTP_HOST = "smtp.mail.org";

    /**
     * Отправляет почтовое сообщение.
     *
     * @param account Учетная запись получателя.
     * @throws MessagingException Ошибка отправки почтового сообщения.
     */
    public void postMail(UserAccount account) throws MessagingException {
        Session session = getSession();

        Message message = new MimeMessage(session);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(account.getEmail()));
        message.addFrom(new InternetAddress[]{new InternetAddress(EMAIL_FROM_ADDRESS)});
        message.setSentDate(new Date());

        // Текст сообщения
        StringBuilder messageBody = new StringBuilder();
        messageBody.append("Здравствуйте, " + account.getUsername() + "! Вы зарегистрировались на нашем сайте.");
        messageBody.append("\n");
        messageBody.append("Ваш логин для входа в систему: " + account.getUsername());
        messageBody.append("Если Вы забудете пароль, то можете запросить его восстановление на сайте.");
        messageBody.append("С наилучшими пожеланиями, МЫ.");

        // Заголовок сообщения
        message.setSubject("Регистрация на сайте");

        // Формируем MIME-сообщение
        MimeBodyPart messagePart = new MimeBodyPart();
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(messagePart);

        messagePart.setText(messageBody.toString(), EMAIL_ENCODING);

        messagePart.setHeader("Content-Type", "text/plain; charset=" + EMAIL_ENCODING);
        messagePart.setHeader("Content-Transfer-Encoding", "quoted-printable");
        message.setContent(multipart);

        // Подключение к SMTP и отправка письма
        Transport transport = session.getTransport("smtp");
        transport.connect();
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    /**
     * Метод создает SMTP-сессию и устанавливает все необходимые настройки для отправки писем.
     *
     * @return SMTP-сессия.
     */
    private Session getSession() {
        SMTPAuthenticator authenticator = new SMTPAuthenticator();

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.host", SMTP_HOST);
        properties.setProperty("mail.smtp.port", "25");

        return Session.getInstance(properties, authenticator);
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        private PasswordAuthentication authentication;

        private final static String SMTP_SERVER_LOGIN = "login_here";

        private final static String SMTP_SERVER_PASSWORD = "password_here";

        public SMTPAuthenticator() {
            authentication = new PasswordAuthentication(SMTP_SERVER_LOGIN, SMTP_SERVER_PASSWORD);
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return authentication;
        }
    }
}