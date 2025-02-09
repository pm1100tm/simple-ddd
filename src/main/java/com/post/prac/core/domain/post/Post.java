package com.post.prac.core.domain.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table(name = "post", schema = "swd")
public class Post {

	private static final String ID_PREFIX = "post_";

	@Id
	@Column(name = "post_id")
	@Comment("게시글 ID")
	private String postId;

	@Column(name = "title")
	@Comment("게시글 제목")
	private String title;

	@Column(name = "content")
	@Comment("게시글 본문")
	private String content;

	@CreatedDate
	@Comment("생성일시")
	@Column(name = "created_at", updatable = false)
	protected LocalDateTime createdAt;

	@LastModifiedDate
	@Comment("수정일시")
	@Column(name = "updated_at")
	protected LocalDateTime updatedAt;

	@Builder
	public Post(String postId, String title, String content) {
		Assert.hasText(title, "필수 값: title");
		Assert.hasText(content, "필수 값: content");

		this.postId = postId;
		this.title = title;
		this.content = content;
	}

	private String postId() {
		return this.postId;
	}

	private String title() {
		return this.title;
	}

	private String content() {
		return this.content;
	}

	private LocalDateTime createdAt() {
		return this.createdAt;
	}

	private LocalDateTime updatedAt() {
		return this.updatedAt;
	}
}
