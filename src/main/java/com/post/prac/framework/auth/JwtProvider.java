package com.post.prac.framework.auth;

import com.post.prac.core.domain.member.dto.LoginImplInDto;
import com.post.prac.framework.auth.exception.InvalidTokenException;
import com.post.prac.framework.auth.exception.InvalidTokenFormatException;
import com.post.prac.framework.config.JwtConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JwtProvider {

	private final JwtConfig jwtConfig;

	public JwtProvider(JwtConfig jwtConfig) {
		this.jwtConfig = jwtConfig;
	}

	private SecretKey getSigningKey() {
		byte[] decodedKey = Base64.getDecoder().decode(jwtConfig.getSecretKey());
		return Keys.hmacShaKeyFor(decodedKey);
	}

	private String generateToken(LoginImplInDto loginImplDto, Long jwtExpirationMs) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

		// JWT 페이로드에 claims 정보(사용자의 추가 정보)
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

	/* PUBLIC Method ------------------------------------------------------------------------------------------------ */

	/**
	 * 요청 객체 Header 의 AUTHORIZATION 에서 access token 문자열을 추출합니다.
	 * @param request HTTP 서블릿 요청 객체
	 * @return 'Bearer ' 문자열이 제거된 jwt access token
	 */
	public String getTokenFromHeader(HttpServletRequest request) {
		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (!StringUtils.hasText(accessToken) || !accessToken.startsWith("Bearer ")) {
			throw new InvalidTokenFormatException();
		}

		return accessToken.substring(7);
	}

	/**
	 * JWT 토큰의 유효성을 검증합니다.
	 * - 서명, 형식, 만료 여부 등을 체크하여 토큰이 유효한지 판단합니다.
	 * @param token JWT 토큰 문자열
	 * */
	public void validateToken(String token) {
		try {
			Jwts.parser()
					.verifyWith(getSigningKey())
					.build()
					.parseSignedClaims(token);
		} catch (SignatureException
				 | MalformedJwtException
				 | ExpiredJwtException
				 | UnsupportedJwtException
				 | IllegalArgumentException e) {
			log.error("[ERROR] {} : {}", e.getClass().getName(), e.getMessage());
			throw new InvalidTokenException("올바른 토큰이 아닙니다.");
		}
	}

	/**
	 * JWT 토큰으로부터 사용자 식별자(Subject)를 추출합니다.
	 * @param token JWT 토큰
	 * @return 토큰에 포함된 사용자 식별자(Subject)
	 */
	public String getSubject(String token) {
		validateToken(token);
		Claims claims = Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();

		return claims.getSubject();
	}

	/**
	 * JWT 토큰으로부터 payload 를 취득하여 JSONObject 로 변환합니다.
	 * @param token JWT 토큰
	 * @return 토큰에서 payload 값을 JSONObject 로 변환한 값
	 */
	public JSONObject getJsonObject(String token) {
		validateToken(token);
		Claims claims = Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();

		return new JSONObject(claims);
	}

	/**
	 * JWT access token 을 생성합니다.
	 * @param dto LoginImplInDto 객체
	 * @return JWT access token
	 */
	public String generateAcToken(LoginImplInDto dto) {
		return generateToken(dto, jwtConfig.getAtExpirationMs());
	}

	/**
	 * JWT refresh token 을 생성합니다.
	 * @param dto LoginImplInDto 객체
	 * @return JWT refresh token
	 */
	public String generateRfToken(LoginImplInDto dto) {
		return generateToken(dto, jwtConfig.getRtExpirationMs());
	}
}
