

INSERT INTO customer_entity ( customer_id, name) VALUES ('1', 'John Doe');
INSERT INTO phone_number_entity ( phone_number, customer_id) VALUES ( '+1234567890', 1);



SELECT name FROM customer_entity;
SELECT customer_id FROM phone_number_entity;