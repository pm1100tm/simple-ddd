package com.post.prac.application.post;

import com.post.prac.application.post.input.CreatePostCommand;
import com.post.prac.application.post.input.UpdatePostCommand;
import com.post.prac.core.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public void process(CreatePostCommand command) {
	}

	public void process(UpdatePostCommand command) {
	}
}
