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