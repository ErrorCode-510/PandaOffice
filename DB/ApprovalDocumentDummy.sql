-- department 테이블에 더미 데이터 삽입
INSERT INTO department (id, name)
VALUES (1, '인사부'),
       (2, '재무부'),
       (3, '마케팅부'),
       (4, 'IT부서'),
       (5, '영업부');
-- job 테이블에 더미 데이터 삽입
INSERT INTO job (id, title)
VALUES (1, '사원'),
       (2, '대리'),
       (3, '과장'),
       (4, '차장'),
       (5, '부장');
INSERT INTO employee (employee_id, name, english_name, hanja_name, department_id, job_id, phone, personal_id, gender,
                      hire_date, end_date, address, nationality, birth_date, email, self_introduction,
                      employment_status)
VALUES (1, '홍길동', 'Hong Gildong', '洪吉童', 1, 1, '010-1234-5678', '123456-1234567', 'M', '2020-01-01', NULL, '서울시 강남구',
        '대한민국', '1990-01-01', 'hong@example.com', '열심히 하겠습니다.', '재직'),
       (2, '김철수', 'Kim Cheolsu', '金哲洙', 2, 2, '010-2345-6789', '234567-2345678', 'M', '2021-02-01', NULL, '서울시 서초구',
        '대한민국', '1985-05-05', 'kim@example.com', '최선을 다하겠습니다.', '재직'),
       (3, '이영희', 'Lee Younghee', '李英姬', 3, 3, '010-3456-7890', '345678-3456789', 'F', '2019-03-01', NULL, '서울시 종로구',
        '대한민국', '1992-02-02', 'lee@example.com', '잘 부탁드립니다.', '재직'),
       (4, '박영수', 'Park Youngsu', '朴英洙', 4, 4, '010-4567-8901', '456789-4567890', 'M', '2018-04-01', NULL, '서울시 용산구',
        '대한민국', '1988-08-08', 'park@example.com', '성실히 임하겠습니다.', '재직'),
       (5, '최지우', 'Choi Jiwoo', '崔智友', 5, 5, '010-5678-9012', '567890-5678901', 'F', '2017-05-01', NULL, '서울시 마포구',
        '대한민국', '1995-12-12', 'choi@example.com', '감사합니다.', '재직');
INSERT INTO approval_document_template_folder(id, `name`, ref_folder_id)
VALUES (1, '전체', null);

INSERT INTO approval_document_template(id, title, document, `status`, last_editor_id, edit_able_status, folder_id)
VALUES (1, '템플릿 1', "ㄴㅇㄹ", 1, '3', 0, 1);

INSERT INTO approval_document (document_template_id, title, draft_employee_id, approval_date, last_approval_date,
                               department_id, document, `state`)
VALUES (1, 'Document 1', 1, '2023-01-01', '2023-01-05', 2, 'document1.pdf', 'APPROVE'),
       (1, 'Document 2', 2, '2023-02-01', '2023-02-10', 3, 'document2.pdf', 'APPROVE'),
       (1, 'Document 3', 3, '2023-03-01', '2023-03-15', 1, 'document3.pdf', 'APPROVE'),
       (1, 'Document 4', 4, '2023-04-01', '2023-04-20', 2, 'document4.pdf', 'APPROVE'),
       (1, 'Document 5', 5, '2023-05-01', '2023-05-25', 1, 'document5.pdf', 'APPROVE');
