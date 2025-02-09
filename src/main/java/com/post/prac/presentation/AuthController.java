package com.post.prac.presentation;

import com.post.prac.application.member.AuthService;
import com.post.prac.application.member.input.LoginCommand;
import com.post.prac.presentation.request.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest request) {
		var command = LoginCommand.builder()
				.memberId(request.getMemberId())
				.password(request.getPassword())
				.build();

		var response = authService.process(command);
		System.out.println("***** response: start");
		System.out.println(response);
		System.out.println("***** response: end");

		return ResponseEntity.ok().build();
	}
}
