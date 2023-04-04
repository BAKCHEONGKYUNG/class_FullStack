--상품관리
create table product_test(
    pid         number(10),
    pname       varchar(30),
    quantity    number(10),
    price       number(10)
);
--기본키
alter table product_test add constraint product_test_pid_pk primary key(pid);

--시퀀스생성
create sequence product_test_pid_seq;

--생성--
insert into product_test(pid,pname,quantity,price)
     values(product_test_pid_seq.nextval, '컴퓨터', 5, 1000000);

insert into product_test(pid,pname,quantity,price)
     values(product_test_pid_seq.nextval, '모니터', 5, 500000);

insert into product_test(pid,pname,quantity,price)
     values(product_test_pid_seq.nextval, '프린터', 1, 300000);

--조회--
select pid, pname, quantity, price
  from product_test
 where pid = 2;

--수정--
update product_test
   set pname = '컴퓨터2',
       quantity = 10,
       price = 1200000;

--삭제
delete from product_test where pid = 1;

--전체조회-
select pid,pname,quantity,price from product_test;

commit;