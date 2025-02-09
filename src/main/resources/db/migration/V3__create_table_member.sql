-- Flyway 마이그레이션 스크립트: member 테이블 생성
CREATE TABLE IF NOT EXISTS swd.member (
    member_id VARCHAR(100) NOT NULL,
    "name" VARCHAR(100) NOT NULL,
    delete_yn VARCHAR(1) NOT NULL default 'N',
    created_at TIMESTAMP(6),
    updated_at TIMESTAMP(6),
    CONSTRAINT member_pk PRIMARY KEY (member_id)
);
