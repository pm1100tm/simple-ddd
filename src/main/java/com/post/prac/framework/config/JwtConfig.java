package com.post.prac.framework.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * JwtConfig 는 application.yml 정의된 jwt 관련 환경변수를 바인딩합니다.
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
	private String name;
	private String prefix;
	private String secretKey;
	private long atExpirationMs;
	private long rtExpirationMs;
}
