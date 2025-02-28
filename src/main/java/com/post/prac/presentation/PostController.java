package com.post.prac.presentation;

import com.post.prac.application.post.PostService;
import com.post.prac.application.post.input.CreatePostCommand;
import com.post.prac.application.post.input.UpdatePostCommand;
import com.post.prac.framework.advice.AuthUser;
import com.post.prac.presentation.request.CreatePostRequest;
import com.post.prac.presentation.request.UpdatePostRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Tag(name = "01. 게시판(Domain - Post")
public class PostController {

	private final PostService postService;

	@Operation(summary = "게시판 등록", description = "게시판 글을 등록합니다.")
	@PostMapping("")
	public ResponseEntity<Void> createPost(
			@AuthenticationPrincipal AuthUser authUser,
			@RequestBody @Valid CreatePostRequest request
	) {
		var command = CreatePostCommand.builder()
				.memberId(authUser.memberId())
				.title(request.getTitle())
				.content(request.getContent())
				.build();
		postService.process(command);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "게시판 수정", description = "특정 게시판 글을 수정합니다.")
	@PutMapping("/{postId}")
	public ResponseEntity<Void> updatePost(
			@RequestBody @Valid UpdatePostRequest request,
			@PathVariable String postId
	) {
		var command = UpdatePostCommand.builder()
				.postId(postId)
				.title(request.getTitle())
				.content(request.getContent())
				.build();
		postService.process(command);
		return ResponseEntity.ok().build();
	}
}
