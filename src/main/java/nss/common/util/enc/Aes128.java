package nss.common.util.enc;

public class Aes128 {
	public static void main(String[] args) {

		String key = "newbie12abcdefgh"; // (중요)키는 반드시 16, 24, 32바이트여야 한다!!!!!

		try {
			String encrypt = AES128Cipher.encrypt("test1234", key);
			System.out.println("origin str = " + "test1234");
			System.out.println("encrypt str = " + encrypt);

			String decrypt = AES128Cipher.decrypt(encrypt, key);
			System.out.println("decrypt str = " + decrypt);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
