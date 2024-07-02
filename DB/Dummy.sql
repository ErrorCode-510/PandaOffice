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
INSERT INTO `job` (`id`, `title`)
VALUES (0600, '사원'),
       (0500, '주임'),
       (0400, '대리'),
       (0300, '과장'),
       (0200, '차장'),
       (0100, '부장'),
       (0001, '사장');
/* 사원 정보 - 임의 */
INSERT INTO `employee` (`employee_id`, `password`, `name`, `english_name`, `hanja_name`, `department_id`,
                        `job_id`, `phone`, `personal_id`, `gender`, `hire_date`, `end_date`,
                        `address`, `nationality`, `birth_date`, `email`, `self_introduction`,
                        `employment_status`, `annual_salary`)
VALUES (201511001, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '김민수', 'Kim Min Su', '金民洙', 11,
        0600, '010-1000-1001', '880101-1234561', '남', '2015-01-01', NULL, '서울특별시 강남구', '대한민국', '1988-01-01',
        'minsu.kim@example.com', NULL, '재직', 35000000),
       (201511002, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '박지영', 'Park Ji Young', '朴智英', 11,
        0500, '010-1000-1002', '880202-1234562', '여', '2015-02-01', NULL, '서울특별시 서초구', '대한민국', '1988-02-02',
        'jiyoung.park@example.com', NULL, '재직', 37000000),
       (201511003, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '이철수', 'Lee Chul Su', '李哲洙', 11,
        0400, '010-1000-1003', '880303-1234563', '남', '2015-03-01', NULL, '서울특별시 동작구', '대한민국', '1988-03-03',
        'chulsu.lee@example.com', NULL, '재직', 36000000),
       (201511004, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '최영희', 'Choi Young Hee', '崔英熙',
        11, 0300, '010-1000-1004', '880404-1234564', '여', '2015-04-01', NULL, '서울특별시 강북구', '대한민국', '1988-04-04',
        'younghee.choi@example.com', NULL, '재직', 38000000),
       (201512001, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '김영수', 'Kim Young Su', '金英洙', 12,
        0600, '010-2000-1001', '880505-2234561', '남', '2015-05-01', NULL, '부산광역시 해운대구', '대한민국', '1988-05-05',
        'youngsu.kim@example.com', NULL, '재직', 35000000),
       (201512002, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '박수현', 'Park Su Hyun', '朴秀賢', 12,
        0500, '010-2000-1002', '880606-2234562', '여', '2015-06-01', NULL, '부산광역시 해운대구', '대한민국', '1988-06-06',
        'suhyun.park@example.com', NULL, '재직', 37000000),
       (201512003, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '이준혁', 'Lee Jun Hyuk', '李俊赫', 12,
        0400, '010-2000-1003', '880707-2234563', '남', '2015-07-01', NULL, '부산광역시 해운대구', '대한민국', '1988-07-07',
        'junhyuk.lee@example.com', NULL, '재직', 36000000),
       (201513001, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '정미경', 'Jung Mi Kyung', '鄭美京', 13,
        0600, '010-3000-1001', '880808-3234561', '여', '2015-08-01', NULL, '인천광역시 남동구', '대한민국', '1988-08-08',
        'mikyoung.jung@example.com', NULL, '재직', 35000000),
       (201513002, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '한지민', 'Han Ji Min', '韓智敏', 13,
        0500, '010-3000-1002', '880909-3234562', '여', '2015-09-01', NULL, '인천광역시 남동구', '대한민국', '1988-09-09',
        'jimin.han@example.com', NULL, '재직', 37000000),
       (201513003, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '김진수', 'Kim Jin Su', '金進洙', 13,
        0400, '010-3000-1003', '881010-3234563', '남', '2015-10-01', NULL, '인천광역시 남동구', '대한민국', '1988-10-10',
        'jinsu.kim@example.com', NULL, '재직', 36000000),
       (201514001, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '박영수', 'Park Young Su', '朴英洙', 14,
        0600, '010-4000-1001', '881111-4234561', '남', '2015-11-01', NULL, '대구광역시 수성구', '대한민국', '1988-11-11',
        'youngsu.park@example.com', NULL, '재직', 35000000),
       (201514002, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '이수현', 'Lee Su Hyun', '李秀賢', 14,
        0500, '010-4000-1002', '881212-4234562', '여', '2015-12-01', NULL, '대구광역시 수성구', '대한민국', '1988-12-12',
        'suhyun.lee@example.com', NULL, '재직', 37000000),
       (201514003, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '정준호', 'Jung Jun Ho', '鄭俊浩', 14,
        0400, '010-4000-1003', '881313-4234563', '남', '2015-12-15', NULL, '대구광역시 수성구', '대한민국', '1988-12-13',
        'junho.jung@example.com', NULL, '재직', 36000000),
       (201515001, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '한수진', 'Han Su Jin', '韓秀珍', 15,
        0600, '010-5000-1001', '881414-5234561', '여', '2015-12-18', NULL, '광주광역시 북구', '대한민국', '1988-12-14',
        'sujin.han@example.com', NULL, '재직', 35000000),
       (201515002, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '최현우', 'Choi Hyun Woo', '崔賢宇', 15,
        0500, '010-5000-1002', '881515-5234562', '남', '2015-12-20', NULL, '광주광역시 북구', '대한민국', '1988-12-15',
        'hyunwoo.choi@example.com', NULL, '재직', 37000000),
       (201515003, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '오준석', 'Oh Jun Seok', '吳俊錫', 15,
        0400, '010-5000-1003', '881616-5234563', '남', '2015-12-22', NULL, '광주광역시 북구', '대한민국', '1988-12-16',
        'junseok.oh@example.com', NULL, '재직', 36000000),
       (201511005, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '유지현', 'Yoo Ji Hyun', '柳智賢', 11,
        0300, '010-1000-1005', '880505-1234565', '여', '2015-12-24', NULL, '서울특별시 강서구', '대한민국', '1988-12-05',
        'jihyun.yoo@example.com', NULL, '재직', 38000000),
       (201512004, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '문준호', 'Moon Jun Ho', '文俊浩', 12,
        0300, '010-2000-1004', '880606-2234564', '남', '2015-12-26', NULL, '부산광역시 해운대구', '대한민국', '1988-12-06',
        'junho.moon@example.com', NULL, '재직', 38000000),
       (201513004, '$2a$10$GN.2fr75YrD6Q78pbgKF9eJEbQxoUn2qA5yTwTgTw/qJQEJb1KDwC', '장지수', 'Jang Ji Su', '張智洙', 13,
        0300, '010-3000-1004', '880707-3234564', '여', '2015-12-28', NULL, '인천광역시 남동구', '대한민국', '1988-12-07',
        'jisu.jang@example.com', NULL, '재직', 38000000);


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
