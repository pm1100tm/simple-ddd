package com.post.prac.core.domain.member;

import com.post.prac.core.domain.type.YnFlag;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table(name = "member", schema = "swd")
public class Member {

	private static final String PREFIX = "member_";

	@Id
	@Column(name = "member_id")
	@Comment("멤버 ID")
	private String memberId;

	@Column(name = "name")
	@Comment("이름")
	private String name;

	@Column(name = "password")
	@Comment("비밀번호")
	private String password;

	@Column(name = "delete_yn")
	@Enumerated(EnumType.STRING)
	@Comment("삭제여부(Y/N)")
	private YnFlag deleteYn;

	@CreatedDate
	@Comment("생성일시")
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Comment("수정일시")
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public String memberId() {
		return this.memberId;
	}

	public String name() {
		return this.name;
	}

	public String password() {
		return this.password;
	}

	public YnFlag deleteYn() {
		return this.deleteYn;
	}
}
