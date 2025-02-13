package com.post.prac.presentation;

import com.post.prac.application.member.AuthService;
import com.post.prac.application.member.input.LoginCommand;
import com.post.prac.framework.advice.AuthUser;
import com.post.prac.presentation.request.LoginRequest;
import com.post.prac.presentation.response.LoginResponse;
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
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
		System.out.println("Login!!!");
		var command = LoginCommand.builder()
				.memberId(request.getMemberId())
				.password(request.getPassword())
				.build();

		var response = authService.process(command);

		return ResponseEntity.ok(LoginResponse.of(response));
	}

	@PostMapping("/test")
	public ResponseEntity<LoginResponse> test(@AuthenticationPrincipal AuthUser user) {
		System.out.println(user);
		return ResponseEntity.ok().build();
	}
}
