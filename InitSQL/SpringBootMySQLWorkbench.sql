DROP DATABASE blog;
CREATE DATABASE blog CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- 유저이름@아이피주소
CREATE USER 'USERNAME'@'%' IDENTIFIED BY 'PASSWORD';
-- ON DB이름.테이블명
-- TO 유저이름@아이피주소
GRANT ALL PRIVILEGES ON *.* TO 'USERNAME'@'%';
CREATE DATABASE blog;
USE blog;

-- 한글 설정 C:\ProgramData\MySQL\MySQL Server 8.0 위치 my.ini 변경 후
-- 한글 설정 확인
SHOW VARIABLES LIKE 'c%';