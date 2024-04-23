insert into internship(id, title, description, start_date, recording_end_date, end_date, status)
values
(nextval('internship_seq'), 'Internship 1', 'Internship 1 description', '2024-04-01', '2024-04-30', '2024-07-30', 'OPEN_FOR_REGISTRATION'),
(nextval('internship_seq'), 'Internship 2', 'Internship 2 description', '2024-04-01', '2024-04-30', '2024-07-30', 'STARTED'),
(nextval('internship_seq'), 'Internship 3', 'Internship 3 description', '2024-04-01', '2024-04-30', '2024-07-30', 'OPEN_FOR_REGISTRATION');