package com.post.prac.domain.repository;

import com.post.prac.domain.model.post.Post;

import java.util.List;
import java.util.Optional;

/**
 * 도메인 리포지토리 인터페이스: Post 도메인 객체의 영속성 작업을 추상화합니다.
 */
public interface PostRepository {
	Post save(Post post);
	List<Post> findAll();
	Optional<Post> findById(String postId);
}
