/* Copyright 2006 Sun Microsystems, Inc. All rights reserved. You may not modify, use, reproduce, or distribute this software except in compliance with the terms of the License at: http://developer.sun.com/berkeley_license.html
$Id: SimpleCaptcha.java,v 1.13 2007/01/11 02:44:46 inder Exp $ */

package org.jgeek.website.captcha;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.util.Random;

public class SimpleCaptcha {

    private Random rd = null;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 60;
    private Color background = new Color(Integer.parseInt("c0c0c0", 16));

    protected void drawMessage(Graphics g, String message) {
        g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
        g.setColor(Color.GRAY);
        int len = message.length();
        int wgap = WIDTH / len;
        int startX = 10;
        int startY = 20;
        for (int i = 0; i < len; i++) {
            g.drawString(message.substring(i, i + 1), startX + (wgap * i), startY + rd.nextInt(40));
        }
    }

    protected void drawRandomLine(Graphics g, int count) {
        while (count > 0) {
            drawRandomLine(g);
            count--;
        }
    }

    protected void drawRandomLine(Graphics g) {
        int x1 = rd.nextInt(200);
        int y1 = rd.nextInt(60);
        int x2 = rd.nextInt(200);
        int y2 = rd.nextInt(60);
        g.drawLine(x1, y1, x2, y2);
    }

    /**
     * Creates a new instance of SimpleCaptcha
     */
    public SimpleCaptcha() {
        this.rd = new Random();
    }

    public String generateCaptchaString(int count) {
        RandomString rs = new RandomString();
        return rs.getString(count, "IiOo0");
    }

    public BufferedImage getCaptchaImage(String message) {
        return getCaptchaImage(message, WIDTH, HEIGHT);
    }

    public BufferedImage getCaptchaImage(String message, int w, int h) {
        BufferedImage bufferImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics g = null;
        Graphics g2 = null;
        BufferedImage lastBimg = null;
        try {
            g = bufferImg.getGraphics();
            g.setColor(background);
            g.fillRect(0, 0, w, h);
            g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
            g.setColor(Color.GRAY);
            drawRandomLine(g, 16);
            drawMessage(g, message);
            //g.drawString(message, 15, 40);

            ImageProducer source = bufferImg.getSource();
            ImageFilter filter = new BlueFilter();
            ImageProducer producer = new FilteredImageSource(source, filter);
            Image filteredImg = Toolkit.getDefaultToolkit().createImage(producer);

            lastBimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            g2 = lastBimg.getGraphics();
            g2.drawImage(filteredImg, 0, 0, null);
        } finally {
            if (g != null) g.dispose();
            if (g2 != null) g2.dispose();
        }
        return lastBimg;
    }
}
