CREATE TABLE topics
(id BIGINT AUTO_INCREMENT NOT NULL,
title varchar(50),
message varchar(250),
creation_date datetime,
status varchar(10),
author_id bigint,
course_id bigint,
PRIMARY KEY(id),
FOREIGN KEY(author_id)
REFERENCES users(id),
FOREIGN KEY(course_id)
REFERENCES courses(id)
);