use
    officeerp;

-- department 테이블에 더미 데이터 삽입
INSERT INTO `department` (`id`, `name`)
VALUES (11, '인사'),
       (12, '회계'),
       (13, '영업'),
       (14, '기획'),
       (15, '마케팅');
-- job 테이블에 더미 데이터 삽입
INSERT INTO `job` (`id`, `title`, `allowance`)
VALUES
    (50, '사원', 0),
    (40, '주임', 0),
    (35, '대리', 100000),
    (30, '과장', 250000),
    (25, '차장', 300000),
    (20, '부장', 400000),
    (11, '사장', 0);
/* 사원 정보 - 임의 */

INSERT INTO `employee` (`employee_id`, `password`, `name`, `english_name`, `hanja_name`, `department_id`,
                        `job_id`, `phone`, `personal_id`, `gender`, `hire_date`, `end_date`,
                        `address`, `nationality`, `birth_date`, `email`, `self_introduction`,
                        `employment_status`, `annual_salary`)
VALUES
    -- Employees who joined in 2012
    (201211001, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '김민수', 'Kim Min Su', '金民洙', 11,
     20, '010-5421-1001', '880115-1234561', '남', '2012-01-15', NULL, '서울특별시 강남구', '대한민국', '1988-01-15',
     'minsu.kim@example.com', NULL, '재직', 55000000),
    (201212002, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '박지영', 'Park Ji Young', '朴智英', 12,
     25, '010-7854-1002', '900224-2234562', '여', '2012-02-27', NULL, '서울특별시 서초구', '대한민국', '1990-02-24',
     'jiyoung.park@example.com', NULL, '재직', 52000000),
    -- Employees who joined in 2013
    (201313003, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '김영수', 'Kim Young Su', '金英洙', 13,
     20, '010-0540-1001', '910503-1234561', '남', '2013-04-02', NULL, '부산광역시 해운대구', '대한민국', '1991-05-03',
     'youngsu.kim@example.com', NULL, '재직', 54000000),
    (201314004, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '정미경', 'Jung Mi Kyung', '鄭美京', 14,
     30, '010-0496-1001', '930812-2234561', '여', '2013-11-23', NULL, '인천광역시 남동구', '대한민국', '1993-08-12',
     'mikyoung.jung@example.com', NULL, '재직', 40000000),
    -- Employees who joined in 2014
    (201414005, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '박영수', 'Park Young Su', '朴英洙', 15,
     35, '010-9864-1001', '881101-1134565', '남', '2014-05-27', NULL, '대구광역시 수성구', '대한민국', '1988-11-01',
     'youngsu.park@example.com', NULL, '재직', 38000000),
    -- Employees who joined in 2015
    (201515006, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '이승호', 'Lee Seung Ho', '李承昊', 15,
     35, '010-6868-1001', '900620-1234567', '남', '2015-07-20', NULL, '서울특별시 마포구', '대한민국', '1990-06-20',
     'seungho.lee@example.com', NULL, '재직', 37000000),
    (201511007, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '장미영', 'Jang Mi Young', '張美英', 11,
     30, '010-3601-1001', '910715-2298768', '여', '2015-03-21', NULL, '서울특별시 강서구', '대한민국', '1991-07-15',
     'miyoung.jang@example.com', NULL, '재직', 42000000),
    -- Employees who joined in 2016
    (201615008, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '오준혁', 'Oh Jun Hyuk', '吳俊赫', 15,
     30, '010-6801-1001', '940824-1234561', '남', '2016-08-24', NULL, '서울특별시 관악구', '대한민국', '1994-08-24',
     'junhyuk.oh@example.com', NULL, '재직', 46000000),
    (201612009, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '한지민', 'Han Ji Min', '韓智敏', 12,
     25, '010-6400-1001', '960102-2234562', '여', '2016-01-02', NULL, '서울특별시 종로구', '대한민국', '1996-01-02',
     'jimin.han@example.com', NULL, '재직', 43000000),
    -- Employees who joined in 2017
    (201713010, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '김동현', 'Kim Dong Hyun', '金東炫', 13,
     40, '010-6490-1001', '970425-1234563', '남', '2017-11-07', NULL, '서울특별시 강남구', '대한민국', '1997-04-25',
     'donghyun.kim@example.com', NULL, '재직', 41000000),
    (201714011, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '송지우', 'Song Ji Woo', '宋智雨', 14,
     50, '010-9064-1001', '970826-2234564', '여', '2017-08-26', NULL, '서울특별시 서초구', '대한민국', '1997-08-26',
     'jiwoo.song@example.com', NULL, '재직', 40000000),
    -- Employees who joined in 2018
    (201815012, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '장현우', 'Jang Hyun Woo', '張賢雨', 15,
     50, '010-9265-1001', '980213-1234565', '남', '2018-02-13', NULL, '서울특별시 송파구', '대한민국', '1998-02-13',
     'hyunwoo.jang@example.com', NULL, '재직', 39000000),
    -- Employees who joined in 2019
    (201911013, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '이가은', 'Lee Ga Eun', '李嘉恩', 11,
     30, '010-6428-1001', '990601-2234566', '여', '2019-06-01', NULL, '서울특별시 강북구', '대한민국', '1999-06-01',
     'gaeun.lee@example.com', NULL, '재직', 38000000),
    -- Employees who joined in 2020
    (202012014, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '박준영', 'Park Jun Young', '朴俊英', 12,
     35, '010-3354-1001', '001214-1234567', '남', '2020-12-14', NULL, '서울특별시 중구', '대한민국', '2000-12-14',
     'junyoung.park@example.com', NULL, '재직', 36000000),
    -- Employees who joined in 2021
    (202113015, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '김서연', 'Kim Seo Yeon', '金瑞妍', 13,
     30, '010-6483-1001', '020927-2234568', '여', '2021-09-27', NULL, '서울특별시 마포구', '대한민국', '2002-09-27',
     'seoyeon.kim@example.com', NULL, '재직', 34000000),
    (202114016, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '이도현', 'Lee Do Hyun', '李道賢', 14,
     25, '010-6541-1001', '020601-1234569', '남', '2021-06-01', NULL, '서울특별시 강서구', '대한민국', '2002-06-01',
     'dohyun.lee@example.com', NULL, '재직', 32000000),
    -- Employees who joined in 2022
    (202215017, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '박지민', 'Park Ji Min', '朴智敏', 15,
     40, '010-6424-1001', '030315-1234570', '여', '2022-03-15', NULL, '서울특별시 강남구', '대한민국', '2003-03-15',
     'jimin.park@example.com', NULL, '재직', 30000000),
    -- Employees who joined in 2023
    (202311018, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '김민수', 'Kim Min Su', '金民洙', 11,
     40, '010-3365-1001', '19971224-1235561', '남', '2023-09-04', NULL, '서울특별시 강남구', '대한민국', '1997-12-24',
     'minsu.kim@example.com', NULL, '재직', 33000000),
    (202312019, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '박지영', 'Park Ji Young', '朴智英', 12,
     50, '010-6546-1002', '950227-2194562', '여', '2023-11-24', NULL, '서울특별시 서초구', '대한민국', '1995-02-27',
     'jiyoung.park@example.com', NULL, '재직', 30000000),
    (202313020, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '이철수', 'Lee Chul Su', '李哲洙', 13,
     50, '010-2565-1003', '990303-1234563', '남', '2023-11-28', NULL, '서울특별시 동작구', '대한민국', '1999-03-03',
     'chulsu.lee@example.com', NULL, '재직', 31000000),
    (202314021, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '최영희', 'Choi Young Hee', '崔英熙', 14,
     50, '010-3216-1004', '031118-4234564', '여', '2023-04-02', NULL, '서울특별시 강북구', '대한민국', '2003-11-18',
     'younghee.choi@example.com', NULL, '재직', 29000000),
    -- Employees who joined in 2024
    (202411022, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '김영수', 'Kim Young Su', '金英洙', 11,
     50, '010-5846-1001', '20020315-3294467', '남', '2024-01-15', NULL, '서울특별시 강남구', '대한민국', '2002-03-15',
     'youngsu.kim@example.com', NULL, '재직', 60000000),
    (202412023, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '박지영', 'Park Ji Young', '朴智英', 12,
     50, '010-2156-1002', '20040211-4234567', '여', '2024-02-27', NULL, '서울특별시 서초구', '대한민국', '2004-02-11',
     'jiyoung.park@example.com', NULL, '재직', 58000000),
    (202413024, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '이철수', 'Lee Chul Su', '李哲洙', 13,
     50, '010-3550-1003', '20040303-3251567', '남', '2024-03-15', NULL, '서울특별시 강북구', '대한민국', '2004-03-03',
     'chulsu.lee@example.com', NULL, '재직', 57000000);

/* 지급항목 - 고정 */
INSERT INTO `earning_category` (`id`, `name`, `is_tax`)
VALUES (101, '기본급여', 'Y'),
       (102, '상여', 'Y'),
       (200, '식대', 'N'),
       (201, '연장근로수당', 'N'),
       (202, '휴일근로수당', 'N'),
       (203, '가족수당', 'Y'),
       (204, '명절상여', 'Y'),
       (205, '직책수당', 'Y');
/* 공제항목 - 고정*/
INSERT INTO deduction_category (`id`, `name`, `deduction_rate`)
VALUES (501, '국민연금', 4.5),
       (502, '건강보험', 3.545),
       (503, '고용보험', 12.95),
       (504, '장기요양보험', 0.9);

INSERT INTO department_box(department_id, `order`, `name`, last_editor_id, last_edit_date)
values (11, 1, '부서함', 202311018, '2024-05-06');
INSERT INTO approval_document_template(id, title, document, `status`, last_editor_id, folder_id)
VALUES (1, '템플릿 1', "템플릿 테스트", 1, 202311018, 1);
INSERT INTO approval_document (id, document_template_id, title, draft_employee_id, approval_date, last_approval_date,
                               department_id, document, `status`)
VALUES (1, 1, 'Document 1', 202215017, '2023-01-01', '2023-01-05', 12, 'document1.pdf', 1), -- 승인
       (2, 1, 'Document 2', 202113015, '2023-02-01', '2023-02-10', 13, 'document2.pdf', 2), -- 반려
       (3, 1, 'Document 3', 202012014, '2023-03-01', '2023-03-15', 11, 'document3.pdf', 0), -- 진행
       (4, 1, 'Document 4', 201911013, '2023-04-01', '2023-04-20', 12, 'document4.pdf', 0), -- 진행
       (5, 1, 'Document 5', 202311018, '2023-05-01', '2023-05-25', 11, 'document5.pdf', 1); -- 승인
INSERT INTO approval_line (document_id, employee_id, handling_date, id, `order`, `status`)
VALUES (1, 201911013, '2024-01-01', 1, 1, 2),
       (1, 202012014, '2024-01-02', 2, 2, 1),
       (1, 202113015, null, 3, 3, 0);
INSERT INTO department_document (document_template_id, department_box_id)
VALUES (1, 1);
