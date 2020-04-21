package nss.common.util.enc;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/*
http://www.imcore.net | hosihito@gmail.com
Developer. Kyoungbin Lee
2012.09.07

AES256 EnCrypt / DeCrypt
*/
public class Aes256 {

	public static void main(String[] args) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		String key = "abcdefghijklmnopqrstuvwxyz123456";

		String plainText;
		String encodeText;
		String decodeText;

		// Encrypt
		plainText  = "imcore.net";
		encodeText = AES256Cipher.Encode(plainText, key);
		System.out.println("AES256_Encode : "+encodeText);

		// Decrypt
		decodeText = AES256Cipher.Decode(encodeText, key);
		System.out.println("AES256_Decode : "+decodeText);

	}

}
