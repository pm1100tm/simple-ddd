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
public class CreatePostRequest {

	@NotBlank(message = "필수 값입니다: title")
	@Size(max = 255, message = "제목은 최대 255자까지 허용됩니다.")
	@Schema(description = "타이틀", example = "title")
	private String title;

	@NotBlank(message = "필수 값입니다: content")
	@Schema(description = "내용", example = "content")
	private String content;
}
