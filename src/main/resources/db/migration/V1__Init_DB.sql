create sequence account_seq start 1 increment 1;
create sequence admin_seq start 1 increment 1;
create sequence internship_seq start 1 increment 1;
create sequence internship_user_seq start 1 increment 1;
create sequence lesson_seq start 1 increment 1;
create sequence role_seq start 1 increment 1;
create sequence task_seq start 1 increment 1;
create sequence user_seq start 1 increment 1;
create sequence user_task_seq start 1 increment 1;

create table account
(
    id       int8         not null,
    login    varchar(255) not null unique ,
    password varchar(255) not null,
    primary key (id)
);

create table account_roles
(
    account_id int8 not null,
    role_id    int8 not null
);

create table admin
(
    id         int8 not null,
    account_id int8,
    primary key (id)
);

create table internship
(
    id                   int8 not null,
    description          text,
    end_date             timestamp,
    recording_start_date timestamp,
    start_date           timestamp,
    status               varchar(255),
    title                varchar(255),
    primary key (id)
);

create table internship_user
(
    id              int8 not null,
    completion_date timestamp,
    entry_date      timestamp,
    status          varchar(255),
    internship_id   int8,
    user_id         int8,
    primary key (id)
);

create table lesson
(
    id            int8 not null,
    description   text,
    title         varchar(255),
    internship_id int8,
    primary key (id)
);

create table role
(
    id   int8         not null,
    name varchar(255) not null unique,
    primary key (id)
);

create table task
(
    id                    int8 not null,
    gitlab_repository_url varchar(255),
    title                 varchar(255),
    lesson_id             int8,
    primary key (id)
);

create table "user"
(
    id                   int8 not null,
    birth_date           date,
    city                 varchar(255),
    course               int4,
    educational_status   varchar(255),
    email                varchar(255),
    faculty              varchar(255),
    first_name           varchar(255),
    last_name            varchar(255),
    middle_name          varchar(255),
    mobile               varchar(255),
    personal_information text,
    speciality           varchar(255),
    telegram_id          varchar(255),
    university           varchar(255),
    account_id           int8,
    primary key (id)
);

create table user_task
(
    id                           int8 not null,
    forked_gitlab_repository_url varchar(255),
    status                       varchar(255),
    task_id                      int8,
    user_id                      int8,
    primary key (id)
);

alter table if exists account
    add constraint UK_5vxwyorsr92jce3ore6h93k6q unique (login);
alter table if exists role
    add constraint UK_8sewwnpamngi6b1dwaa88askk unique (name);
alter table if exists account_roles
    add constraint FKi84870gssnbi37wfqfifekghb foreign key (role_id) references role;
alter table if exists account_roles
    add constraint FKtp61eta5i06bug3w1qr6286uf foreign key (account_id) references account;
alter table if exists admin
    add constraint FKn2eatyxq78i3wg18tt0jf56lw foreign key (account_id) references account;
alter table if exists internship_user
    add constraint FK3myjq09rlwd6hpt38daiqg67d foreign key (internship_id) references internship;
alter table if exists internship_user
    add constraint FKjpv7hwigdr2mgsg2n72wvn2m8 foreign key (user_id) references "user";
alter table if exists lesson
    add constraint FKaj40dxk9s9g2q1jurbioxy6it foreign key (internship_id) references internship;
alter table if exists task
    add constraint FK5x8hrayewoued0usmps6rhk9e foreign key (lesson_id) references lesson;
alter table if exists "user"
    add constraint FKg6e3jb9kwr725fa0st0cuyvxl foreign key (account_id) references account;
alter table if exists user_task
    add constraint FKvs34bjkmpbk2e54qlrol3ilt foreign key (task_id) references task;
alter table if exists user_task
    add constraint FK8q1joy748n5hutw4ukoi7xead foreign key (user_id) references "user";