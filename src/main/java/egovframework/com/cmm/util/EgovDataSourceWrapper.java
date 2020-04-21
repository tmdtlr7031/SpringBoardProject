/**
 * Spring의 MyBatis 연동 지원 클래스인 EgovAbstractMapper 상속
 * @author IT사업부 강종빈 과장
 * @since 2016.04.01
 * @version 1.0
 * @see
 * <pre>
 *  == 개정이력(Modification Information) ==
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2016.04.01  강종빈          최초 생성
 *
 * </pre>
 */

package egovframework.com.cmm.util;

import org.apache.commons.dbcp.BasicDataSource;

import nss.common.util.enc.AES128Cipher;


public class EgovDataSourceWrapper extends BasicDataSource{
// <TODO> db접속 암호화
	
	// key값 기준은?
	static String key = "newbie12abcdefgh";



    public void setPassword(String password) {
    	
        try {
        	password = AES128Cipher.decrypt(password, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
        super.setPassword(password);
    }
    


    public void setUsername(String username) {
    	
        try {
        	username = AES128Cipher.decrypt(username, key);
		} catch (Exception e) {
			e.printStackTrace();
		} 
        
    	super.setUsername(username);
    }
    
    public static void main(String[] args){
    	
        try {
        	
			String encrypt1 = AES128Cipher.encrypt("접속ID", key);
			System.out.println("origin str = " + "접속ID");
			System.out.println("encrypt str = " + encrypt1);

			String decrypt1 = AES128Cipher.decrypt(encrypt1, key);
			System.out.println("decrypt str = " + decrypt1);
        	
			
			
			String encrypt2 = AES128Cipher.encrypt("접속PW", key);
			System.out.println("origin str = " + "접속PW");
			System.out.println("encrypt str = " + encrypt2);

			String decrypt2 = AES128Cipher.decrypt(encrypt2, key);
			System.out.println("decrypt str = " + decrypt2);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
