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


INSERT INTO `employee` (`employee_id`, `password`, `name`, `english_name`, `hanja_name`, `department_id`,
                        `job_id`, `phone`, `personal_id`, `gender`, `hire_date`, `end_date`,
                        `address`, `nationality`, `birth_date`, `email`, `self_introduction`,
                        `employment_status`, `annual_salary`)
VALUES (202314054, '$2a$10$UjIJUhFUnSebRNWkVhqDf.h7NhBAbKhiSLs16Bk9j0CPgwyfKYJdi', '이영희', 'Lee Young Hee', '李英熙', 14,
        0600, '010-3456-7890', '820303-3456789', '여', '2023-03-27', NULL,
        '대구광역시 중구', '대한민국', '1982-03-03', 'younghee.lee@example.com', NULL,
        '재직', 30000000);
-- 비밀번호 lee0hee
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
INSERT INTO `deducation_category` (`id`, `name`, `deducation_rate`)
VALUES (501, '국민연금', 4.5),
       (502, '건강보험', 3.545),
       (503, '고용보험', 12.95),
       (504, '장기요양보험', 0.9);
INSERT INTO department_box(department_id, `order`, `name`, last_editor_id, last_edit_date)
values (11, 1, '부서함', 201511003, '2024-05-06');
INSERT INTO approval_document_template(id, title, document, `status`, last_editor_id, folder_id)
VALUES (1, '템플릿 1', "템플릿 테스트", 1, 201515002, 1);
INSERT INTO approval_document (id, document_template_id, title, draft_employee_id, approval_date, last_approval_date,
                               department_id, document, `status`)
VALUES (1, 1, 'Document 1', 201515003, '2023-01-01', '2023-01-05', 12, 'document1.pdf', 1), -- 승인
       (2, 1, 'Document 2', 202314054, '2023-02-01', '2023-02-10', 13, 'document2.pdf', 2), -- 반려
       (3, 1, 'Document 3', 201512002, '2023-03-01', '2023-03-15', 11, 'document3.pdf', 0), -- 진행
       (4, 1, 'Document 4', 201511003, '2023-04-01', '2023-04-20', 12, 'document4.pdf', 0), -- 진행
       (5, 1, 'Document 5', 201515002, '2023-05-01', '2023-05-25', 11, 'document5.pdf', 1); -- 승인
INSERT INTO approval_line (document_id, employee_id, handling_date, id, `order`, `status`)
VALUES (1, 202314054, '2024-01-01', 1, 1, 2),
       (1, 201515002, '2024-01-02', 2, 2, 1),
       (1, 201515003, null, 3, 3, 0);
INSERT INTO department_document (document_template_id, department_box_id)
VALUES (1, 1);


################################## 근태관리 더미데이터 ##################################
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
(9, '유효기간 만료', '소진');

INSERT INTO annual_leave_grant_record (id, amount, date, expiration_date, annual_leave_category_id, employee_id)
VALUES
(1, 15, '2015-01-01', '2016-01-01', 1, 201511001),
(2, 15, '2015-02-01', '2016-02-01', 1, 201511002),
(3, 15, '2015-03-01', '2016-03-01', 1, 201511003),
(4, 15, '2015-04-01', '2016-04-01', 1, 201511004),
(5, 15, '2015-05-01', '2016-05-01', 1, 201512001),
(6, 15, '2015-06-01', '2016-06-01', 1, 201512002),
(7, 15, '2015-07-01', '2016-07-01', 1, 201512003),
(8, 15, '2015-08-01', '2016-08-01', 1, 201513001),
(9, 15, '2015-09-01', '2016-09-01', 1, 201513002),
(10, 15, '2015-10-01', '2016-10-01', 1, 201513003),
(11, 15, '2015-11-01', '2016-11-01', 1, 201514001),
(12, 15, '2015-12-01', '2016-12-01', 1, 201514002),
(13, 15, '2015-12-15', '2016-12-15', 1, 201514003),
(14, 15, '2015-12-18', '2016-12-18', 1, 201515001),
(15, 15, '2015-12-20', '2016-12-20', 1, 201515002),
(16, 15, '2015-12-22', '2016-12-22', 1, 201515003),
(17, 15, '2015-12-24', '2016-12-24', 1, 201511005),
(18, 15, '2015-12-26', '2016-12-26', 1, 201512004),
(19, 15, '2015-12-28', '2016-12-28', 1, 201513004);

INSERT INTO annual_leave_used_record (id, used_start_date, used_end_date, leave_session, used_amount,
                                      remaining_amount, employee_id, annual_leave_category_id, annual_leave_grant_record_id,
                                      approval_document_id)
VALUES
(1, '2024-06-28', '2024-06-28', '오전', 1, 14, 201511001, 5, 1, NULL),
(2, '2024-06-25', '2024-06-25', '오후', 1, 13, 201511002, 6, 2, NULL),
(3, '2024-06-22', '2024-06-22', '오전', 1, 12, 201511003, 7, 3, NULL),
(4, '2024-06-30', '2024-06-30', '오후', 1, 11, 201511004, 5, 4, NULL),
(5, '2024-06-29', '2024-06-29', '오전', 1, 10, 201512001, 6, 5, NULL),
(6, '2024-06-28', '2024-06-28', '오전', 1, 9, 201512002, 7, 6, NULL),
(7, '2024-06-27', '2024-06-27', '오후', 1, 8, 201512003, 5, 7, NULL),
(8, '2024-06-26', '2024-06-26', '오전', 1, 7, 201513001, 6, 8, NULL),
(9, '2024-06-25', '2024-06-25', '오전', 1, 6, 201513002, 7, 9, NULL);

INSERT INTO attendance_record (id, date, check_in_time, check_out_time, employee_id)
VALUES
(1, '2024-06-30', '09:00:00', '18:00:00', 201511001),
(2, '2024-06-27', '09:00:00', '18:00:00', 201511002),
(3, '2024-06-24', '09:00:00', '18:00:00', 201511003),
(4, '2024-06-23', '09:00:00', '18:00:00', 201511004),
(5, '2024-06-22', '09:00:00', '18:00:00', 201512001),
(6, '2024-06-21', '09:00:00', '18:00:00', 201512002),
(7, '2024-06-20', '09:00:00', '18:00:00', 201512003),
(8, '2024-06-19', '09:00:00', '18:00:00', 201513001),
(9, '2024-06-18', '09:00:00', '18:00:00', 201513002),
(10, '2024-06-29', '09:00:00', '18:00:00', 201513003);

INSERT INTO overtime_record (id, type, date, start_time, end_time, approval_document_id, employee_id) VALUES
(1, '연장', '2024-06-29', '18:00:00', '20:00:00', NULL, 201511001),
(2, '연장', '2024-06-26', '18:00:00', '19:30:00', NULL, 201511002),
(3, '연장', '2024-06-23', '18:00:00', '21:00:00', NULL, 201511003),
(4, '휴일', '2024-06-28', '09:00:00', '13:00:00', NULL, 201511004),
(5, '휴일', '2024-06-25', '13:00:00', '18:00:00', NULL, 201512001),
(6, '휴일', '2024-06-22', '09:00:00', '13:00:00', NULL, 201512002),
(7, '연장', '2024-06-30', '18:00:00', '22:00:00', NULL, 201512003),
(8, '연장', '2024-06-28', '18:00:00', '23:00:00', NULL, 201513001),
(9, '연장', '2024-06-27', '18:00:00', '20:30:00', NULL, 201513002),
(10, '휴일', '2024-06-30', '09:00:00', '13:00:00', NULL, 201513003),
(11, '휴일', '2024-06-29', '13:00:00', '18:00:00', NULL, 201514001),
(12, '휴일', '2024-06-28', '09:00:00', '13:00:00', NULL, 201514002),
(13, '연장', '2024-06-25', '18:00:00', '21:30:00', NULL, 201514003),
(14, '휴일', '2024-06-27', '13:00:00', '18:00:00', NULL, 201515001),
(15, '휴일', '2024-06-26', '09:00:00', '13:00:00', NULL, 201515002),
(16, '연장', '2024-06-24', '18:00:00', '20:00:00', NULL, 201515003),
(19, '지각', '2024-06-30', '09:00:00', '09:15:00', NULL, 201511001),
(20, '지각', '2024-06-27', '09:00:00', '09:10:00', NULL, 201511002),
(21, '지각', '2024-06-24', '09:00:00', '09:20:00', NULL, 201511003),
(22, '지각', '2024-06-23', '09:00:00', '09:05:00', NULL, 201511004),
(23, '지각', '2024-06-22', '09:00:00', '09:12:00', NULL, 201512001),
(24, '지각', '2024-06-21', '09:00:00', '09:18:00', NULL, 201512002),
(25, '지각', '2024-06-20', '09:00:00', '09:25:00', NULL, 201512003),
(26, '지각', '2024-06-19', '09:00:00', '09:30:00', NULL, 201513001),
(27, '지각', '2024-06-18', '09:00:00', '09:08:00', NULL, 201513002);

