package com.post.prac.framework.filter;

import com.post.prac.framework.advice.AuthUser;
import com.post.prac.framework.auth.JwtProvider;
import com.post.prac.framework.exception.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;
	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	private final List<String> EXCLUDE_PATHS = List.of(
			"/actuator",
			"/actuator/**",
			"/api/v1/auth/login"
	);

	private final List<String> INCLUDE_PATHS = List.of(
			"/api/v1/**"
	);

	/**
	 * 특정 요청 경로가 인증 예외 대상(EXCLUDED_PATHS)에 포함되는지 확인
	 */
	private boolean checkExcludePath(String requestPath) {
		return EXCLUDE_PATHS.stream()
				.anyMatch(it -> pathMatcher.match(it, requestPath));
	}

	private boolean checkIncludePath(String requestPath) {
		return INCLUDE_PATHS.stream()
				.anyMatch(it -> pathMatcher.match(it, requestPath));
	}

	/**
	 * 요청 헤더 "Authorization"에서 "Bearer " 접두어가 붙은 JWT 토큰을 추출합니다.
	 * @param request: HTTP 요청 객체
	 * @return: Json Web Token
	 */
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith("Bearer ")) {
			return null;
		}

		return bearerToken.substring(7);
	}

	private AuthUser getLoginUserFromToken(JSONObject obj) {
		String memberId = (String) obj.get("memberId");
		String name = (String) obj.get("name");
		return AuthUser.builder().memberId(memberId).name(name).build();
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
									@NonNull HttpServletResponse response,
									@NonNull FilterChain filterChain) throws ServletException, IOException {

		String requestPath = request.getServletPath();
		if(checkExcludePath(requestPath)) {
			filterChain.doFilter(request, response);
			return;
		}
		if(!checkIncludePath(requestPath)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = getJwtFromRequest(request);
		if (!StringUtils.hasText(token)) {
			throw new InvalidTokenException("Token is Empty");
		}

		// TODO: ✅ JWT 검증 수행

		// ✅ JWT 파싱하여 사용자 정보 추출
		JSONObject jsonObject = jwtProvider.getJsonObject(token);
		AuthUser loginUser = getLoginUserFromToken(jsonObject);

		var authentication = new UsernamePasswordAuthenticationToken(loginUser,null, Collections.emptyList());
		var webAuthenticationDetails = new WebAuthenticationDetailsSource().buildDetails(request);
		authentication.setDetails(webAuthenticationDetails);

		// ✅ Spring Security Context 설정
		SecurityContextHolder.getContext().setAuthentication(authentication);

		filterChain.doFilter(request, response);
	}
}
