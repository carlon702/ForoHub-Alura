CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
    email VARCHAR(100) NOT NULL,
    password VARCHAR(300) NOT NULL UNIQUE,
    profile BIGINT NOT NULL,
    PRIMARY KEY (id)
);

-- Inserting a record into the 'autorxs' table
INSERT INTO users (id,name, email, password, profile) VALUES
(1, 'ana.souza@voll.med', '$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoafCopUazP12.rqB41bsolF5.');