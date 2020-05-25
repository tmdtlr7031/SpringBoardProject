package nss.common.util.enc;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * [프로그램명] <br>
 * [요약] <br>
 * [변경이력]<br>
 * 2012. 10. 18.::김나리::신규작성<br>
 */
public class SHA256 {

	/**
	 * [오퍼레이션명] SHA256 암호화 문자열로 변환<br>
	 * [요약] <br>
	 *
	 * @param value
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String encryptSHA256(String value) throws NoSuchAlgorithmException {
		if ( value == null || "".equals(value) ) return value;
		String encryptData = "";
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		sha.update(value.getBytes());
		byte[] digest = sha.digest();
		for (int i=0; i<digest.length; i++) {
			encryptData += Integer.toHexString(digest[i] & 0xFF).toUpperCase();
		}

		return encryptData;
	}
	
	/**
	 * [오퍼레이션명] 솔트 처리 하여 SHA256 암호화 문자열로 변환<br>
	 * [요약] <br>
	 *
	 * @param value, usrId
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String encryptSHA256(String value, String usrId) throws NoSuchAlgorithmException {
		if ( value == null || "".equals(value) ) return value;
		String encryptData = "";
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		
		//사용자 password 에 사용자 id 를 salt 처리
		String saltData = value + usrId;
		
		sha.update(saltData.getBytes());
		byte[] digest = sha.digest();
		for (int i=0; i<digest.length; i++) {
			encryptData += Integer.toHexString(digest[i] & 0xFF).toUpperCase();
		}

		return encryptData;
	}
}