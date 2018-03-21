package com.guinea.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Hex;
import org.joda.time.DateTime;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * Created by shiky on 2015/12/8.
 */
public class EndecryptUtils {
    public final static String DEFALUTPWD ="888888";

    public static String createKey(){
        String key ="";
        List<String> keyls = Arrays.asList("W", "m", "z","Ui","hb","sm","sX","Op","TX","WB","oo","vn","vb");
        DateTime dateTime = new DateTime();
        key = keyls.get(dateTime.getMonthOfYear())+dateTime.getDayOfWeek()+dateTime.getDayOfMonth();
        return key;
    }

    /**
     * base64进制加密
     *
     * @param password
     * @return
     */
    public static String encrytBase64(String password) {
        String encoded = Base64
                .getEncoder()
                .encodeToString(password.getBytes(StandardCharsets.UTF_8));
        return encoded;
    }

    /**
     * base64进制解密
     *
     * @return
     */
    public static String decryptBase64(String encoded) {
        String decoded = new String(
                Base64.getDecoder().decode(encoded),
                StandardCharsets.UTF_8);
        return decoded;
    }

    /**
     * 16进制加密
     *
     * @param password
     * @return
     */
    public static String encrytHex(String password) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(password), "不能为空");
        byte[] bytes = password.getBytes();
        return Hex.encodeToString(bytes);
    }

    /**
     * 16进制解密
     *
     * @param cipherText
     * @return
     */
    public static String decryptHex(String cipherText) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(cipherText), "不能为空");
        return new String(Hex.decode(cipherText));
    }

    /***
     * 加密
     *
     * @return
     */
    public static String encrytm(String pwd, String salt) {
        String ens = encrytHex(salt), enp = encrytBase64(pwd + ens), enm = encrytBase64(enp + salt);
        return enm;
    }

    /***
     * 解密
     *
     * @return
     */
    public static String encryptor(String epwd, String salt) {
        String temp = decryptBase64(epwd), ens = encrytHex(salt),
                pwd1 = StringUtils.removeEnd(temp, salt),
                pwd2 = decryptBase64(pwd1),
                pwd = StringUtils.removeEnd(pwd2, ens);
        return pwd;
    }


    public static void main(String[] args) {
//        String stre = EndecryptUtils.encrytm("`783]*o,p;KT_-+", "skyv23");
//        System.out.println(stre);
//        System.out.println(stre.length());

        String pwd = EndecryptUtils.encryptor("TVRNNE9USTBPVE15TURnM05qWXlNelF6TWpNMHZiNDI0", "vb424");
        System.out.println(pwd);
//        System.out.println(pwd.length());

//        System.out.println(createKey());

    }
}
