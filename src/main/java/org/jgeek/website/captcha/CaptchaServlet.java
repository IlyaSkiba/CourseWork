package org.jgeek.website.captcha;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * User: Dmitry Leontyev
 * Date: 19.01.11
 * Time: 22:04
 */
public class CaptchaServlet extends HttpServlet {

    public static final String CAPTCHA_STRING = "captcha_string";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SimpleCaptcha captcha = new SimpleCaptcha();
        HttpSession session = request.getSession();
        String cstring = captcha.generateCaptchaString(5);
        session.setAttribute(CAPTCHA_STRING, cstring);
        BufferedImage bimg = captcha.getCaptchaImage(cstring);
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        OutputStream out = response.getOutputStream();
        ImageIO.write(bimg, "jpeg", out);
        out.flush();
        out.close();
    }

}
