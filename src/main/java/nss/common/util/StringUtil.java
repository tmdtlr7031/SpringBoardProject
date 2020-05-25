/**
 * @Class Name  : StringUtil.java
 * @Description : 전자정부프레임워크 상속받은 문자열 데이터 처리 관련 유틸리티
 * @Modification Information
 *
 *     수정일         수정자                   수정내용
 *     -------          --------        ---------------------------
 *	 
 * @author 공통 서비스 개발팀 박정규
 * @since 2009. 01. 13
 * @version 1.0
 * @see
 *
 */
package nss.common.util;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;

import egovframework.com.utl.fcc.service.EgovStringUtil;



public class StringUtil extends EgovStringUtil{
	private final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private final static int MILLIS_PER_HOUR = 3600000; // 60*60*1000
	public static final SimpleDateFormat DATE_FORMAT_NO_DIV = new SimpleDateFormat("yyyyMMddHHmmss");
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
		    e.printStackTrace();
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
		} catch(Exception e) {
			e.printStackTrace();
		}

		return returnValue;

	}


    /**
	* 공백 치환 메소드(파일 다운로드)
	* @param String str
	* @return String
	*/
	public static String getChangeArray( String str ){
		StringBuffer sb = new StringBuffer();

		if( str == null ) return "";

		for( int i = 0; i < str.length(); i++ ){
			if( str.charAt( i ) != '+' ){
				sb.append( str.charAt( i ) );
			}else {
				sb.append( "%20" );
			}
		}

		return sb.toString();
	}

	public static String evl(Object obj) {
		return evl(obj,"");
	}

	/**
	* If the value argument value is null or empty, returns defaultValue argument value;
	* if the value argument value is not null and not empty, returns value argument value.
	*
	* @param value a string.
	* @param defaultValue default value.
	* @return the string argument or default value argument.
	* @see #nvl(String value, String defaultValue)
	*/
	public static String evl(Object obj, String def) {
		return ( obj == null || "".equals(obj) ) ? def : String.valueOf(obj);
	}

	/**
	 * [오퍼레이션명] 특수문자변환<br>
	 * [요약] 기존 시나리오마켓 문자변환 함수<br>
	 *
	 * @param str
	 * @return
	 */
	public static String rReplace(String str) {
		if( str == null || "".equals(str) ) return "";

		String ret = str;

		ret = ret.replaceAll("&lt;"  ,"<");
		ret = ret.replaceAll("&gt;"  ,">");
		ret = ret.replaceAll("&quot;","'");
		ret = ret.replaceAll("&amp;" ,"&");
		ret = ret.replaceAll("&nbsp;"," " );
		ret = ret.replaceAll("&per;" ,"%");
		ret = ret.replaceAll("\"", "&#34;");

		return ret;
	}

	public static String getExt(String str) {
		if( str == null || "".equals(str) ) return "";
		String ret = str;
		int n = ret.lastIndexOf(".");
		ret = str.substring(n+1,str.length());
		ret = ret.toLowerCase();
		return ret;
	}


	public static String getHostAddress() {

		String returnValue = "";

		boolean loopback = true;

		try {
			Enumeration<NetworkInterface> enumerationNetworkInterface = NetworkInterface.getNetworkInterfaces();

			for (NetworkInterface networkInterface : Collections.list(enumerationNetworkInterface)) {
				if (networkInterface.isLoopback()) {
					continue;
				}

				Enumeration<InetAddress> enumerationInetAddress = networkInterface.getInetAddresses();

				for (InetAddress inetAddress : Collections.list(enumerationInetAddress)) {
					if ((inetAddress.getHostAddress() != null) && (inetAddress.getHostAddress().indexOf('.') != -1)) {
						returnValue = inetAddress.getHostAddress();

						loopback = false;
					}
				}

				if (!loopback) {
					break;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		return returnValue;

	}

	/**
	* Parses the string argument as a signed decimal integer.
	*
	* If the string argument is null or empty or not a number(<code>NumberFormatException<code> occur),
	* return default value argument.
	*
	* @param value a string.
	* @param defaultValue default value.
	* @return the integer represented by the argument in decimal.
	*/
	public static int parseInt(String value, int defaultValue)
	{
		try
		{
			return (value == null || value.equals("") ) ? defaultValue : Integer.parseInt(value);
		}
		catch (NumberFormatException e)
		{
			return defaultValue;
		}
	}

	/**
	* Parses the string argument as a signed decimal integer.
	*
	* If the string argument is null or empty or not a number(<code>NumberFormatException<code> occur),
	* return -1.
	*
	* @param value a string.
	* @return the integer represented by the argument in decimal.
	*/
	public static int parseInt(String value)
	{
		return parseInt(value, -1);
	}

	//▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒

	/**
	* Parses the string argument as a signed decimal <code>long</code>.
	*
	* If the string argument is null or empty or not a number(<code>NumberFormatException<code> occur),
	* return default value argument.
	*
	* @param value a string.
	* @param defaultValue default value.
	* @return the <code>long</code> represented by the argument in decimal.
	*/
	public static long parseLong(String value, long defaultValue)
	{
		try
		{
			return (value == null || value.equals("") ) ? defaultValue : Long.parseLong(value);
		}
		catch (NumberFormatException e)
		{
			return defaultValue;
		}
	}

	public static Long parseLong(String value, Long defaultValue)
	{
		try
		{
			return (value == null || "".equals(value) ) ? defaultValue : Long.valueOf(value);
		}
		catch (NumberFormatException e)
		{
			return defaultValue;
		}
	}

	/**
	* Parses the string argument as a signed decimal <code>long</code>.
	*
	* If the string argument is null or empty or not a number(<code>NumberFormatException<code> occur),
	* return -1L.
	*
	* @param value a string.
	* @return the <code>long</code> represented by the argument in decimal.
	*/
	public static long parseLong(String value)
	{
		return parseLong(value, -1L);
	}

	public static java.util.Date toUtilDate(long dt) {
		return new java.util.Date(dt);
	}

	/**
	 * conver date format
	 *
	 * @param java.sql.Timestamp
	 *            dt
	 * @param String
	 *            formatter (yyyyMMdd.....)
	 * @return String
	 */
	public static String converFormat(java.sql.Timestamp dt, String formatter) {
		return convertFormat((java.util.Date) dt, formatter);
	}

	private static java.util.Date getDate(String dt) {
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.valueOf(dt.substring(0, 4)).intValue(), Integer.valueOf(dt.substring(4, 6)).intValue() - 1,
				Integer.valueOf(dt.substring(6, 8)).intValue(), Integer.valueOf(dt.substring(8, 10)).intValue(),
				Integer.valueOf(dt.substring(10, 12)).intValue(), Integer.valueOf(dt.substring(12, 14)).intValue());

		return cal.getTime();
	}

	public static String convertFormat(long dt, String formatter) {
		return convertFormat(toUtilDate(dt), formatter);
	}

	public static String convertFormat(String dt) {
		if (dt == null)
			return "";//throw new IllegalArgumentException("dt can not be null");

		String formatter = "";
		int len = dt.length();
		if (!(len == 8 || len == 14))
			return "";//throw new IllegalArgumentException("dt length must be 8 or 14 (yyyyMMdd or yyyyMMddHHmmss)");

		if (dt.length() == 8) {
			dt += "090000";
			formatter = "MM-dd-yyyy";
		} else
			formatter = "MM-dd-yyyy HH:mm:ss";

		SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		return sdf.format(getDate(dt));
	}

	/**
	 * conver date format
	 *
	 * @param String
	 *            dt (yyyyMMdd or yyyyMMddHHmmss)
	 * @param String
	 *            formatter (yyyyMMdd.....)
	 * @return String
	 */
	public static String convertFormat(String dt, String formatter) {
		if (dt == null)
			return "";//throw new IllegalArgumentException("dt can not be null");

		int len = dt.length();
		if (!(len == 8 || len == 14))
			return "";//throw new IllegalArgumentException("dt length must be 8 or 14 (yyyyMMdd or yyyyMMddHHmmss)");

		if (dt.length() == 8)
			dt += "090000";

		SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		return sdf.format(getDate(dt));
	}

	/**
	 * conver date format
	 *
	 * @param java.sql.Date
	 *            dt
	 * @param String
	 *            formatter (yyyyMMdd.....)
	 * @return String
	 */
	public static String convertFormat(java.sql.Date dt, String formatter) {
		return convertFormat((java.util.Date) dt, formatter);
	}

	/**
	 * conver date format
	 *
	 * @param java.util.Date
	 *            dt
	 * @param String
	 *            formatter (yyyyMMdd.....)
	 * @return String
	 */
	public static String convertFormat(java.util.Date dt, String formatter) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		return sdf.format(dt);
	}

	/**
	 * get system date
	 *
	 * @return java.util.Date
	 */
	public static java.util.Date getCurrentDate() {
		return new java.util.Date(System.currentTimeMillis());
	}

	/**
	 * get system time with a default date format
	 *
	 * @return String
	 */
	public static String getCurrentTime() {
		return getCurrentTime(getDefaultFormat());
	}

	/**
	 * get system time with a formatter
	 *
	 * @param String
	 *            formatter : yyyyMMdd....
	 * @return String
	 */
	public static String getCurrentTime(String formatter) {
		SimpleDateFormat fmt = new SimpleDateFormat(formatter);
		fmt.setTimeZone(getTimeZone());

		return fmt.format(new java.util.Date(System.currentTimeMillis()));
	}

	public static String getDefaultFormat() {
		return DEFAULT_DATE_FORMAT;
		// return
		// BasePropManager.getBaseProperties("Environment").getString("date.formatter",
		// DEFAULT_DATE_FORMAT);
	}

	public static TimeZone getTimeZone() {
		return TimeZone.getDefault();
	}
	public static java.util.Date addDate(java.util.Date dt, long day) {
		return new java.util.Date(dt.getTime() + (MILLIS_PER_HOUR * 24L * day));
	}

	/**
	* Returns value argument,
	* left-padded to length padLen argument with the sequence of character in padChar argument.
	*
	* @param value a short value.
	* @param padLen the total length of the return value.
	* @param padChar padded character.
	* @return left padded string.
	*/
	public static String lpad(int value, int padLen, char padChar)
	{
		return lpad( String.valueOf(value), padLen, padChar );
	}

	/**
	* Returns value argument,
	* left-padded to length padLen argument with the sequence of character in padChar argument.
	*
	* If the value argument is null, value argument think of empty string (&quot;&quot;).
	*
	* @param value a string value.
	* @param padLen the total length of the return value.
	* @param padChar padded character.
	* @return left padded string.
	*/
	public static String lpad(String value, int padLen, char padChar)
	{
		if (value == null) value = "";

		while (value.length() < padLen)
		{
			value = padChar + value;
		}

		return value;
	}

	/**
	 * request 파라미터 정보로 Request Query String 을 생성한다.<br />
	 * 삭제 대상 파라미터 배열이 존재할 경우 해당 파라미터를 제외한 request 정보로 Request Query String 을 생성한다.<br />
	 *
	 * @param request - HttpServletRequest
	 * @param removeKeyArray - 삭제 대상 파라미터 배열
	 * @return request 파라미터 정보로 생성된 Request Query String 을 String 타입으로 리턴한다.
	 */
	public static String requestToQueryString(HttpServletRequest request, String[] removeKeyArray) {

		StringBuffer returnValue = new StringBuffer();

		try {
			@SuppressWarnings("unchecked")
			Enumeration<String> enumeration = request.getParameterNames();

			String key = "";
			String[] values = null;

			boolean isRemoveKey = false;

			while (enumeration.hasMoreElements()) {
				key = (String) enumeration.nextElement();

				if (!"".equals(key)) {
					if (removeKeyArray != null) {
						for (String removeKey : removeKeyArray) {
							if (key.equals(removeKey)) {
								isRemoveKey = true;

								break;
							}
						}
					}

					if (isRemoveKey) {
						isRemoveKey = false;
					} else {
						values = request.getParameterValues(key);

						for (String value : values) {
							returnValue.append(key + "=" + value + "&");
						}
					}
				}
			}

			if (returnValue.lastIndexOf("&") != -1) {
				returnValue = new StringBuffer(returnValue.substring(0, returnValue.lastIndexOf("&")));
			}
		} catch(Exception e) {
			returnValue = new StringBuffer();

			e.printStackTrace();
		}

		return returnValue.toString();

    }

    /**
     * 해당 String 값이 숫자값이면 parseInt한 값을 , 아니면 -1 리턴
     * @return int
     */
    public static int isNumberic2(String str){
        if(Pattern.matches("[0-9]+", str))
            return zeroConvert(str);
        else
            return -1;
    }

    /**
     * 확장자가 있는 화일명을 화일명을 삭제하고 확장자만 넘겨줌
     * @return String
     */
    public static String getFileExtension(String filename)
    {
        if (filename == null) return null;

        int len = filename.length();
        if (len == 0) return filename;

        int last = filename.lastIndexOf(".");
        if (last == -1) return filename;

        return filename.substring( last + 1, len );
    }

    /**
     * 배열안에 문자열이 존재하는지 비교하여 true,false 반환
     * @return String
     */
    public static String compareString(String[] arr, String compareStr, String returnStr) {
     	if (arr == null) return "";
     	for (int i = 0; i < arr.length; i++) {
			if(arr[i].equals(compareStr)) {
			   	return returnStr;
			}
     	}
     	return "";
 	}

    /**
     * 배열안에 문자열이 존재하는지 비교하여 true,false 반환
     * @return String
     */
	public static String compareString(String[] arr, String compareStr) {
		return compareString(arr, compareStr, compareStr);
	}


	public static String cleanXSS(String s) {
		if(s != null && s.length() > 0) {
			s = s.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			s = s.replaceAll("\"", "&quot;").replaceAll("'", "&#39;");
		}
		return s;
	}
	
	public static String ifnull(Object obj, String dft) {
		if(obj == null) { return dft; }
		return obj.toString();
	}
	
	public static String ifnull(Object obj) {
		return ifnull(obj, null);
	}
	
	public static String getDate(long time) {
		Calendar nCal = Calendar.getInstance();
		nCal.setTimeInMillis(time);
		return getNow(nCal);
	}
	
	public static String getDate(long time, String format) {
		Calendar nCal = Calendar.getInstance();
		nCal.setTimeInMillis(time);
		return getNow(nCal, format);
	}
	
	public static String getNow(Calendar cal) {
		return new SimpleDateFormat(getDefaultFormat()).format(cal.getTime());
	}
	
	public static String getNow(Calendar cal, String format) {
		return new SimpleDateFormat(format).format(cal.getTime());
	}
	
	public static synchronized String getNowNoFmt() {
		return DATE_FORMAT_NO_DIV.format(Calendar.getInstance().getTime());
	}
	
	public static String leftZeroPadding(int maxdigit, int number) {
		return String.format("%0" + maxdigit + "d", number);
	}

	/* 데몬에서 사용하는 부분 */
	public static synchronized Calendar convertDate(String date) {	// date: yyyyMMddHHmmss
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(DATE_FORMAT_NO_DIV.parse(date));
			return cal;
		} catch (ParseException e) {
			System.err.println("ParseException");
		}
		return null;
	}
}
