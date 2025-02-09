package com.post.prac.infrastructure.persistence;

import com.post.prac.domain.model.post.Post;
import com.post.prac.domain.repository.PostRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostJpaRepository extends JpaRepository<Post, String>, PostRepository {
}
