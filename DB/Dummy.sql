-- department 테이블에 더미 데이터 삽입
INSERT INTO `department` (`id`, `name`)
VALUES
    (11, '인사'),
    (12, '회계'),
    (13, '영업'),
    (14, '기획'),
    (15, '마케팅');
-- job 테이블에 더미 데이터 삽입
INSERT INTO `job` (`id`, `title`)
VALUES
    (0600, '사원'),
    (0500, '주임'),
    (0400, '대리'),
    (0300, '과장'),
    (0200, '차장'),
    (0100, '부장'),
    (0001, '사장');
/* 사원 정보 - 임의 */
INSERT INTO `employee` (
    `employee_id`, `password`, `name`, `english_name`, `hanja_name`, `department_id`,
    `job_id`, `phone`, `personal_id`, `gender`, `hire_date`, `end_date`,
    `address`, `nationality`, `birth_date`, `email`, `self_introduction`,
    `employment_status`, `annual_salary`
) VALUES (
             201911027, '$2a$10$ZPYfwW9sssWjFldokxW2DOJvq0o.6NPBIqrOo/oNl2Xl4/RVNTySy', '홍길동', 'Hong Gil Dong', '洪吉童', 11,
             0400, '010-1234-5678', '800101-1234567', '남', '2019-01-15', NULL,
             '서울특별시 강남구', '대한민국', '1980-01-01', 'honggildong@example.com', '안녕하세요, 홍길동입니다.',
             '재직', 45000000
         ); -- 비밀번호 gil555

INSERT INTO `employee` (
    `employee_id`, `password`, `name`, `english_name`, `hanja_name`, `department_id`,
    `job_id`, `phone`, `personal_id`, `gender`, `hire_date`, `end_date`,
    `address`, `nationality`, `birth_date`, `email`, `self_introduction`,
    `employment_status`, `annual_salary`
) VALUES (
             201712001, '$2a$10$JbsFtkMHroQOVpGK3Q8Vy.Th8adJeqk4KPUbOM3Hae8pxahU0ceoe', '김철수', 'Kim Chul Soo', '金哲洙', 12,
             0100, '010-2345-6789', '810202-2345678', '남', '2017-03-12', NULL,
             '부산광역시 해운대구', '대한민국', '1981-02-02', 'chulsoo.kim@example.com', NULL,
             '재직', 60000000
         ); -- 비밀번호 su2017

INSERT INTO `employee` (
    `employee_id`, `password`, `name`, `english_name`, `hanja_name`, `department_id`,
    `job_id`, `phone`, `personal_id`, `gender`, `hire_date`, `end_date`,
    `address`, `nationality`, `birth_date`, `email`, `self_introduction`,
    `employment_status`, `annual_salary`
) VALUES (
             202314054, '$2a$10$UjIJUhFUnSebRNWkVhqDf.h7NhBAbKhiSLs16Bk9j0CPgwyfKYJdi', '이영희', 'Lee Young Hee', '李英熙', 14,
             0600, '010-3456-7890', '820303-3456789', '여', '2023-03-27', NULL,
             '대구광역시 중구', '대한민국', '1982-03-03', 'younghee.lee@example.com', NULL,
             '재직', 30000000
         ); -- 비밀번호 lee0hee
/* 지급항목 - 고정 */
INSERT INTO `earning_category` (`id`, `name`, `is_tax`)
VALUES
    (101, '기본급여', 'Y'),
    (102, '상여', 'Y'),
    (200, '식대', 'N'),
    (201, '연장근로수당', 'N'),
    (202, '휴일근로수당', 'N'),
    (203, '가족수당', 'Y'),
    (204, '명절상여', 'Y'),
    (205, '직책수당', 'Y');
/* 공제항목 - 고정*/
INSERT INTO `deducation_category` (`id`, `name`, `deducation_rate`)
VALUES
    (501, '국민연금', 4.5),
    (502, '건강보험', 3.545),
    (503, '고용보험', 12.95),
    (504, '장기요양보험', 0.9);
