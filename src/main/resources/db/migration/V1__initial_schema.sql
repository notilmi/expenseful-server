CREATE TABLE statements
(
    id       VARCHAR(255)     NOT NULL,
    title    VARCHAR(255)     NOT NULL,
    category VARCHAR(255),
    amount   DOUBLE PRECISION NOT NULL,
    date     VARCHAR(255)     NOT NULL,
    type     SMALLINT         NOT NULL,
    owner_id VARCHAR(255)     NOT NULL,
    CONSTRAINT pk_statements PRIMARY KEY (id)
);

CREATE TABLE users
(
    id       VARCHAR(255) NOT NULL,
    name     VARCHAR(255),
    email    VARCHAR(255),
    password VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);