drop user c##product_test;
--계정생성
CREATE USER c##product_test IDENTIFIED BY product1234
    DEFAULT TABLESPACE users
    TEMPORARY TABLESPACE temp
    PROFILE DEFAULT;
--권한부여
GRANT CONNECT, RESOURCE TO c##product_test;
GRANT CREATE VIEW, CREATE SYNONYM TO c##product_test;
GRANT UNLIMITED TABLESPACE TO c##product_test;
--락 풀기
ALTER USER c##spring ACCOUNT UNLOCK;