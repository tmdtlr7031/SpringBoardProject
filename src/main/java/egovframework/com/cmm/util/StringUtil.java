/**
 * @Class Name  : StringUtil.java
 * @Description : 전자정부프레임워크 상속받은 문자열 데이터 처리 관련 유틸리티
 * @Modification Information
 *
 *     수정일         수정자                   수정내용
 *     -------          --------        ---------------------------
 *	 2016.04.01		강종빈		최초 생성
 * @author 공통 서비스 개발팀 박정규
 * @since 2009. 01. 13
 * @version 1.0
 * @see
 *
 */
package egovframework.com.cmm.util;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.Arrays;

import org.apache.commons.lang3.math.NumberUtils;

import egovframework.com.utl.fcc.service.EgovStringUtil;


public class StringUtil extends EgovStringUtil{


	public static void main(String[] args){



	}

	/**
	 * 문자열이 널 또는 "" 일경우 대체문자열 처리
	 * @param src 원본 문자열 배열
	 * @param des 더할문자열
	 * @return null일경우나 "" 일 경우 대체문자열로 변경
	 */
	public static String nvl(String src){

		if( "".equals(nullConvert(src)) ) return "";

		return src;
	}

	/**
	 * 문자열이 널 또는 "" 일경우 대체문자열 처리
	 * @param src 원본 문자열 배열
	 * @param des 더할문자열
	 * @return null일경우나 "" 일 경우 대체문자열로 변경
	 */
	public static String getConvert(String src, String des){

		if( "".equals(nullConvert(src)) ) return des;

		return src;
	}



	/**
	 * 문자열이 공백 제거
	 * @param src 원본 문자열 배열
	 * @return 문자열 공백을 제거하고 null일 경우 "" 일 경우 대체문자열로 변경
	 */
	public static String trim(String str){

		return getConvert(removeWhitespace(str),"");

	}

	/**
	 * 첫번째 인자 값이 true일 경우 리턴값 변경 처리
	 * @param compare booelan값
	 * @param des1 boolean 값이 true일 경우 리턴
	 * @param des2 boolean 값이 false일 경우 리턴
	 * @return des1 or des2
	 */
	public static String compareString(boolean compare, String des1, String des2){

		if(compare)return getConvert(des1,"");
		else return getConvert(des2,"");
	}

	/**
	 * 첫번째 인자 값이 0이상일 경우 리턴값 변경 처리
	 * @param compare -1 이상일 경우 true로 인식
	 * @param des1 boolean 값이 true일 경우 리턴
	 * @param des2 boolean 값이 false일 경우 리턴
	 * @return des1 or des2
	 */
	public static String compareString(int compare, String des1, String des2){

		if(compare > -1 )return getConvert(des1,"");
		else return getConvert(des2,"");
	}


	/**
	 * 숫자값에 Lpadding 적용된 문자열 리턴
	 * @param soruce 원본값
	 * @param digits 패딩길이
	 * @param str 패딩대체 값
	 * @return Lpadding된 패딩된 문자열 리턴
	 */
	public static String leftPad(String soruce, int digits, String str)
	{
		String padStr = "";
		String ori = "";
		ori = getConvert(soruce,"");
		if (ori.length() < digits)
		{
			for (int i = 0; i < digits - ori.length(); i++)
			{
				padStr += str;
			}
		}
		return padStr + ori;
	}

	/**
	 * 숫자값에 Rpadding 적용된 문자열 리턴
	 * @param soruce 원본값
	 * @param digits 패딩길이
	 * @param str 패딩대체 값
	 * @return Rpadding된 패딩된 문자열 리턴
	 */
	public static String rightPad(String soruce, int digits, String str)
	{
		String padStr = "";
		String ori = "";
		ori = getConvert(soruce,"");
		if (ori.length() < digits)
		{
			for (int i = 0; i < digits - ori.length(); i++)
			{
				padStr += str;
			}
		}
		return ori+padStr;
	}

	public static String getArrays(String[] array){

		if(array == null )return "";

		return Arrays.toString(array);
	}

	/**
	 * 문자열의 바이트 길이를 UTF-8 캐릭터셋으로 계산해 리턴
	 * @param soruce 원본값
	 * @return 문자열의 바이트 길이
	 */
	public static long getStringBytes(String soruce) {
		return getStringBytes(soruce, "euc-kr");
	}

	/**
	 * 문자열의 바이트 길이를 입력한 캐릭터셋으로 계산해 리턴 (UTF-8, EUC-KR ...)
	 * @param soruce 원본값
	 * @PARAM charset 캐릭터셋
	 * @return 문자열의 바이트 길이
	 */
	public static long getStringBytes(String soruce, String charset) {

		long returnValue = 0L;

		try {

			returnValue = soruce.getBytes(charset).length;

		} catch(UnsupportedEncodingException e) {
		    System.err.println("UnsupportedEncodingException");
		}

		return returnValue;
	}

	/**
	 * 수치를 천단위 콤마가 추가된 문자열을 생성한다.
	 *
	 * @param number 숫자형태의 문자열
	 * @return 수치를 천단위 콤마가 추가된 문자열로 반환한다.
	 */
	public static String getThousandCommaSeperator(Object number) {

		String returnValue = "";

		try {

			if(number != null && !"".equals(number) ) {
				returnValue = String.valueOf(number);

//				if(NumberUtil.getNumberValidCheck(returnValue)) {
					returnValue = NumberFormat.getInstance().format(NumberUtils.createNumber(returnValue));
//				}
			}
		} catch(NumberFormatException e) {
			System.err.println("NumberFormatException");
		}

		return returnValue;

	}
	
	public static String evl(Object obj, String def) {
		return ( obj == null || "".equals(obj) ) ? def : String.valueOf(obj);
	}
	
	public static String evl(Object obj) {
		return evl(obj,"");
	}
}
