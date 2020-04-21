/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

/**
 * @Class Name  : NumberUtil.java
 * @Description : 전자정부프레임워크 상속받은 number 관련 유틸을 상속받은 유틸리티
 * @Modification Information
 *
 *     수정일         수정자                   수정내용
 *     -------          --------        ---------------------------
 *	 2016.04.01		강종빈		최초 생성
 * @since 2009.02.01
 * @version 1.0
 * @see
 *
 */

package nss.common.util;

import egovframework.com.utl.fcc.service.EgovNumberUtil;

public class NumberUtil extends EgovNumberUtil {

	/**
	 * 인자로 받은 Object가 Integer 타입이 아닐 경우 대체값(int) 리턴
	 * @param src 원본값
	 * @param def 대체값
	 * @return 인자로 받은 Object가 Integer 타입이 아닐 경우 대체값(int) 리턴
	 */
	public static int toInt(Object src, int def) {
		if(src == null) {
            return def;
        }
        try {
            return Integer.parseInt(String.valueOf(src));
        } catch (NumberFormatException nfe) {
            return def;
        }
	}

	/**
	 * 인자로 받은 Object가 Long 타입이 아닐 경우 대체값(long) 리턴
	 * @param src 원본값
	 * @param def 대체값
	 * @return 인자로 받은 Object가 Long 타입이 아닐 경우 대체값(long) 리턴
	 */
	public static long toLong(Object src, long def) {
		if(src == null) {
            return def;
        }
        try {
            return Long.parseLong(String.valueOf(src));
        } catch (NumberFormatException nfe) {
            return def;
        }
	}

	/**
	 * 인자로 받은 Object가 Long 타입이 아닐 경우 대체값(long) 리턴
	 * @param src 원본값
	 * @param def 대체값
	 * @return 인자로 받은 Object가 Long 타입이 아닐 경우 대체값(long) 리턴
	 */
	public static long toLong(String src, long def) {
		if(src == null) {
            return def;
        }
        try {
            return Long.parseLong(src);
        } catch (NumberFormatException nfe) {
            return def;
        }
	}

	/**
	 * 인자로 받은 Object가 Float 타입이 아닐 경우 대체값(float) 리턴
	 * @param src 원본값
	 * @param def 대체값
	 * @return 인자로 받은 Object가 Float 타입이 아닐 경우 대체값(float) 리턴
	 */
	public static double toFloat(Object src, double def) {
		if(src == null) {
            return def;
        }
        try {
            return Float.parseFloat(String.valueOf(src));
        } catch (NumberFormatException nfe) {
            return def;
        }
	}

	/**
	 * 인자로 받은 Object가 Double 타입이 아닐 경우 대체값(double) 리턴
	 * @param src 원본값
	 * @param def 대체값
	 * @return 인자로 받은 Object가 Double 타입이 아닐 경우 대체값(double) 리턴
	 */
	public static double toDouble(Object src, double def) {
		if(src == null) {
            return def;
        }
        try {
            return Double.parseDouble(String.valueOf(src));
        } catch (NumberFormatException nfe) {
            return def;
        }
	}
}
