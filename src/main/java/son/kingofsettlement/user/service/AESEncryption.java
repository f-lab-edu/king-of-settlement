package son.kingofsettlement.user.service;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import son.kingofsettlement.user.exception.EncryptException;

public class AESEncryption {

	private static final String AES_SECRET_KEY = "KING_OF_SETTLEMENT_02024";
	private static final String AES_ALGORITHM = "AES";
	private static final String AES_TRANSFORMATION = "AES/ECB/PKCS5Padding";

	public static String encrypt(String text) throws EncryptException {
		try {
			Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
			SecretKeySpec secretKeySpec = new SecretKeySpec(AES_SECRET_KEY.getBytes(), AES_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] encryptedBytes = cipher.doFinal(text.getBytes());
			return Base64.getEncoder().encodeToString(encryptedBytes);
		} catch (Exception e) {
			throw new EncryptException("암호화중 에러가 발생했습니다.");
		}
	}

	public static String decrypt(String encryptedText) throws EncryptException {
		try {
			Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
			SecretKeySpec secretKeySpec = new SecretKeySpec(AES_SECRET_KEY.getBytes(), AES_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
			byte[] decryptedBytes = cipher.doFinal(decodedBytes);
			return new String(decryptedBytes);
		} catch (Exception e) {
			throw new EncryptException("복호화중 에러가 발생했습니다.");
		}
	}
}
