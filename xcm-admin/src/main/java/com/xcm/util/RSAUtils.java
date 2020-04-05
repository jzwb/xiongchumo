package com.xcm.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;

/**
 * Utils - RSA
 */
public final class RSAUtils {

	static final Logger LOGGER = LoggerFactory.getLogger(RSAUtils.class);

	//安全服务提供者
	private static final Provider PROVIDER = new BouncyCastleProvider();
	//密钥大小
	private static final int KEY_SIZE = 1024;
	//密钥方式
	private static final String GENERATOR_TYPE = "RSA";
	//加密和解密补位方式
	private static final String CIPHER_TYPE = "RSA/ECB/PKCS1Padding";

	/**
	 * 不可实例化
	 */
	private RSAUtils() {
	}

	/**
	 * 生成公钥和私钥
	 *
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static HashMap<String, Object> getKeys() throws NoSuchAlgorithmException {
		HashMap<String, Object> map = new HashMap<>();
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(GENERATOR_TYPE);
		keyPairGen.initialize(KEY_SIZE);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		map.put("public", publicKey);
		map.put("private", privateKey);
		return map;
	}

	/**
	 * 使用模和指数生成RSA公钥
	 *
	 * @param modulus  模
	 * @param exponent 指数
	 * @return
	 */
	public static RSAPublicKey getPublicKey(String modulus, String exponent) {
		try {
			BigInteger modulusBigInteger = new BigInteger(modulus);
			BigInteger exponentBigInteger = new BigInteger(exponent);
			KeyFactory keyFactory = KeyFactory.getInstance(GENERATOR_TYPE);
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulusBigInteger, exponentBigInteger);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			LOGGER.error("获取公钥错误（modulus：{},exponent：{}）", modulus, exponent, e);
		}
		return null;
	}

	/**
	 * 使用模和指数生成RSA私钥
	 *
	 * @param modulus  模
	 * @param exponent 指数
	 * @return
	 */
	public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
		try {
			BigInteger modulusBigInteger = new BigInteger(modulus);
			BigInteger exponentBigInteger = new BigInteger(exponent);
			KeyFactory keyFactory = KeyFactory.getInstance(GENERATOR_TYPE);
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(modulusBigInteger, exponentBigInteger);
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			LOGGER.error("获取私钥错误（modulus：{},exponent：{}）", e);
		}
		return null;
	}

	/**
	 * 生成密钥对
	 *
	 * @return 密钥对
	 */
	public static KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(GENERATOR_TYPE, PROVIDER);
			keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
			return keyPairGenerator.generateKeyPair();
		} catch (Exception e) {
			LOGGER.error("获取钥匙对错误", e);
		}
		return null;
	}

	/**
	 * 加密
	 *
	 * @param publicKey 公钥
	 * @param data      数据
	 * @return
	 */
	public static byte[] encrypt(PublicKey publicKey, byte[] data) {
		if (publicKey == null || data == null) {
			return null;
		}
		try {
			Cipher cipher = Cipher.getInstance(CIPHER_TYPE, PROVIDER);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			LOGGER.error("加密错误（publicKey：{}，data：{}）", publicKey, data, e);
		}
		return null;
	}

	/**
	 * 加密
	 *
	 * @param publicKey 公钥
	 * @param text      字符串
	 * @return
	 */
	public static String encrypt(PublicKey publicKey, String text) {
		if (publicKey == null || text == null) {
			return null;
		}
		byte[] data = encrypt(publicKey, text.getBytes());
		return data != null ? Base64.encodeBase64String(data) : null;
	}

	/**
	 * 分片加密
	 *
	 * @param publicKey 公钥
	 * @param text      字符串
	 * @return
	 */
	public static String sliceOfEncrypt(PublicKey publicKey, String text) throws UnsupportedEncodingException {
		if (publicKey == null || StringUtils.isEmpty(text))
			return null;
		//转码（解决中文问题）
		text = URLEncoder.encode(text, "UTF-8");
		//字符串分片
		String[] strings = RSAUtils.splitString(text, (KEY_SIZE / 8 - 11) / 8);
		if (strings == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (String string : strings) {
			byte[] data = RSAUtils.encrypt(publicKey, string.getBytes());
			if (data == null) {
				return null;
			}
			sb.append(RSAUtils.bcd2Str(data));
		}
		return sb.toString();
	}

	/**
	 * 解密
	 *
	 * @param privateKey 私钥
	 * @param data       数据
	 * @return 解密后的数据
	 */
	public static byte[] decrypt(PrivateKey privateKey, byte[] data) {
		if (privateKey == null || data == null) {
			return null;
		}
		try {
			Cipher cipher = Cipher.getInstance(CIPHER_TYPE, PROVIDER);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			LOGGER.error("解密错误（privateKey：{}，data：{}）", privateKey, data, e);
		}
		return null;
	}

	/**
	 * 解密
	 *
	 * @param privateKey 私钥
	 * @param text       字符串
	 * @return
	 */
	public static String decrypt(PrivateKey privateKey, String text) {
		if (privateKey == null || text == null) {
			return null;
		}
		byte[] data = decrypt(privateKey, Base64.decodeBase64(text));
		return data != null ? new String(data) : null;
	}

	/**
	 * 分片解密
	 *
	 * @param privateKey 私钥
	 * @param text       Base64编码字符串
	 * @return
	 */
	public static String sliceOfDecrypt(PrivateKey privateKey, String text) throws UnsupportedEncodingException {
		if (privateKey == null || StringUtils.isEmpty(text))
			return null;
		byte[] bytes = text.getBytes();
		byte[] bcd = ascii2bcd(bytes, bytes.length);
		StringBuilder sb = new StringBuilder();
		if (bcd != null) {
			byte[][] arrays = RSAUtils.splitArray(bcd, KEY_SIZE / 8);
			if (arrays != null) {
				for (byte[] arr : arrays) {
					byte[] data = decrypt(privateKey, arr);
					if (data == null) {
						return null;
					}
					sb.append(new String(data));
				}
			}
		}
		return URLDecoder.decode(sb.toString(), "UTF-8");
	}

	/**
	 * 字符串分片
	 *
	 * @param string 源字符串
	 * @param len    单片的长度（keysize/8）
	 * @return
	 */
	private static String[] splitString(String string, int len) {
		if (StringUtils.isEmpty(string)) {
			return null;
		}
		int x = string.length() / len;
		int y = string.length() % len;
		int z = 0;
		if (y != 0) {
			z = 1;
		}
		String[] strings = new String[x + z];
		String str;
		for (int i = 0; i < x + z; i++) {
			if (i == x + z - 1 && y != 0) {
				str = string.substring(i * len, i * len + y);
			} else {
				str = string.substring(i * len, i * len + len);
			}
			strings[i] = str;
		}
		return strings;
	}

	/**
	 * 字节数组分片
	 *
	 * @param data
	 * @param len
	 * @return
	 */
	private static byte[][] splitArray(byte[] data, int len) {
		if (data == null) {
			return null;
		}
		int x = data.length / len;
		int y = data.length % len;
		int z = 0;
		if (y != 0) {
			z = 1;
		}
		byte[][] arrays = new byte[x + z][];
		byte[] arr;
		for (int i = 0; i < x + z; i++) {
			arr = new byte[len];
			if (i == x + z - 1 && y != 0) {
				System.arraycopy(data, i * len, arr, 0, y);
			} else {
				System.arraycopy(data, i * len, arr, 0, len);
			}
			arrays[i] = arr;
		}
		return arrays;
	}

	/**
	 * bcd 转 Str
	 *
	 * @param bytes
	 * @return
	 */
	private static String bcd2Str(byte[] bytes) {
		if (bytes == null) {
			return "";
		}
		char temp[] = new char[bytes.length * 2], val;
		for (int i = 0; i < bytes.length; i++) {
			val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
			temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

			val = (char) (bytes[i] & 0x0f);
			temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
		}
		return new String(temp);
	}

	/**
	 * ASCII 转 BCD
	 *
	 * @param ascii
	 * @param asc_len
	 * @return
	 */
	private static byte[] ascii2bcd(byte[] ascii, int asc_len) {
		if (ascii == null) {
			return null;
		}
		byte[] bcd = new byte[asc_len / 2];
		int j = 0;
		for (int i = 0; i < (asc_len + 1) / 2; i++) {
			bcd[i] = asc2bcd(ascii[j++]);
			bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc2bcd(ascii[j++])) + (bcd[i] << 4));
		}
		return bcd;
	}

	/**
	 * asc转bcd
	 *
	 * @param asc
	 * @return
	 */
	private static byte asc2bcd(byte asc) {
		byte bcd;
		if ((asc >= '0') && (asc <= '9'))
			bcd = (byte) (asc - '0');
		else if ((asc >= 'A') && (asc <= 'F'))
			bcd = (byte) (asc - 'A' + 10);
		else if ((asc >= 'a') && (asc <= 'f'))
			bcd = (byte) (asc - 'a' + 10);
		else
			bcd = (byte) (asc - 48);
		return bcd;
	}
}