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
 *	 
 * @since 2009.02.01
 * @version 1.0
 * @see
 *
 */

package nss.common.util;

public class PasswordUtil{

	/**
	 * 랜덤으로 임시 비밀번호 생성
	 * @param length 임시 비밀번호 길이
	 * @return 생성된 임시 비밀번호 리턴
	 */
	public static String randomPassword(int length) {
		int index = 0;
		char[]  charSet = new char[]{
			'0','1','2','3','4','5','6','7','8','9',
			'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
			'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
			'!','@','#','$','%'};

		StringBuffer sb = new StringBuffer();
		for(int i=0;i<length;i++){
			index = (int) (charSet.length*Math.random());
			sb.append(charSet[index]);
		}

		return sb.toString();
	}


}
