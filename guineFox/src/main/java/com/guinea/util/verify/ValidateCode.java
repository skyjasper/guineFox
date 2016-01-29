package com.guinea.util.verify;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.collect.Maps;
import com.guinea.cache.SysCache;
import com.guinea.util.CommonToolsUtil;
import com.guinea.util.file.FileContents;

/**
 * Created by shiky on 2015/12/7.
 */
public class ValidateCode implements Serializable {
    private static final long serialVersionUID = 3996779652392585719L;

    public static final String RANDOMCODEKEY = "verify_sky";

    private Random random = new Random();

    private static Map<String, String> validataMap = Maps.newHashMap(), valicodeMap = Maps.newHashMap();

    static {
        initValiData();
    }

    // 随机产生的字符串

    /****
     * 图片宽度，高度，干扰线大小，字符数量
     */
    private int width = 80, height = 26, lineSize = 40;

    /*
     * 获得字体
     */
    private Font getFont() {
        return new Font("Fixedsys", Font.CENTER_BASELINE, 18);
    }

    /*
     * 获得颜色
     */
    private Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc - 16), g = fc + random.nextInt(bc - fc - 14), b = fc + random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }

    /**
     * 生成随机图片
     */
    public void getRandcode(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
        g.setColor(getRandColor(110, 133));
        // 绘制干扰线
        for (int i = 0; i <= lineSize; i++) {
            drowLine(g);
        }
        // 绘制随机字符
        String randomString = drowString(g);
        session.removeAttribute(RANDOMCODEKEY);
        session.setAttribute(RANDOMCODEKEY, randomString);
        g.dispose();
        try {
            ImageIO.setUseCache(true);
            ImageIO.setCacheDirectory(CommonToolsUtil.checkFileDir(FileContents.CATCHPATH));
            ImageIO.write(image, "JPEG", response.getOutputStream());// 将内存中的图片通过流动形式输出到客户端
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initValiData() {
        validataMap.put("9+9=", "18");
        validataMap.put("7*7=", "49");
        validataMap.put("3*2=", "6");
        validataMap.put("5+3=", "8");
        validataMap.put("107+1=", "108");
        validataMap.put("2+1=", "3");
        validataMap.put("6*6=", "36");
        validataMap.put("6+6=", "12");
        validataMap.put("6+2=", "8");
        validataMap.put("8*6=", "48");
        validataMap.put("8-6=", "2");
        validataMap.put("8+6=", "14");
        validataMap.put("5+2=", "7");
        validataMap.put("8+8=", "16");
        validataMap.put("8*8=", "64");
        validataMap.put("8-8=", "0");
        validataMap.put("100+1=", "101");
        validataMap.put("8-3=", "5");
        validataMap.put("8-2=", "6");
        validataMap.put("3-1=", "2");
        validataMap.put("1*1=", "1");
        validataMap.put("16+1=", "17");
        validataMap.put("2+1=", "3");
        validataMap.put("22-1=", "21");
        validataMap.put("7+7=", "14");
        validataMap.put("3*3=", "9");
        SysCache.validataMap.putAll(validataMap);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (Map.Entry<String, String> tempMap : validataMap.entrySet()) {
            if (null != tempMap) {
                valicodeMap.put(atomicInteger.toString(), tempMap.getKey());
                atomicInteger.addAndGet(1);
            }
        }
    }

    /*
     * 绘制字符串
     */
    private String drowString(Graphics g) {
        String randomString = "";
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
        int ramdomi = random.nextInt(validataMap.size());
        randomString = valicodeMap.get(ramdomi + "");
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(randomString, 13 * 2, 16);
        return randomString;
    }

    /*
     * 绘制干扰线
     */
    private void drowLine(Graphics g) {
        int x = random.nextInt(width), y = random.nextInt(height), xl = random.nextInt(13), yl = random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }
}
