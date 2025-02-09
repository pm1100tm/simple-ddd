package com.post.prac.framework.filter;

import com.post.prac.framework.advice.LoginUser;
import com.post.prac.framework.config.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	public JwtAuthenticationFilter(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}

	private final AntPathMatcher pathMatcher = new AntPathMatcher();
	private final List<String> EXCLUDED_PATHS = List.of(
			"/api/v1/auth/login"
	);

	/**
	 * 특정 요청 경로가 인증 예외 대상(EXCLUDED_PATHS)에 포함되는지 확인
	 */
	private boolean isExcludedPath(String requestPath) {
		return EXCLUDED_PATHS.stream().anyMatch(excludedPath -> pathMatcher.match(excludedPath, requestPath));
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

	private LoginUser getLoginUserFromToken(JSONObject obj) {
		String memberId = (String) obj.get("memberId");
		String name = (String) obj.get("name");
		return LoginUser.builder().memberId(memberId).name(name).build();
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
									@NonNull HttpServletResponse response,
									@NonNull FilterChain filterChain) throws ServletException, IOException {
		String path = request.getServletPath();

		// ✅ 특정 경로는 인증 로직을 타지 않음
		if(isExcludedPath(path)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = getJwtFromRequest(request);
		if (!StringUtils.hasText(token)) {
			throw new ServletException("JWT token is empty");
		}

		// TODO: ✅ JWT 검증 수행

		// ✅ JWT 파싱하여 사용자 정보 추출
		JSONObject jsonObject = jwtProvider.parseToken(token);
		var user = getLoginUserFromToken(jsonObject);

		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		// ✅ Spring Security Context 설정
		SecurityContextHolder.getContext().setAuthentication(authentication);

		filterChain.doFilter(request, response);
	}
}
