DELETE FROM specialist_speciality;
DELETE FROM users;
DELETE FROM specialities;

INSERT INTO specialities (id, name) VALUES (1, 'Cardiología');
INSERT INTO specialities (id, name) VALUES (2, 'Nefrología');
INSERT INTO specialities (id, name) VALUES (3, 'Pediatría');

ALTER TABLE users AUTO_INCREMENT = 1;
INSERT INTO users (id, user_type, name, lastname, age, dni, email, profile_pic, health_insurance, profile_pic_alt)
    VALUES (1, "PACIENTE", "Morty", "Smith", 18, 45177915, "morty@gmail.com", "morty.png", "OSDE", "morty2.png");
INSERT INTO users (id, user_type, name, lastname, age, dni, email, profile_pic)
    VALUES (2, "ESPECIALISTA", "Rick", "Sanchez", 60, 10949875, "rick.sanchez@mail.com", "rick.png");
INSERT INTO users (id, user_type, name, lastname, age, dni, email, profile_pic)
    VALUES (3, "ESPECIALISTA", "Gregory", "House", 52, 27191594, "drhouse@mail.com", "house.png");
INSERT INTO users (id, user_type, name, lastname, age, dni, email, profile_pic)
    VALUES (4, "ADMIN", "Gonza", "Monar", 32, 36149489, "gonza.monar@mail.com", "gonza_monar.png");

INSERT INTO specialist_speciality (specialist_id, speciality_id) VALUES (2, 1);
INSERT INTO specialist_speciality (specialist_id, speciality_id) VALUES (2, 2);
INSERT INTO specialist_speciality (specialist_id, speciality_id) VALUES (3, 3);
