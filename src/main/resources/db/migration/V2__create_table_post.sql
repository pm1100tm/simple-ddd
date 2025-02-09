-- Flyway 마이그레이션 스크립트: posts 테이블 생성
CREATE TABLE IF NOT EXISTS swd.post (
    post_id VARCHAR(100) NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP(6),
    updated_at TIMESTAMP(6),
    CONSTRAINT post_pk PRIMARY KEY (post_id)
);
