use
officeerp;

-- department 테이블에 더미 데이터 삽입
INSERT INTO `department` (`id`, `name`)
VALUES (1, 'CEO'),
       (11, '인사'),
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

-- 새로운 사장 데이터를 삽입
INSERT INTO `employee` (`employee_id`, `password`, `name`, `english_name`, `hanja_name`, `department_id`,
                        `job_id`, `phone`, `personal_id`, `gender`, `hire_date`, `end_date`,
                        `address`, `nationality`, `birth_date`, `email`, `self_introduction`,
                        `employment_status`, `annual_salary`)
VALUES
    (1, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '권은지', 'Kwon Eun Ji', '權恩智', 1,
     11, '010-1234-5678', '820101-1234567', '여', '2024-07-10', NULL, '서울특별시 종로구', '대한민국', '1982-01-01',
     'eodlf333@gmail.com', NULL, '재직', 100000000);

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


INSERT INTO annual_leave_category (id, name, type)
VALUES
    (1, '기본발생', '부여'),
    (2, '1년미만', '부여'),
    (3, '보상', '부여'),
    (4, '대체', '부여'),
    (5, '기본발생', '소진'),
    (6, '1년미만', '소진'),
    (7, '보상', '소진'),
    (8, '대체', '소진'),
    (9, '초기화', '소진');

INSERT INTO annual_leave_grant_record (id, amount, date, expiration_date, annual_leave_category_id, employee_id)
VALUES
-- 2012년에 입사한 사원들
(1, 15, '2013-01-15', '2014-01-15', 1, 201211001),
(2, 15, '2013-02-27', '2014-02-27', 1, 201212002),
-- 2013년에 입사한 사원들
(3, 15, '2014-04-02', '2015-04-02', 1, 201313003),
(4, 15, '2014-11-23', '2015-11-23', 1, 201314004),
-- 2014년에 입사한 사원들
(5, 15, '2015-05-27', '2016-05-27', 1, 201414005),
-- 2015년에 입사한 사원들
(6, 1, '2015-08-20', '2016-08-20', 2, 201515006),
(7, 1, '2015-09-20', '2016-09-20', 2, 201515006),
(8, 1, '2015-10-20', '2016-10-20', 2, 201515006),
(9, 1, '2015-11-20', '2016-11-20', 2, 201515006),
(10, 1, '2015-12-20', '2016-12-20', 2, 201515006),
(11, 15, '2016-07-20', '2017-07-20', 1, 201515006),
(12, 1, '2015-04-21', '2016-04-21', 2, 201511007),
(13, 1, '2015-05-21', '2016-05-21', 2, 201511007),
(14, 1, '2015-06-21', '2016-06-21', 2, 201511007),
(15, 1, '2015-07-21', '2016-07-21', 2, 201511007),
(16, 1, '2015-08-21', '2016-08-21', 2, 201511007),
(17, 15, '2016-03-21', '2017-03-21', 1, 201511007),
-- 2016년에 입사한 사원들
(18, 1, '2016-09-24', '2017-09-24', 2, 201615008),
(19, 1, '2016-10-24', '2017-10-24', 2, 201615008),
(20, 1, '2016-11-24', '2017-11-24', 2, 201615008),
(21, 1, '2016-12-24', '2017-12-24', 2, 201615008),
(22, 1, '2017-01-24', '2018-01-24', 2, 201615008),
(23, 15, '2017-08-24', '2018-08-24', 1, 201615008),
(24, 1, '2016-02-02', '2017-02-02', 2, 201612009),
(25, 1, '2016-03-02', '2017-03-02', 2, 201612009),
(26, 1, '2016-04-02', '2017-04-02', 2, 201612009),
(27, 1, '2016-05-02', '2017-05-02', 2, 201612009),
(28, 1, '2016-06-02', '2017-06-02', 2, 201612009),
(29, 15, '2017-01-02', '2018-01-02', 1, 201612009),
-- 2017년에 입사한 사원들
(30, 1, '2017-12-07', '2018-12-07', 2, 201713010),
(31, 1, '2018-01-07', '2019-01-07', 2, 201713010),
(32, 1, '2018-02-07', '2019-02-07', 2, 201713010),
(33, 1, '2018-03-07', '2019-03-07', 2, 201713010),
(34, 1, '2018-04-07', '2019-04-07', 2, 201713010),
(35, 15, '2018-11-07', '2019-11-07', 1, 201713010),
(36, 1, '2017-09-26', '2018-09-26', 2, 201714011),
(37, 1, '2017-10-26', '2018-10-26', 2, 201714011),
(38, 1, '2017-11-26', '2018-11-26', 2, 201714011),
(39, 1, '2017-12-26', '2018-12-26', 2, 201714011),
(40, 1, '2018-01-26', '2019-01-26', 2, 201714011),
(41, 15, '2018-08-26', '2019-08-26', 1, 201714011),
-- 2018년에 입사한 사원들
(42, 1, '2018-03-13', '2019-03-13', 2, 201815012),
(43, 1, '2018-04-13', '2019-04-13', 2, 201815012),
(44, 1, '2018-05-13', '2019-05-13', 2, 201815012),
(45, 1, '2018-06-13', '2019-06-13', 2, 201815012),
(46, 1, '2018-07-13', '2019-07-13', 2, 201815012),
(47, 15, '2019-02-13', '2020-02-13', 1, 201815012),
-- 2019년에 입사한 사원들
(48, 1, '2019-07-01', '2020-07-01', 2, 201911013),
(49, 1, '2019-08-01', '2020-08-01', 2, 201911013),
(50, 1, '2019-09-01', '2020-09-01', 2, 201911013),
-- 유효기간 만료
(51, 1, '2012-06-15', '2013-06-15', 4, 201211001),
(52, 1, '2012-07-17', '2013-07-17', 4, 202215017),
(53, 1, '2012-07-19', '2013-07-19', 4, 202312019),
(54, 1, '2013-08-09', '2014-08-09', 4, 201515006),
(55, 1, '2013-08-21', '2014-08-21', 4, 202311018),
(56, 1, '2014-09-14', '2015-09-14', 4, 201511007),
(57, 1, '2014-09-22', '2015-09-22', 4, 202312019),
(58, 1, '2015-09-23', '2016-09-23', 4, 201314004),
(59, 1, '2015-10-31', '2016-10-31', 4, 202215017),
(60, 1, '2016-10-12', '2017-10-12', 4, 201414005),
(61, 1, '2016-11-22', '2017-11-22', 4, 202311018),
(62, 1, '2017-12-08', '2018-12-08', 4, 201713010),
(63, 1, '2017-12-30', '2018-12-30', 4, 202312019),
(64, 1, '2018-01-14', '2019-01-14', 4, 201212002),
(65, 1, '2018-01-18', '2019-01-18', 4, 201615008),
(66, 1, '2018-05-14', '2019-05-14', 4, 201714011),
(67, 1, '2018-06-23', '2019-06-23', 4, 201314004),
(68, 1, '2018-11-19', '2019-11-19', 4, 202312019),
(69, 1, '2019-01-19', '2020-01-19', 4, 202312019),
(70, 1, '2019-04-11', '2020-04-11', 4, 202311018),
(71, 1, '2019-08-15', '2020-08-15', 4, 201615008),
(72, 1, '2019-10-17', '2020-10-17', 4, 201313003),
(73, 1, '2020-03-27', '2021-03-27', 4, 201314004),
(74, 1, '2020-06-22', '2021-06-22', 4, 201511007),
(75, 1, '2020-08-17', '2021-08-17', 4, 201714011),
(76, 1, '2021-04-17', '2022-04-17', 4, 201211001),
(77, 1, '2022-05-15', '2023-05-15', 4, 202113015),
(78, 1, '2023-01-11', '2024-01-11', 4, 202215017),
(79, 1, '2023-03-15', '2024-03-15', 4, 202313020),
(80, 1, '2023-06-22', '2024-06-22', 4, 201511007),
(81, 1, '2023-09-14', '2024-09-14', 4, 201314004),
(82, 1, '2023-11-19', '2024-11-19', 4, 202311018),
(83, 1, '2024-01-14', '2025-01-14', 4, 202312019),
-- 보상 연차
(84, 1, '2012-06-15', '2013-06-15', 3, 201211001),
(85, 1, '2012-07-17', '2013-07-17', 3, 202215017),
(86, 1, '2012-07-19', '2013-07-19', 3, 202312019),
(87, 1, '2013-08-09', '2014-08-09', 3, 201515006),
(88, 1, '2013-08-21', '2014-08-21', 3, 202311018),
(89, 1, '2014-09-14', '2015-09-14', 3, 201511007),
(90, 1, '2014-09-22', '2015-09-22', 3, 202312019),
(91, 1, '2015-09-23', '2016-09-23', 3, 201314004),
(92, 1, '2015-10-31', '2016-10-31', 3, 202215017),
(93, 1, '2016-10-12', '2017-10-12', 3, 201414005),
(94, 1, '2016-11-22', '2017-11-22', 3, 202311018),
(95, 1, '2017-12-08', '2018-12-08', 3, 201713010),
(96, 1, '2017-12-30', '2018-12-30', 3, 202312019),
(97, 1, '2018-01-14', '2019-01-14', 3, 201212002),
(98, 1, '2018-01-18', '2019-01-18', 3, 201615008),
(99, 1, '2018-05-14', '2019-05-14', 3, 201714011),
(100, 1, '2018-06-23', '2019-06-23', 3, 201314004);

INSERT INTO annual_leave_used_record (id, used_start_date, used_end_date, leave_session, used_amount, remaining_amount, employee_id, annual_leave_category_id, annual_leave_grant_record_id, approval_document_id)
VALUES
    -- 기존 연차 사용 데이터
    (1, '2024-06-28', '2024-06-28', '오전', 0.5, 14.5, 201511007, 5, 1, NULL),
    (2, '2024-06-25', '2024-06-25', '오후', 0.5, 13.5, 201511007, 6, 2, NULL),
    (3, '2024-06-22', '2024-06-22', '전체', 1, 12, 201511007, 7, 3, NULL),
    (4, '2024-06-30', '2024-06-30', '오후', 0.5, 11.5, 201511007, 5, 4, NULL),
    (5, '2024-06-29', '2024-06-29', '오전', 0.5, 10.5, 202314021, 6, 5, NULL),
    (6, '2024-06-28', '2024-06-28', '전체', 1, 9, 202314021, 7, 6, NULL),
    (7, '2024-06-27', '2024-06-27', '오후', 0.5, 8.5, 202314021, 5, 7, NULL),
    (8, '2024-06-26', '2024-06-26', '오전', 0.5, 7.5, 202314021, 6, 8, NULL),
    (9, '2024-06-25', '2024-06-25', '전체', 1, 6, 202314021, 7, 9, NULL),
    (10, '2024-06-24', '2024-06-24', '오후', 0.5, 5.5, 202314021, 5, 10, NULL),
    (11, '2024-06-23', '2024-06-23', '오전', 0.5, 4.5, 202314021, 6, 11, NULL),
    (12, '2024-06-22', '2024-06-22', '오후', 0.5, 3.5, 202314021, 7, 12, NULL),
    (13, '2024-06-21', '2024-06-21', '전체', 1, 2.5, 202314021, 5, 13, NULL),
    (14, '2024-06-20', '2024-06-20', '오후', 0.5, 2, 202314021, 6, 14, NULL),
    (15, '2024-06-19', '2024-06-19', '오전', 0.5, 1.5, 202314021, 7, 15, NULL),
    (16, '2024-06-18', '2024-06-18', '오후', 0.5, 1, 202314021, 5, 16, NULL),
    (17, '2024-06-17', '2024-06-17', '오전', 0.5, 0.5, 202314021, 6, 17, NULL),
    (18, '2024-06-16', '2024-06-16', '전체', 1, 14, 202314021, 7, 18, NULL),
    (19, '2024-06-15', '2024-06-15', '오전', 0.5, 13.5, 202314021, 5, 19, NULL),
    (20, '2024-06-14', '2024-06-14', '오후', 0.5, 13, 202314021, 6, 20, NULL),
    (21, '2024-06-13', '2024-06-13', '오전', 0.5, 12.5, 202314021, 7, 21, NULL),
    (22, '2024-06-12', '2024-06-12', '오후', 0.5, 12, 202314021, 5, 22, NULL),
    (23, '2024-06-11', '2024-06-11', '전체', 1, 11, 202314021, 6, 23, NULL),
    (24, '2024-06-10', '2024-06-10', '오전', 0.5, 10.5, 202314021, 7, 24, NULL),
    (25, '2024-06-09', '2024-06-09', '오후', 0.5, 10, 202314021, 5, 25, NULL),
    (26, '2024-06-08', '2024-06-08', '오전', 0.5, 9.5, 202314021, 6, 26, NULL),
    (27, '2024-06-07', '2024-06-07', '오후', 0.5, 9, 202314021, 7, 27, NULL),
    (28, '2024-06-06', '2024-06-06', '오전', 0.5, 8.5, 202314021, 5, 28, NULL),
    (29, '2024-06-05', '2024-06-05', '오후', 0.5, 8, 202314021, 6, 29, NULL),
    (30, '2024-06-04', '2024-06-04', '오전', 0.5, 7.5, 202314021, 7, 30, NULL),
    (31, '2024-06-03', '2024-06-03', '오후', 0.5, 7, 202314021, 5, 31, NULL),
    (32, '2024-06-02', '2024-06-02', '오전', 0.5, 6.5, 202314021, 6, 32, NULL),
    (33, '2024-06-01', '2024-06-01', '전체', 1, 6, 202314021, 7, 33, NULL),
    (34, '2024-05-31', '2024-05-31', '오후', 0.5, 5.5, 202314021, 5, 34, NULL),
    (35, '2024-05-30', '2024-05-30', '오전', 0.5, 5, 202314021, 6, 35, NULL),
    (36, '2024-05-29', '2024-05-29', '오후', 0.5, 4.5, 202314021, 7, 36, NULL),
    (37, '2024-05-28', '2024-05-28', '오전', 0.5, 4, 202314021, 5, 37, NULL),
    (38, '2024-05-27', '2024-05-27', '오후', 0.5, 3.5, 202314021, 6, 38, NULL),
    (39, '2024-05-26', '2024-05-26', '오전', 0.5, 3, 202314021, 7, 39, NULL),
    (40, '2024-05-25', '2024-05-25', '오후', 0.5, 2.5, 202314021, 5, 40, NULL),
    (41, '2024-05-24', '2024-05-24', '오전', 0.5, 2, 202314021, 6, 41, NULL),
    (42, '2024-05-23', '2024-05-23', '오후', 0.5, 1.5, 202314021, 7, 42, NULL),
    (43, '2024-05-22', '2024-05-22', '오전', 0.5, 1, 202314021, 5, 43, NULL),
    (44, '2024-05-21', '2024-05-21', '오후', 0.5, 0.5, 202314021, 6, 44, NULL),
    (45, '2024-05-20', '2024-05-20', '오전', 0.5, 0, 202314021, 7, 45, NULL),
    (46, '2024-05-19', '2024-05-19', '오후', 0.5, 14.5, 202314021, 5, 46, NULL),
    (47, '2024-05-18', '2024-05-18', '오전', 0.5, 14, 202314021, 6, 47, NULL),
    (48, '2024-05-17', '2024-05-17', '오후', 0.5, 13.5, 202314021, 7, 48, NULL),
    (49, '2024-05-16', '2024-05-16', '오전', 0.5, 13, 202314021, 5, 49, NULL),
    (50, '2024-05-15', '2024-05-15', '오후', 0.5, 12.5, 202314021, 6, 50, NULL),
    -- 대체 연차 사용
    (51, '2024-07-01', '2024-07-01', '오전', 0.5, 0.5, 201211001, 4, 51, NULL),
    (52, '2024-07-02', '2024-07-02', '오후', 0.5, 0.5, 202215017, 4, 52, NULL),
    (53, '2024-07-03', '2024-07-03', '전체', 1, 0, 202312019, 4, 53, NULL),
    (54, '2024-07-04', '2024-07-04', '오전', 0.5, 0.5, 201511007, 4, 54, NULL),
    (55, '2024-07-05', '2024-07-05', '오후', 0.5, 0.5, 202311018, 4, 55, NULL),
    (56, '2024-07-06', '2024-07-06', '전체', 1, 0, 201511007, 4, 56, NULL),
    (57, '2024-07-07', '2024-07-07', '오전', 0.5, 0.5, 202312019, 4, 57, NULL),
    (58, '2024-07-08', '2024-07-08', '오후', 0.5, 0.5, 201314004, 4, 58, NULL),
    (59, '2024-07-09', '2024-07-09', '전체', 1, 0, 202215017, 4, 59, NULL),
    (60, '2024-07-10', '2024-07-10', '오전', 0.5, 0.5, 201414005, 4, 60, NULL),
    (61, '2024-07-11', '2024-07-11', '오후', 0.5, 0.5, 202311018, 4, 61, NULL),
    (62, '2024-07-12', '2024-07-12', '전체', 1, 0, 201713010, 4, 62, NULL),
    (63, '2024-07-13', '2024-07-13', '오전', 0.5, 0.5, 202312019, 4, 63, NULL),
    (64, '2024-07-14', '2024-07-14', '오후', 0.5, 0.5, 201212002, 4, 64, NULL),
    (65, '2024-07-15', '2024-07-15', '전체', 1, 0, 201615008, 4, 65, NULL),
    (66, '2024-07-16', '2024-07-16', '오전', 0.5, 0.5, 201714011, 4, 66, NULL),
    (67, '2024-07-17', '2024-07-17', '오후', 0.5, 0.5, 201314004, 4, 67, NULL),
    (68, '2024-07-18', '2024-07-18', '전체', 1, 0, 202312019, 4, 68, NULL),
    (69, '2024-07-19', '2024-07-19', '오전', 0.5, 0.5, 202312019, 4, 69, NULL),
    (70, '2024-07-20', '2024-07-20', '오후', 0.5, 0.5, 202311018, 4, 70, NULL),
    (71, '2024-07-21', '2024-07-21', '전체', 1, 0, 201615008, 4, 71, NULL),
    (72, '2024-07-22', '2024-07-22', '오전', 0.5, 0.5, 201313003, 4, 72, NULL),
    (73, '2024-07-23', '2024-07-23', '오후', 0.5, 0.5, 201314004, 4, 73, NULL),
    (74, '2024-07-24', '2024-07-24', '전체', 1, 0, 201511007, 4, 74, NULL),
    (75, '2024-07-25', '2024-07-25', '오전', 0.5, 0.5, 201714011, 4, 75, NULL),
    (76, '2024-07-26', '2024-07-26', '오후', 0.5, 0.5, 201211001, 4, 76, NULL),
    (77, '2024-07-27', '2024-07-27', '전체', 1, 0, 202113015, 4, 77, NULL),
    (78, '2024-07-28', '2024-07-28', '오전', 0.5, 0.5, 202215017, 4, 78, NULL),
    (79, '2024-07-29', '2024-07-29', '오후', 0.5, 0.5, 202313020, 4, 79, NULL),
    (80, '2024-07-30', '2024-07-30', '전체', 1, 0, 201511007, 4, 80, NULL),
    (81, '2024-07-31', '2024-07-31', '오전', 0.5, 0.5, 201314004, 4, 81, NULL),
    (82, '2024-08-01', '2024-08-01', '오후', 0.5, 0.5, 202311018, 4, 82, NULL),
    (83, '2024-08-02', '2024-08-02', '전체', 1, 0, 202312019, 4, 83, NULL),
    -- 보상 연차 사용
    (84, '2024-08-03', '2024-08-03', '오전', 0.5, 0.5, 201211001, 3, 84, NULL),
    (85, '2024-08-04', '2024-08-04', '오후', 0.5, 0.5, 202215017, 3, 85, NULL),
    (86, '2024-08-05', '2024-08-05', '전체', 1, 0, 202312019, 3, 86, NULL),
    (87, '2024-08-06', '2024-08-06', '오전', 0.5, 0.5, 201511007, 3, 87, NULL),
    (88, '2024-08-07', '2024-08-07', '오후', 0.5, 0.5, 202311018, 3, 88, NULL),
    (89, '2024-08-08', '2024-08-08', '전체', 1, 0, 201511007, 3, 89, NULL),
    (90, '2024-08-09', '2024-08-09', '오전', 0.5, 0.5, 202312019, 3, 90, NULL),
    (91, '2024-08-10', '2024-08-10', '오후', 0.5, 0.5, 201314004, 3, 91, NULL),
    (92, '2024-08-11', '2024-08-11', '전체', 1, 0, 202215017, 3, 92, NULL),
    (93, '2024-08-12', '2024-08-12', '오전', 0.5, 0.5, 201414005, 3, 93, NULL),
    (94, '2024-08-13', '2024-08-13', '오후', 0.5, 0.5, 202311018, 3, 94, NULL),
    (95, '2024-08-14', '2024-08-14', '전체', 1, 0, 201713010, 3, 95, NULL),
    (96, '2024-08-15', '2024-08-15', '오전', 0.5, 0.5, 202312019, 3, 96, NULL),
    (97, '2024-08-16', '2024-08-16', '오후', 0.5, 0.5, 201212002, 3, 97, NULL),
    (98, '2024-08-17', '2024-08-17', '전체', 1, 0, 201615008, 3, 98, NULL),
    (99, '2024-08-18', '2024-08-18', '오전', 0.5, 0.5, 201714011, 3, 99, NULL),
    (100, '2024-08-19', '2024-08-19', '오후', 0.5, 0.5, 201314004, 3, 100, NULL);

INSERT INTO attendance_record (id, date, check_in_time, check_out_time, employee_id)
VALUES
    (1, '2012-02-03', '09:00:00', '18:00:00', 202012014),
    (2, '2012-04-12', '09:00:00', '18:00:00', 201211001),
    (3, '2012-06-12', '09:00:00', '18:00:00', 201414005),
    (4, '2012-08-23', '09:00:00', '18:00:00', 202215017),
    (5, '2012-09-27', '09:00:00', '18:00:00', 201615008),
    (6, '2013-04-20', '09:00:00', '18:00:00', 202113015),
    (7, '2013-07-23', '09:00:00', '18:00:00', 201212002),
    (8, '2013-08-14', '09:00:00', '18:00:00', 201515006),
    (9, '2013-10-09', '09:00:00', '18:00:00', 202311018),
    (10, '2014-07-29', '09:00:00', '18:00:00', 202114016),
    (11, '2014-11-03', '09:00:00', '18:00:00', 201511007),
    (12, '2014-12-24', '09:00:00', '18:00:00', 202312019),
    (13, '2015-01-16', '09:00:00', '18:00:00', 201615008),
    (14, '2015-02-14', '09:00:00', '18:00:00', 202313020),
    (15, '2015-10-05', '09:00:00', '18:00:00', 202215017),
    (16, '2015-11-17', '09:00:00', '18:00:00', 201314004),
    (17, '2016-01-25', '09:00:00', '18:00:00', 201414005),
    (18, '2016-03-21', '09:00:00', '18:00:00', 201612009),
    (19, '2016-04-16', '09:00:00', '18:00:00', 202314021),
    (20, '2016-11-19', '09:00:00', '18:00:00', 202311018),
    (21, '2017-01-22', '09:00:00', '18:00:00', 202312019),
    (22, '2017-03-12', '09:00:00', '18:00:00', 201515006),
    (23, '2017-05-27', '09:00:00', '18:00:00', 201713010),
    (24, '2017-06-20', '09:00:00', '18:00:00', 201211001),
    (25, '2017-11-17', '09:00:00', '18:00:00', 201314004),
    (26, '2018-03-15', '09:00:00', '18:00:00', 202313020),
    (27, '2018-05-08', '09:00:00', '18:00:00', 201511007),
    (28, '2018-07-08', '09:00:00', '18:00:00', 201714011),
    (29, '2018-08-15', '09:00:00', '18:00:00', 201615008),
    (30, '2018-11-19', '09:00:00', '18:00:00', 202312019),
    (31, '2019-06-18', '09:00:00', '18:00:00', 202314021),
    (32, '2019-08-15', '09:00:00', '18:00:00', 201615008),
    (33, '2019-09-14', '09:00:00', '18:00:00', 201815012),
    (34, '2020-08-23', '09:00:00', '18:00:00', 201211001),
    (35, '2020-10-21', '09:00:00', '18:00:00', 201612009),
    (36, '2021-01-30', '09:00:00', '18:00:00', 202012014),
    (37, '2021-11-04', '09:00:00', '18:00:00', 201212002),
    (38, '2021-12-30', '09:00:00', '18:00:00', 201713010),
    (39, '2022-01-11', '09:00:00', '18:00:00', 201714011),
    (40, '2022-02-17', '09:00:00', '18:00:00', 201313003),
    (41, '2022-04-05', '09:00:00', '18:00:00', 202113015),
    (42, '2023-03-27', '09:00:00', '18:00:00', 201815012),
    (43, '2023-04-29', '09:00:00', '18:00:00', 201314004),
    (44, '2023-06-14', '09:00:00', '18:00:00', 201911013),
    (45, '2023-07-14', '09:00:00', '18:00:00', 201511007),
    (46, '2023-08-29', '09:00:00', '18:00:00', 201212002),
    (47, '2023-10-17', '09:00:00', '18:00:00', 201313003),
    (48, '2023-12-22', '09:00:00', '18:00:00', 201314004),
    (49, '2024-02-26', '09:00:00', '18:00:00', 201414005),
    (50, '2024-06-30', '09:00:00', '18:00:00', 201211001);

INSERT INTO over_time_and_lateness_record (id, type, date, start_time, end_time, approval_document_id, employee_id)
VALUES
    (1, '연장', '2012-06-15', '18:00:00', '20:00:00', NULL, 201211001),
    (2, '휴일', '2012-07-17', '09:00:00', '13:00:00', NULL, 202215017),
    (3, '휴일', '2012-07-19', '09:00:00', '13:00:00', NULL, 202312019),
    (4, '연장', '2013-08-09', '18:00:00', '21:00:00', NULL, 201515006),
    (5, '휴일', '2013-08-21', '09:00:00', '13:00:00', NULL, 202311018),
    (6, '지각', '2013-08-29', '09:05:00', '09:05:00', NULL, 202113015),
    (7, '연장', '2014-09-14', '18:00:00', '21:00:00', NULL, 201511007),
    (8, '지각', '2014-09-19', '09:15:00', '09:15:00', NULL, 201313003),
    (9, '연장', '2014-09-22', '18:00:00', '21:00:00', NULL, 202312019),
    (10, '지각', '2014-09-30', '09:20:00', '09:20:00', NULL, 201615008),
    (11, '연장', '2015-09-23', '18:00:00', '21:00:00', NULL, 201314004),
    (12, '휴일', '2015-10-31', '09:00:00', '13:00:00', NULL, 202215017),
    (13, '지각', '2015-11-04', '09:25:00', '09:25:00', NULL, 201515006),
    (14, '연장', '2016-10-12', '18:00:00', '20:00:00', NULL, 201414005),
    (15, '지각', '2016-11-12', '09:05:00', '09:05:00', NULL, 201612009),
    (16, '휴일', '2016-11-22', '09:00:00', '13:00:00', NULL, 202311018),
    (17, '지각', '2016-11-25', '09:10:00', '09:10:00', NULL, 202312019),
    (18, '연장', '2017-12-08', '18:00:00', '21:00:00', NULL, 201713010),
    (19, '지각', '2017-12-13', '09:20:00', '09:20:00', NULL, 201211001),
    (20, '연장', '2017-12-30', '18:00:00', '20:00:00', NULL, 202312019),
    (21, '휴일', '2018-01-14', '09:00:00', '13:00:00', NULL, 201212002),
    (22, '휴일', '2018-01-18', '09:00:00', '13:00:00', NULL, 201615008),
    (23, '지각', '2018-02-27', '09:15:00', '09:15:00', NULL, 201313003),
    (24, '휴일', '2018-05-14', '09:00:00', '13:00:00', NULL, 201714011),
    (25, '연장', '2018-06-23', '18:00:00', '21:00:00', NULL, 201314004),
    (26, '연장', '2018-11-19', '18:00:00', '21:00:00', NULL, 202312019),
    (27, '연장', '2019-01-19', '18:00:00', '21:00:00', NULL, 202312019),
    (28, '지각', '2019-02-19', '09:20:00', '09:20:00', NULL, 202314021),
    (29, '연장', '2019-04-11', '18:00:00', '22:00:00', NULL, 202311018),
    (30, '지각', '2019-07-11', '09:25:00', '09:25:00', NULL, 202215017),
    (31, '연장', '2019-08-15', '18:00:00', '22:00:00', NULL, 201615008),
    (32, '휴일', '2019-10-17', '09:00:00', '13:00:00', NULL, 201313003),
    (33, '연장', '2020-03-27', '18:00:00', '21:00:00', NULL, 201314004),
    (34, '연장', '2020-06-22', '18:00:00', '22:00:00', NULL, 201511007),
    (35, '휴일', '2020-08-17', '09:00:00', '13:00:00', NULL, 201714011),
    (36, '지각', '2021-03-19', '09:15:00', '09:15:00', NULL, 201714011),
    (37, '연장', '2021-04-17', '18:00:00', '21:00:00', NULL, 201211001),
    (38, '연장', '2022-05-15', '18:00:00', '21:00:00', NULL, 202113015),
    (39, '휴일', '2022-06-20', '09:00:00', '13:00:00', NULL, 202114016),
    (40, '연장', '2023-01-11', '18:00:00', '20:00:00', NULL, 202215017),
    (41, '연장', '2023-03-15', '18:00:00', '22:00:00', NULL, 202313020),
    (42, '휴일', '2023-04-14', '09:00:00', '13:00:00', NULL, 202312019),
    (43, '휴일', '2023-05-16', '09:00:00', '13:00:00', NULL, 202311018),
    (44, '연장', '2023-06-22', '18:00:00', '22:00:00', NULL, 201511007),
    (45, '지각', '2023-07-14', '09:05:00', '09:05:00', NULL, 202113015),
    (46, '연장', '2023-09-14', '18:00:00', '21:00:00', NULL, 201314004),
    (47, '휴일', '2023-11-19', '09:00:00', '13:00:00', NULL, 202311018),
    (48, '휴일', '2024-01-14', '09:00:00', '13:00:00', NULL, 202312019),
    (49, '지각', '2024-02-16', '09:25:00', '09:25:00', NULL, 201511007),
    (50, '연장', '2024-06-30', '18:00:00', '21:00:00', NULL, 201211001);

