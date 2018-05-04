package ch.mokath.uniknowledgerestapi.utils;

import java.util.Base64;

public class CustomDecoder {

	public static String toBase64(byte[] s) {
		return new String(Base64.getEncoder().encode(s));
	}

	public static byte[] fromBase64(String s) {
		return Base64.getDecoder().decode(s);
	}
}
