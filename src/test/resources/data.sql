

INSERT INTO customer_entity ( customer_id, name) VALUES ('1', 'John Doe');
INSERT INTO phone_number_entity ( phone_number, customer_id) VALUES ( '+1234567890', 1);

SELECT * FROM customer_entity;
SELECT * FROM phone_number_entity;