package util;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGen {
	public static void main(String[] args) {
		// JJWT 0.12.x 버전 이후에서는 Keys.secretKeyFor(SignatureAlgorithm) 대신 Keys.hmacShaKeyFor()와 SecureRandom 을
		// 사용하여 키를 생성하는 방식을 권장합니다.
		byte[] keyBytes = new byte[32]; // 256-bit key (HMAC-SHA256 requires 32 bytes)
		new SecureRandom().nextBytes(keyBytes);
		String base64Key = Base64.getEncoder().encodeToString(keyBytes);
		System.out.println("Generated Base64 Secret Key: " + base64Key);
	}
}
