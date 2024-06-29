CREATE USER IF NOT EXISTS 'panda'@'%' IDENTIFIED BY 'panda';

-- 모든 권한 부여
GRANT ALL PRIVILEGES ON *.* TO 'panda'@'localhost' WITH GRANT OPTION;

-- 변경 사항 적용
FLUSH PRIVILEGES;

CREATE DATABASE officeerp;