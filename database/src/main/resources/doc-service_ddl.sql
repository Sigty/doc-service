CREATE DATABASE doc_service encoding ='UTF8';

\c doc_service;

CREATE SCHEMA doc_service;

CREATE TABLE doc_service.doc_type
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(32) UNIQUE NOT NULL,
    type     VARCHAR(32)        NOT NULL,
    assembly BOOLEAN,
    detail   BOOLEAN
);

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
    email         VARCHAR(64) UNIQUE NOT NULL
);

CREATE TABLE doc_service.user
(
    id             SERIAL PRIMARY KEY,
    login          VARCHAR(32) UNIQUE                              NOT NULL,
    password       VARCHAR(32)                                     NOT NULL,
    detail_user_id INTEGER REFERENCES doc_service.user_specialty (id) NOT NULL,
    role_id        INTEGER REFERENCES doc_service.role (id)        NOT NULL DEFAULT 1
);

CREATE TABLE doc_service.manufacturer
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(32) UNIQUE NOT NULL
);

CREATE TABLE doc_service.part
(
    id               SERIAL PRIMARY KEY,
    part_number      VARCHAR(32) UNIQUE                       NOT NULL,
    description      VARCHAR(128)                             NOT NULL,
    type             VARCHAR(32)                              NOT NULL,
    sort             VARCHAR(16)                              NOT NULL,
    create_part_date TIMESTAMP WITH TIME ZONE                 NOT NULL,
    create_user_id   INTEGER REFERENCES doc_service.user (id) NOT NULL,
    manufacturer_id  INTEGER REFERENCES doc_service.manufacturer (id)
);

CREATE TABLE doc_service.document
(
    id              SERIAL PRIMARY KEY,
    number          VARCHAR(32) UNIQUE       NOT NULL,
    create_doc_date TIMESTAMP WITH TIME ZONE NOT NULL,
    type_doc_id     INTEGER REFERENCES doc_service.doc_type (id),
    user_id         INTEGER REFERENCES doc_service.user (id)
);



CREATE TABLE doc_service.doc_part
(
    doc_id        INTEGER REFERENCES doc_service.document (id),
    part_id       INTEGER REFERENCES doc_service.part (id),
    quantity_part INTEGER NOT NULL,
    PRIMARY KEY (doc_id, part_id)
);

