-- Flyway 마이그레이션 스크립트: member 테이블 생성
CREATE TABLE IF NOT EXISTS swd.member (
    member_id VARCHAR(100) NOT NULL,
    "name" VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    delete_yn VARCHAR(1) NOT NULL default 'N',
    created_at TIMESTAMP(6),
    updated_at TIMESTAMP(6),
    CONSTRAINT member_pk PRIMARY KEY (member_id)
);

INSERT INTO swd.member
(member_id, "name", password, delete_yn, created_at, updated_at)
VALUES
('member_id_001', '멤버001', 'password1', 'N', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
('member_id_002', '멤버002', 'password2', 'N', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
('member_id_003', '멤버003', 'password3', 'N', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6))
;
