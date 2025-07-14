package com.clinica_hipocrates.appointment_service.controller;

import com.clinica_hipocrates.appointment_service.assembler.AppointmentDTOAssembler;
import com.clinica_hipocrates.appointment_service.assembler.FeedbackDTOAssembler;
import com.clinica_hipocrates.appointment_service.assembler.ScheduleDTOAssembler;
import com.clinica_hipocrates.appointment_service.assembler.SurveyDTOAssembler;
import com.clinica_hipocrates.appointment_service.controller.shared.IntegrationTest;
import com.clinica_hipocrates.appointment_service.model.*;
import com.clinica_hipocrates.appointment_service.model.enums.AppointmentStatus;
import com.clinica_hipocrates.appointment_service.model.enums.FeedbackReason;
import com.clinica_hipocrates.appointment_service.model.enums.FeedbackType;
import com.clinica_hipocrates.appointment_service.repository.CachedUserRepository;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

@ActiveProfiles("test")
@Sql(scripts = "/e2e-bd-reset.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class E2EIntegrationTest extends IntegrationTest {

    @Autowired
    CachedUserRepository userRepository;

    @Autowired
    ScheduleDTOAssembler scheduleAssembler;

    @Autowired
    AppointmentDTOAssembler appointmentAssembler;

    @Autowired
    FeedbackDTOAssembler feedbackAssembler;

    @Autowired
    SurveyDTOAssembler surveyAssembler;


    @Test
    void e2e_perfectOutcome() {
        // 0. PRE-REQUISITES
        userRepository.save(new CachedUser(1L, "PACIENTE", "Morty", "Smith", null, LocalDateTime.now()));
        userRepository.save(new CachedUser(2L, "ESPECIALISTA", "Rick", "Sanchez", new HashSet<>(List.of(1L, 2L)), LocalDateTime.now()));


        // 1. The SPECIALIST creates a new SCHEDULE
        String scheduleBody = """
        {
            "specialistId": 2,
            "day": "MONDAY",
            "start": "09:00",
            "end": "16:00"
        }
        """;

        Schedule expectedSchedule = new Schedule(1L, 2L, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(16, 0));
        assertCreateOne("/schedules", scheduleBody, 1L,  new TypeRef<>() {}, scheduleAssembler, expectedSchedule);
        assertGetOne("/schedules", 1L, new TypeRef<>() {}, scheduleAssembler, expectedSchedule);


        // 2. The PATIENT creates a new APPOINTMENT
        String appointmentBody = """
        {
            "specialistId": 2,
            "specialityId": 1,
            "patientId": 1,
            "dateTime": "2025-10-13T11:00:00"
        }
        """;

        Appointment expectedAppointment =
                Appointment.builder()
                        .id(1L)
                        .status(AppointmentStatus.PENDIENTE)
                        .specialistId(2L)
                        .specialityId(1L)
                        .patientId(1L)
                        .dateTime(LocalDateTime.of(2025, 10, 13, 11, 0))
                        .build();
        assertCreateOne("/appointments", appointmentBody, 1L,  new TypeRef<>() {}, appointmentAssembler, expectedAppointment);
        assertGetOne("/appointments", 1L, new TypeRef<>() {}, appointmentAssembler, expectedAppointment);


        // 3. The SPECIALIST accepts the APPOINTMENT
        String patchAcceptedStatusBody = """
        { "status": "ACEPTADO" }
        """;
        expectedAppointment.setStatus(AppointmentStatus.ACEPTADO);
        assertPatchOne("/appointments", 1L, "status", patchAcceptedStatusBody, new TypeRef<>() {}, appointmentAssembler, expectedAppointment);
        assertGetOne("/appointments", 1L, new TypeRef<>() {}, appointmentAssembler, expectedAppointment);


        // 4. The SPECIALIST finishes the APPOINTMENT
        String patchFinishedStatusBody = """
        { "status": "REALIZADO" }
        """;
        expectedAppointment.setStatus(AppointmentStatus.REALIZADO);
        assertPatchOne("/appointments", 1L, "status", patchFinishedStatusBody, new TypeRef<>() {}, appointmentAssembler, expectedAppointment);
        assertGetOne("/appointments", 1L, new TypeRef<>() {}, appointmentAssembler, expectedAppointment);


        // 5. The SPECIALIST loads the FEEDBACK(REVIEW)
        String reviewBody = """
        {
            "type": "REVIEW",
            "appointmentId": 1,
            "authorId": 2,
            "reason": "REVIEW",
            "text": "Reseña del turno 1"
        }
        """;
        Feedback expectedReview = new Feedback(1L, FeedbackType.REVIEW, 1L, 2L, FeedbackReason.REVIEW, "Reseña del turno 1");
        assertCreateOne("/feedback", reviewBody, 1L,  new TypeRef<>() {}, feedbackAssembler, expectedReview);
        assertGetOne("/feedback", 1L, new TypeRef<>() {}, feedbackAssembler, expectedReview);

        expectedAppointment.setReviewId(1L);
        assertGetOne("/appointments", 1L, new TypeRef<>() {}, appointmentAssembler, expectedAppointment);


        // 6. The PATIENT rates the attention, giving RATING + FEEDBACK(COMMENT)
        String patchRatingBody = """
        { "rating": 5 }
        """;
        expectedAppointment.setRating(5);
        assertPatchOne("/appointments", 1L, "rating", patchRatingBody, new TypeRef<>() {}, appointmentAssembler, expectedAppointment);
        assertGetOne("/appointments", 1L, new TypeRef<>() {}, appointmentAssembler, expectedAppointment);


        String commentBody = """
        {
            "type": "COMMENT",
            "appointmentId": 1,
            "authorId": 1,
            "reason": "CALIFICATION",
            "text": "Comentario para calificar el turno"
        }
        """;
        Feedback expectedComment = new Feedback(2L, FeedbackType.COMMENT, 1L, 1L, FeedbackReason.CALIFICATION, "Comentario para calificar el turno");
        assertCreateOne("/feedback", commentBody, 2L,  new TypeRef<>() {}, feedbackAssembler, expectedComment);
        assertGetOne("/feedback", 2L, new TypeRef<>() {}, feedbackAssembler, expectedComment);
        expectedAppointment.setCommentId(2L);
        assertGetOne("/appointments", 1L, new TypeRef<>() {}, appointmentAssembler, expectedAppointment);


        // 7. The PATIENT completes the SURVEY
        String surveyBody = """
        {
            "appointmentId": 1,
            "websiteCalification": 3,
            "websiteComment": "Comentario página web",
            "specialistCalification": 3,
            "specialistComment": "Comentario especialista",
            "appointmentCalification": 3,
            "appointmentComment": "Comentario turno"
        }
        """;
        Survey expectedSurvey = new Survey(1L, 1L,
                3, "Comentario página web",
                3, "Comentario especialista",
                3, "Comentario turno");
        assertCreateOne("/surveys", surveyBody, 1L,  new TypeRef<>() {}, surveyAssembler, expectedSurvey);
        assertGetOne("/surveys", 1L, new TypeRef<>() {}, surveyAssembler, expectedSurvey);
        expectedAppointment.setSurveyId(1L);
        assertGetOne("/appointments", 1L, new TypeRef<>() {}, appointmentAssembler, expectedAppointment);


        // 8. The SPECIALIST loads the MEDICAL HISTORY
        // performed through medical-records-service
        // and updated through MQ events
    }

}
