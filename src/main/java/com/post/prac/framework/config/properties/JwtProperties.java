package com.post.prac.framework.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Setter
@Getter
public class JwtProperties {

	private String name;
	private String prefix;
	private String secretKey;
	private String atExp;
	private String rtExp;
}
