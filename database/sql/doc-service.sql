CREATE DATABASE doc_service encoding ='UTF8';

\c doc_service;

CREATE SCHEMA doc_service;

CREATE TABLE doc_service.doc_type
(
    id   SERIAL PRIMARY KEY,
    type VARCHAR(32) UNIQUE NOT NULL
);

CREATE TABLE doc_service.department
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(64) UNIQUE NOT NULL
);

INSERT INTO doc_service.department (id, name)
VALUES (1, 'DEV'),
       (2, 'QA'),
       (3, 'DEVOPS'),
       (4, 'BA'),
       (5, 'PR');

CREATE TABLE doc_service.role
(
    id   SERIAL PRIMARY KEY,
    role VARCHAR(16) UNIQUE NOT NULL
);

CREATE TABLE doc_service.user_detail
(
    id            SERIAL PRIMARY KEY,
    firstname     VARCHAR(64)        NOT NULL,
    lastname      VARCHAR(64)        NOT NULL,
    email         VARCHAR(64) UNIQUE NOT NULL,
    department_id INTEGER REFERENCES doc_service.department (id)
);

CREATE TABLE doc_service.user
(
    id             SERIAL PRIMARY KEY,
    login          VARCHAR(32) UNIQUE                              NOT NULL,
    password       VARCHAR(32)                                     NOT NULL,
    detail_user_id INTEGER REFERENCES doc_service.user_detail (id) not null,
    role_id        INTEGER REFERENCES doc_service.role (id)        NOT NULL DEFAULT 1
);

CREATE TABLE doc_service.project
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(32) NOT NULL
);

CREATE TABLE doc_service.user_project
(
    user_id    INTEGER REFERENCES doc_service.user (id),
    project_id INTEGER REFERENCES doc_service.project (id)
);

CREATE TABLE doc_service.document
(
    id              SERIAL PRIMARY KEY,
    number          VARCHAR(32) UNIQUE NOT NULL,
    create_doc_date timestamp          NOT NULL,
    type_doc_id     INTEGER REFERENCES doc_service.doc_type (id),
    user_id         INTEGER REFERENCES doc_service.user (id)
);

CREATE TABLE doc_service.manufacturer
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(32) unique NOT NULL
);

CREATE TABLE doc_service.part
(
    id               SERIAL PRIMARY KEY,
    part_number      VARCHAR(32) UNIQUE                       NOT NULL,
    description      VARCHAR(128)                             NOT NULL,
    type             VARCHAR(32)                              NOT NULL,
    sort             VARCHAR(16)                              NOT NULL,
    create_part_date timestamp                                NOT NULL,
    create_user_id   INTEGER REFERENCES doc_service.user (id) NOT NULL,
    manufacturer_id  INTEGER REFERENCES doc_service.manufacturer (id)
);

CREATE TABLE doc_service.doc_part
(
    part_id       INTEGER REFERENCES doc_service.part (id),
    doc_id        INTEGER REFERENCES doc_service.document (id),
    quantity_part INTEGER NOT NULL
);

