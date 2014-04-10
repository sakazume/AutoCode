package common;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 暗号化クラス
 * @author styu03
 *
 */
public class Encrypt {
	/**
	 * 秘密鍵をバイト列から生成する
	 * @param key_bits 鍵の長さ（ビット単位）
	 */
	public static Key makeKey1(int key_bits) {
		// バイト列
		byte[] key = new byte[key_bits / 8];

		// バイト列の内容（秘密鍵の値）はプログラマーが決める
		for (int i = 0; i < key.length; i++) {
			key[i] = (byte) (i + 1);
		}

		return new SecretKeySpec(key, "AES");
	}

	/**
	 * 暗号化
	 */
	public static byte[] encode1(byte[] src, Key skey) {
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			return cipher.doFinal(src);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 復号化
	 */
	public static byte[] decode1(byte[] src, Key skey) {
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			return cipher.doFinal(src);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
