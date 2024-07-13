CREATE TABLE responses
(id BIGINT  AUTO_INCREMENT NOT NULL,
message varchar(250),
topic_id BIGINT,
creation_date datetime,
author_id BIGINT,
solution tinyInt,
PRIMARY KEY(id),
FOREIGN KEY(topic_id)
REFERENCES topics(id),
FOREIGN KEY(author_id)
REFERENCES users(id)
);