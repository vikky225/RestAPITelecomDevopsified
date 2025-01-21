DROP TABLE IF EXISTS phone_number_entity;
DROP TABLE IF EXISTS customer_entity;

CREATE TABLE customer_entity (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 customer_id VARCHAR(255) NOT NULL UNIQUE,
                                 name VARCHAR(255) NOT NULL
);

CREATE TABLE phone_number_entity (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     phone_number VARCHAR(255) NOT NULL,
                                     customer_id BIGINT,
                                     FOREIGN KEY (customer_id) REFERENCES customer_entity(id)
);