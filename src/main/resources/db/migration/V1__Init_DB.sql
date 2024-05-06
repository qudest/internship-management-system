create sequence account_seq start 1 increment 1;
create sequence admin_seq start 1 increment 1;
create sequence internship_request_seq start 1 increment 1;
create sequence internship_seq start 1 increment 1;
create sequence internship_user_seq start 1 increment 1;
create sequence lesson_seq start 1 increment 1;
create sequence message_seq start 1 increment 1;
create sequence role_seq start 1 increment 1;
create sequence task_seq start 1 increment 1;
create sequence user_seq start 1 increment 1;
create sequence user_task_seq start 1 increment 1;

create table account
(
    id       int8                not null,
    username varchar(255) unique not null,
    password varchar(255)        not null,
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
    name       varchar(255),
    account_id int8,
    primary key (id)
);

create table internship
(
    id                 int8 not null,
    title              varchar(255),
    description        text,
    start_date         timestamp,
    recording_end_date timestamp,
    end_date           timestamp,
    status             varchar(255),
    primary key (id)
);

create table internship_request
(
    id            int8 not null,
    status        varchar(255),
    internship_id int8,
    user_id       int8,
    primary key (id)
);

create table internship_user
(
    id              int8 not null,
    entry_date      timestamp,
    completion_date timestamp,
    status          varchar(255),
    internship_id   int8,
    user_id         int8,
    primary key (id)
);

create table lesson
(
    id            int8 not null,
    title         varchar(255),
    description   text,
    internship_id int8,
    primary key (id)
);

create table message
(
    id          int8 not null,
    text        varchar(255),
    sender_name varchar(255),
    created_at  timestamp,
    receiver_id int8,
    primary key (id)
);

create table role
(
    id   int8         not null,
    name varchar(255) not null,
    primary key (id)
);

create table task
(
    id                    int8         not null,
    title                 varchar(255) not null unique,
    gitlab_repository_url varchar(255),
    lesson_id             int8,
    primary key (id)
);

create table "user"
(
    id                   int8 not null,
    last_name            varchar(255),
    first_name           varchar(255),
    middle_name          varchar(255),
    email                varchar(255),
    phone                varchar(255),
    telegram_id          varchar(255),
    personal_information text,
    birth_date           date,
    city                 varchar(255),
    educational_status   varchar(255),
    university           varchar(255),
    faculty              varchar(255),
    speciality           varchar(255),
    course               int4,
    account_id           int8,
    primary key (id)
);

create table user_task
(
    id                           int8 not null,
    forked_gitlab_repository_url varchar(255),
    status                       varchar(255),
    last_check_date              timestamp,
    task_id                      int8,
    user_id                      int8,
    primary key (id)
);

alter table account
    add constraint UK_gex1lmaqpg0ir5g1f5eftyaa1 unique (username);
alter table role
    add constraint UK_8sewwnpamngi6b1dwaa88askk unique (name);
alter table account_roles
    add constraint FKi84870gssnbi37wfqfifekghb foreign key (role_id) references role;
alter table account_roles
    add constraint FKtp61eta5i06bug3w1qr6286uf foreign key (account_id) references account;
alter table admin
    add constraint FKn2eatyxq78i3wg18tt0jf56lw foreign key (account_id) references account;
alter table internship_request
    add constraint FK7jyjmrt43akfbjpwg3vb7hq92 foreign key (internship_id) references internship;
alter table internship_request
    add constraint FKvnyyld57fr0txmm9yx9xb2rs foreign key (user_id) references "user";
alter table internship_user
    add constraint FK3myjq09rlwd6hpt38daiqg67d foreign key (internship_id) references internship;
alter table internship_user
    add constraint FKjpv7hwigdr2mgsg2n72wvn2m8 foreign key (user_id) references "user";
alter table lesson
    add constraint FKaj40dxk9s9g2q1jurbioxy6it foreign key (internship_id) references internship;
alter table message
    add constraint FK4hbj90ctopccuptmpiuu65cd9 foreign key (receiver_id) references account;
alter table task
    add constraint FK5x8hrayewoued0usmps6rhk9e foreign key (lesson_id) references lesson;
alter table "user"
    add constraint FKg6e3jb9kwr725fa0st0cuyvxl foreign key (account_id) references account;
alter table user_task
    add constraint FKvs34bjkmpbk2e54qlrol3ilt foreign key (task_id) references task;
alter table user_task
    add constraint FK8q1joy748n5hutw4ukoi7xead foreign key (user_id) references "user";