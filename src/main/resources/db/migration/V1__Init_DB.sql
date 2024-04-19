CREATE SEQUENCE internship_seq START 1;
CREATE SEQUENCE internship_user_seq START 1;
CREATE SEQUENCE lesson_seq START 1;
CREATE SEQUENCE user_seq START 1;
CREATE SEQUENCE task_seq START 1;
CREATE SEQUENCE user_task_seq START 1;

CREATE TABLE internship (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    recording_start_date TIMESTAMP,
    status VARCHAR(255)
);

CREATE TABLE "user" (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    middle_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    mobile VARCHAR(255),
    telegram_id VARCHAR(255),
    personal_information TEXT,
    birth_date DATE,
    city VARCHAR(255),
    educational_status VARCHAR(255),
    university VARCHAR(255),
    faculty VARCHAR(255),
    speciality VARCHAR(255),
    course INTEGER
);

CREATE TABLE internship_user (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES "user"(id),
    internship_id BIGINT REFERENCES internship(id),
    status VARCHAR(255),
    entry_date TIMESTAMP,
    completion_date TIMESTAMP
);

CREATE TABLE lesson (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    internship_id BIGINT REFERENCES internship(id)
);

CREATE TABLE task (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    gitlab_repository_url TEXT,
    lesson_id BIGINT REFERENCES lesson(id)
);

CREATE TABLE user_task (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES "user"(id),
    task_id BIGINT REFERENCES task(id),
    forked_gitlab_repository_url TEXT,
    status VARCHAR(255)
);