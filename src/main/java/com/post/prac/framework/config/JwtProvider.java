package com.post.prac.framework.config;

import com.post.prac.core.domain.member.dto.LoginImplInDto;
import com.post.prac.framework.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class JwtProvider {

	private final JwtProperties jwtProperties;

	private SecretKey getSigningKey() {
		byte[] decodedKey = Base64.getDecoder().decode(jwtProperties.getSecretKey());
		return Keys.hmacShaKeyFor(decodedKey);
	}

	private String generateToken(LoginImplInDto loginImplDto, Long jwtExpirationMs) {
		Date now = new Date();
		System.out.println(now);
		System.out.println(jwtExpirationMs);
		Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
		System.out.println(expiryDate);

		// JWT 페이로드에 추가할 클레임
		Map<String, String> claims = new HashMap<>();
		claims.put("memberId", loginImplDto.memberId());
		claims.put("name", loginImplDto.name());
		claims.put("deleteYn", loginImplDto.deleteYn());

		return Jwts.builder()
				.subject(loginImplDto.memberId())
				.claims(claims)
				.issuedAt(now)
				.expiration(expiryDate)
				.signWith(getSigningKey())
				.compact();
	}

	public JSONObject parseToken(String token) {
		try {
			Claims claims = Jwts.parser()
					.verifyWith(getSigningKey())
					.build()
					.parseSignedClaims(token)
					.getPayload();

			return new JSONObject(claims);
		} catch (Exception e) {
			throw new RuntimeException("Invalid JWT token", e);
		}
	}

	public String generateAcToken(LoginImplInDto dto) {
		return generateToken(dto, Long.parseLong(jwtProperties.getAtExp()));
	}

	public String generateRfToken(LoginImplInDto dto) {
		return generateToken(dto, Long.parseLong(jwtProperties.getRtExp()));
	}
}
