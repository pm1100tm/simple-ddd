package com.post.prac.framework.config;

import com.post.prac.framework.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SpringSecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	private static final String[] WHITE_LIST = new String[]{
			"/v3/api-docs/**",
			"/swagger-ui.html",
			"/swagger-ui/**",
			"/actuator/health",
			"/api/v1/auth/**"
	};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// CSRF 보호 비활성화 (stateless API 이므로)
				.csrf(AbstractHttpConfigurer::disable)
				// TODO: 추후 설정
				.cors(cors -> {})
				// 세션을 사용하지 않음 (stateless)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// 엔드포인트 별 접근 제어 설정
				// 로그인 엔드포인트는 인증 없이 허용. 하지만 Filter 를 실행하지 않는 것은 아니다. 결국 양쪽 모두에 있어야 함.
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(WHITE_LIST).permitAll()
						.anyRequest().authenticated() // 그 외 요청은 인증 필요
				)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				// TODO: JwtExceptionFilter 추가
		;

		return http.build();
	}

	// TODO: CorsConfigurationSource Bean 추가
}
