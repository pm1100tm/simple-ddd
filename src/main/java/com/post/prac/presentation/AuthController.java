package com.post.prac.presentation;

import com.post.prac.application.member.AuthService;
import com.post.prac.application.member.input.LoginCommand;
import com.post.prac.framework.advice.AuthUser;
import com.post.prac.presentation.request.LoginRequest;
import com.post.prac.presentation.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "00. 인증/인가(Domain - Auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	@Operation(summary = "유저 인증/인가", description = "유저가 ID/Password 로 인증 후 토큰을 취득합니다.")
	public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
		var command = LoginCommand.builder()
				.memberId(request.getMemberId())
				.password(request.getPassword())
				.build();

		var response = authService.process(command);

		return ResponseEntity.ok(LoginResponse.of(response));
	}

	@PostMapping("/test")
	@Operation(summary = "유저 인증/인가 테스트", description = "인증/인가 테스트. 삭제 예정")
	public ResponseEntity<LoginResponse> test(@AuthenticationPrincipal AuthUser user) {
		System.out.println(user);
		return ResponseEntity.ok().build();
	}
}
