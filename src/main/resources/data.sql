insert into users (email, nickname, password, role, active, reg_date, mod_date)
values ('admin@gmail.com', '관리자', '$2a$10$BsLduLFYpzwPScJDQUSqNunzJLT7S41GN.7wk6HQKDBVmBKq7QEZG', 'ADMIN', true, now(), now());
-- 테스트용 계정 데이터 1234