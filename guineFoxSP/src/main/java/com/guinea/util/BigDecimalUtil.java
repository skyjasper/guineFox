package com.guinea.util;

import java.math.BigDecimal;

/***
 *
 * @author shiky
 * @describle: bigdecimaiUtil
 * @dateTime: 2013-1-8
 */
public class BigDecimalUtil {
    private static BigDecimal zero = BigDecimal.ZERO;

    /**
     * 提供精确的加法运算。
     *
     * @param b1
     *            被加数
     * @param b2
     *            加数
     * @return 两个参数的和
     */

    public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
        b1 = b1 == null ? BigDecimal.ZERO : b1;
        b2 = b2 == null ? BigDecimal.ZERO : b2;
        return b1.add(b2);
    }

    public static double getDoubleByBigDecimal(BigDecimal b) {
        if (null == b) {
            return 0;
        } else {
            return b.doubleValue();
        }
    }

    /**
     * 提供精确的减法运算。b1-b2
     *
     * @param b1
     *            被减数
     * @param b2
     *            减数
     * @return 两个参数的差
     */

    public static BigDecimal sub(BigDecimal b1, BigDecimal b2) {
        b1 = b1 == null ? BigDecimal.ZERO : b1;
        b2 = b2 == null ? BigDecimal.ZERO : b2;
        return b1.subtract(b2);
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1
     *            被乘数
     * @param v2
     *            乘数
     * @return 两个参数的积
     */

    public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
        v1 = v1 == null ? BigDecimal.ZERO : v1;
        v2 = v2 == null ? BigDecimal.ZERO : v2;
        return v1.multiply(v2);
    }

    /** v1/v2
     * @param v1 除数
     *
     * @param v2 被除数
     *
     * @return 两个参数的商
     */

    public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
        v1 = v1 == null ? BigDecimal.ZERO : v1;
        v2 = v2 == null ? BigDecimal.ZERO : v2;
        if (v2.equals(BigDecimal.ZERO)) {
            return BigDecimal.ONE;
        }
        return v1.divide(v2,8, BigDecimal.ROUND_HALF_EVEN);
    }

    /** v1/v2
     * @param v1 除数
     *
     * @param v2 被除数
     *
     * @return 两个参数的商 12位小数
     */

    public static BigDecimal div12(BigDecimal v1, BigDecimal v2) {
        v1 = v1 == null ? BigDecimal.ZERO : v1;
        v2 = v2 == null ? BigDecimal.ZERO : v2;
        if (v2.equals(BigDecimal.ZERO)) {
            return BigDecimal.ONE;
        }
        return v1.divide(v2,8, BigDecimal.ROUND_HALF_EVEN);
    }

    public static BigDecimal negate(BigDecimal big){
        big = big == null ? BigDecimal.ZERO : big;
        big.negate();
        return big;
    }

    /***
     * 大小比较:大于
     *
     * @param b1
     *            较大数
     * @param b2
     *            较小数
     * @return b1>b2 true
     */
    public static boolean greater(BigDecimal b1, BigDecimal b2) {
        boolean flag = false;
        b1 = b1 == null ? BigDecimal.ZERO : b1;
        b2 = b2 == null ? BigDecimal.ZERO : b2;
        int i = b1.compareTo(b2);
        if (i == 1) {
            flag = true;
        }
        return flag;
    }

    /***
     * 大小比较：小于
     *
     * @param b1
     *            较小数
     * @param b2
     *            较大数
     * @return b1<b2 true
	 */
    public static boolean less(BigDecimal b1, BigDecimal b2) {
        boolean flag = false;
        b1 = b1 == null ? BigDecimal.ZERO : b1;
        b2 = b2 == null ? BigDecimal.ZERO : b2;
        int i = b1.compareTo(b2);
        if (i == -1) {
            flag = true;
        }
        return flag;
    }

    /***
     * 大小比较：等于
     *
     * @param b1
     * @param b2
     * @return b1==b2 true
     */
    public static boolean equal(BigDecimal b1, BigDecimal b2) {
        boolean flag = false;
        b1 = b1 == null ? BigDecimal.ZERO : b1;
        b2 = b2 == null ? BigDecimal.ZERO : b2;
        int i = b1.compareTo(b2);
        if (i == 0) {
            flag = true;
        }
        return flag;
    }

    /****
     *
     * @param target
     * @return 大于零返回 true
     */
    public static boolean moreThanZero(BigDecimal target) {
        if(target==null){
            return false;
        }
        return target.compareTo(zero) > 0 ? true : false;
    }

    /***
     * 设置精度
     * @param b
     *            原始值
     * @param accuracy
     *            精度
     * @return
     */
    public static BigDecimal accuracySet(BigDecimal b, int accuracy) {
        BigDecimal temp = BigDecimal.ZERO;
        if (null != b) {
            try {
                temp = b.setScale(accuracy, BigDecimal.ROUND_HALF_UP);
            } catch (Exception e) {
                temp = b;//出现异常保存原值不变
            }
        }
        return temp;
    }

    /***
     * 返回绝对值
     * @param b
     * @return
     */
    public static BigDecimal abs(BigDecimal b){
        BigDecimal temp = BigDecimal.ZERO;
        if(null!=b){
            try {
                temp=b.abs();
            } catch (Exception e) {
                temp = BigDecimal.ZERO;
            }
        }
        return temp;
    }
}
