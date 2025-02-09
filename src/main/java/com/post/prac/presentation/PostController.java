package com.post.prac.presentation;

import com.post.prac.application.post.PostService;
import com.post.prac.application.post.input.CreatePostCommand;
import com.post.prac.application.post.input.UpdatePostCommand;
import com.post.prac.presentation.request.CreatePostRequest;
import com.post.prac.presentation.request.UpdatePostRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST 컨트롤러: 외부 클라이언트와의 통신을 통해 게시글 CRUD API를 제공합니다.
 */
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping("")
	public ResponseEntity<Void> createPost(@RequestBody @Valid CreatePostRequest request) {
		var command = CreatePostCommand.builder()
				.title(request.getTitle())
				.content(request.getContent())
				.build();
		postService.process(command);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PatchMapping("/{postId}")
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
