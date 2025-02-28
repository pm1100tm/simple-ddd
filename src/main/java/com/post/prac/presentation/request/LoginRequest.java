package com.post.prac.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

	@NotBlank(message = "필수 값입니다: memberId")
	@Size(max = 100, message = "유저 아이디는 최대 100자입니다.")
	@Schema(description = "유저 아이디", example = "memberId123")
	private String memberId;

	@NotBlank(message = "필수 값입니다: password")
	@Size(max = 20, message = "비밀번호는 최대 20자입니다.")
	@Schema(description = "비밀번호", example = "1234")
	private String password;
}
