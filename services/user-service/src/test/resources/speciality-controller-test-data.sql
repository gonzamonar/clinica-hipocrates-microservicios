DELETE FROM specialist_speciality;
DELETE FROM users;
DELETE FROM specialities;

ALTER TABLE specialities AUTO_INCREMENT = 1;
INSERT INTO specialities (id, name) VALUES (1, 'Cardiología');
INSERT INTO specialities (id, name) VALUES (2, 'Nefrología');
INSERT INTO specialities (id, name) VALUES (3, 'Pediatría');
INSERT INTO users (id, user_type, name, lastname, age, dni, email, profile_pic)
    VALUES (1, "ESPECIALISTA", "Test", "Doctor", 30, 30123345, "e@mail.com", "/picture.png");
INSERT INTO specialist_speciality (specialist_id, speciality_id) VALUES (1, 2);