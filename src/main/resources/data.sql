DROP TABLE IF EXISTS phone_number_entity;
DROP TABLE IF EXISTS customer_entity;

CREATE TABLE customer_entity (
                                 id SERIAL PRIMARY KEY,
                                 customer_id VARCHAR(255) NOT NULL UNIQUE,
                                 name VARCHAR(255) NOT NULL
);

CREATE TABLE phone_number_entity (
                                     id SERIAL PRIMARY KEY,
                                     phone_number VARCHAR(255) NOT NULL,
                                     customer_id BIGINT,
                                     FOREIGN KEY (customer_id) REFERENCES customer_entity(id)
);

INSERT INTO customer_entity (customer_id, name) VALUES ('1', 'John Doe');
INSERT INTO customer_entity (customer_id, name) VALUES ('2', 'Jane Smith');

INSERT INTO phone_number_entity (phone_number, customer_id) VALUES ('+1234567890', 1);
INSERT INTO phone_number_entity (phone_number, customer_id) VALUES ('+0987654321', 2);

SELECT * FROM customer_entity;
SELECT * FROM phone_number_entity;