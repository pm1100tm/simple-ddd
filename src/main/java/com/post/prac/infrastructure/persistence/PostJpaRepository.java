package com.post.prac.infrastructure.persistence;

import com.post.prac.core.domain.post.Post;
import com.post.prac.core.repository.PostRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostJpaRepository extends JpaRepository<Post, String>, PostRepository {
}
