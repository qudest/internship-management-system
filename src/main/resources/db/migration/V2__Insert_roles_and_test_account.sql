insert into role (id, name) values (nextval('role_seq'), 'ROLE_USER');
insert into role (id, name) values (nextval('role_seq'), 'ROLE_ADMIN');

insert into account (id, username, password)
values
(nextval('account_seq'), 'user', '$2a$12$6MsWCdQAMIishOiAjrEydOnSO/zXeKNzRPBRqb6poNKcqGZccxz4e'),
(nextval('account_seq'), 'admin', '$2a$12$810/P/xv/ZhM1JeKFc.5reCOO/sxdDzmYHszMYBMpIw2IhfC5riI.');

insert into account_roles (account_id, role_id)
values
(1, 1),
(2, 2);

insert into admin (id, account_id)
values
(nextval('admin_seq'), 2);

insert into "user" (id, account_id)
values
(nextval('user_seq'), 1);