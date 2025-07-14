DELETE FROM cached_user_speciality_ids;
DELETE FROM cached_users;
DELETE FROM appointments;
DELETE FROM feedback;
DELETE FROM schedules;
DELETE FROM surveys;

ALTER TABLE cached_users AUTO_INCREMENT = 1;
ALTER TABLE appointments AUTO_INCREMENT = 1;
ALTER TABLE feedback AUTO_INCREMENT = 1;
ALTER TABLE schedules AUTO_INCREMENT = 1;
ALTER TABLE surveys AUTO_INCREMENT = 1;

INSERT INTO cached_users (id, user_type, name, lastname, last_updated)
    VALUES (1, "PACIENTE", "Morty", "Smith", NOW());
INSERT INTO cached_users (id, user_type, name, lastname, last_updated)
    VALUES (2, "ESPECIALISTA", "Rick", "Sanchez", NOW());
INSERT INTO cached_users (id, user_type, name, lastname, last_updated)
    VALUES (3, "ESPECIALISTA", "Gregory", "House", NOW());
INSERT INTO cached_users (id, user_type, name, lastname, last_updated)
    VALUES (4, "PACIENTE", "Gonza", "Monar", NOW());

INSERT INTO cached_user_speciality_ids (user_id, speciality_id) VALUES (2, 1);
INSERT INTO cached_user_speciality_ids (user_id, speciality_id) VALUES (2, 2);
INSERT INTO cached_user_speciality_ids (user_id, speciality_id) VALUES (3, 3);

INSERT INTO appointments (id, status, specialist_id, speciality_id, patient_id, date_time)
    VALUES (1, 'PENDIENTE', 2, 1, 1, "2025-12-05T15:00:00");
INSERT INTO appointments (id, status, specialist_id, speciality_id, patient_id, date_time)
    VALUES (2, 'PENDIENTE', 2, 2, 4, "2025-12-06T11:00:00");
INSERT INTO appointments (id, status, specialist_id, speciality_id, patient_id, date_time)
    VALUES (3, 'PENDIENTE', 2, 2, 1, "2025-12-06T16:00:00");
INSERT INTO appointments (id, status, specialist_id, speciality_id, patient_id, date_time)
    VALUES (4, 'PENDIENTE', 3, 3, 4, "2025-12-07T11:00:00");
INSERT INTO appointments (id, status, specialist_id, speciality_id, patient_id, date_time)
    VALUES (5, 'PENDIENTE', 3, 3, 4, "2025-12-07T14:00:00");

INSERT INTO feedback (id, type, appointment_id, author_id, reason, text)
    VALUES (1, "REVIEW", 1, 1, "REVIEW", "Reseña de prueba");
INSERT INTO feedback (id, type, appointment_id, author_id, reason, text)
    VALUES (2, "COMMENT", 2, 2, "CALIFICATION", "Comentario de prueba");

INSERT INTO schedules (id, specialist_id, day, start, end) VALUES (1, 2, "Monday", "10:00", "16:00");
INSERT INTO schedules (id, specialist_id, day, start, end) VALUES (2, 2, "Tuesday", "10:00", "16:00");
INSERT INTO schedules (id, specialist_id, day, start, end) VALUES (3, 2, "Wednesday", "10:00", "16:00");
INSERT INTO schedules (id, specialist_id, day, start, end) VALUES (4, 2, "Thursday", "10:00", "16:00");
INSERT INTO schedules (id, specialist_id, day, start, end) VALUES (5, 2, "Friday", "10:00", "16:00");
INSERT INTO schedules (id, specialist_id, day, start, end) VALUES (6, 2, "Saturday", "10:00", "16:00");
INSERT INTO schedules (id, specialist_id, day, start, end) VALUES (7, 3, "Monday", "09:00", "18:00");
INSERT INTO schedules (id, specialist_id, day, start, end) VALUES (8, 3, "Wednesday", "09:00", "18:00");

INSERT INTO surveys (id, appointment_id, website_calification, website_comment, specialist_calification, specialist_comment, appointment_calification, appointment_comment)
VALUES (1, 1, 5, "Comentario página web 1", 5, "Comentario especialista 1", 5, "Comentario turno 1");
INSERT INTO surveys (id, appointment_id, website_calification, website_comment, specialist_calification, specialist_comment, appointment_calification, appointment_comment)
VALUES (2, 2, 1, "Comentario página web 2", 1, "Comentario especialista 2", 1, "Comentario turno 2");
INSERT INTO surveys (id, appointment_id, website_calification, website_comment, specialist_calification, specialist_comment, appointment_calification, appointment_comment)
VALUES (3, 3, 3, "Comentario página web 3", 3, "Comentario especialista 3", 3, "Comentario turno 3");