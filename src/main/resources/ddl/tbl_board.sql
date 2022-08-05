CREATE SEQUENCE seq_tbl_board;

DROP TABLE tbl_board;
CREATE TABLE tbl_board (
    board_no NUMBER(10),
    writer VARCHAR2(20) NOT NULL,
    title VARCHAR2(200) NOT NULL,
    content CLOB,
    view_cnt NUMBER(10) DEFAULT 0,
    reg_date DATE DEFAULT SYSDATE,
    CONSTRAINT pk_tbl_board PRIMARY KEY (board_no)
);

-- 첨부파일 정보를 가지는 테이블 생성
CREATE TABLE file_upload (
    file_name VARCHAR2(150),  -- /2022/08/01/sfdsdfsdf_상어.jpg
    reg_date DATE DEFAULT SYSDATE,
    bno NUMBER(10) NOT NULL
    -- 추가하면 좋은거
    -- origin_name -- 원본이름
    -- file_size -- 용량
    -- extension -- 확장자

);

-- PK, FK 부여
ALTER TABLE file_upload
ADD CONSTRAINT pk_file_name
PRIMARY KEY (file_name);

ALTER TABLE file_upload
ADD CONSTRAINT fk_file_upload
FOREIGN KEY (bno)
REFERENCES tbl_board (board_no)
ON DELETE CASCADE;




SELECT * FROM tbl_board;