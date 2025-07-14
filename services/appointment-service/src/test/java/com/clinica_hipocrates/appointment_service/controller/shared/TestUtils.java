package com.clinica_hipocrates.appointment_service.controller.shared;

import static org.assertj.core.api.Assertions.assertThat;

import com.clinica_hipocrates.appointment_service.model.*;
import com.clinica_hipocrates.appointment_service.model.enums.AppointmentStatus;
import com.clinica_hipocrates.appointment_service.model.enums.FeedbackReason;
import com.clinica_hipocrates.appointment_service.model.enums.FeedbackType;
import com.clinica_hipocrates.common.exception.ApiError;
import com.clinica_hipocrates.common.exception.ErrorCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

import static io.restassured.RestAssured.*;

public class TestUtils {

    public static void assertFailedRequest(Method method, String endpoint, Object body, int expectedStatus, ErrorCode errorCode) {
        RequestSpecification spec = given().contentType(ContentType.JSON);

        if (method != Method.GET && body != null && body != "") {
            spec = spec.body(body);
        }

        Response response = spec.when().request(method, endpoint);

        System.out.println("Response status: " + response.statusCode());
        System.out.println("Response body: " + response.asString());

        ApiError error = response.then()
                .statusCode(expectedStatus)
                .extract().as(ApiError.class);

        assertThat(error.code()).isEqualTo(errorCode);
    }

    public static ValidatableResponse assertSuccessfulRequest(Method method, String endpoint, Object body, int expectedStatus) {
        RequestSpecification spec = given().contentType(ContentType.JSON);

        if (method != Method.GET && method != Method.DELETE && body != null && body != "") {
            spec = spec.body(body);
        }

        Response response = spec.when().request(method, endpoint);
        System.out.println("Response status: " + response.statusCode());
        System.out.println("Response body: " + response.asString());
        return response.then().statusCode(expectedStatus);
    }

    public static int countSubstr(String haystack, String needle) {
        int count = 0;
        int idx = 0;

        while ((idx = haystack.indexOf(needle, idx)) != -1) {
            count++;
            idx += needle.length();
        }

        return count;
    }


    public static List<Feedback> expectedFeedback() {
        return List.of(
                new Feedback(1L, FeedbackType.REVIEW, 1L, 1L, FeedbackReason.REVIEW, "Reseña de prueba"),
                new Feedback(2L, FeedbackType.COMMENT, 2L, 2L, FeedbackReason.CALIFICATION, "Comentario de prueba")
        );
    }

    public static List<Appointment> expectedAppointments() {
        return List.of(
                Appointment.builder()
                        .id(1L)
                        .status(AppointmentStatus.PENDIENTE)
                        .specialistId(2L)
                        .specialityId(1L)
                        .patientId(1L)
                        .dateTime(LocalDateTime.of(2025, 12, 5, 15, 0))
                        .build(),

                Appointment.builder()
                        .id(2L)
                        .status(AppointmentStatus.PENDIENTE)
                        .specialistId(2L)
                        .specialityId(2L)
                        .patientId(4L)
                        .dateTime(LocalDateTime.of(2025, 12, 6, 11, 0))
                        .build(),

                Appointment.builder()
                        .id(3L)
                        .status(AppointmentStatus.PENDIENTE)
                        .specialistId(2L)
                        .specialityId(2L)
                        .patientId(1L)
                        .dateTime(LocalDateTime.of(2025, 12, 6, 16, 0))
                        .build(),

                Appointment.builder()
                        .id(4L)
                        .status(AppointmentStatus.PENDIENTE)
                        .specialistId(3L)
                        .specialityId(3L)
                        .patientId(4L)
                        .dateTime(LocalDateTime.of(2025, 12, 7, 11, 0))
                        .build(),

                Appointment.builder()
                        .id(5L)
                        .status(AppointmentStatus.PENDIENTE)
                        .specialistId(3L)
                        .specialityId(3L)
                        .patientId(4L)
                        .dateTime(LocalDateTime.of(2025, 12, 7, 14, 0))
                        .build()
        );
    }

    public static List<CachedUser> expectedCachedUsers() {
        return List.of(
                new CachedUser(1L, "PACIENTE", "Morty", "Smith", null, LocalDateTime.now()),
                new CachedUser(2L, "ESPECIALISTA", "Rick", "Sanchez", new HashSet<>(List.of(1L, 2L)), LocalDateTime.now()),
                new CachedUser(3L, "ESPECIALISTA", "Gregory", "House", new HashSet<>(List.of(3L)), LocalDateTime.now()),
                new CachedUser(4L, "PACIENTE", "Gonza", "Monar", null, LocalDateTime.now())
        );
    }

    public static List<Schedule> expectedSchedules() {
        return List.of(
                new Schedule(1L, 2L, DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(16, 0)),
                new Schedule(2L, 2L, DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(16, 0)),
                new Schedule(3L, 2L, DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(16, 0)),
                new Schedule(4L, 2L, DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(16, 0)),
                new Schedule(5L, 2L, DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(16, 0)),
                new Schedule(6L, 2L, DayOfWeek.SATURDAY, LocalTime.of(10, 0), LocalTime.of(16, 0)),
                new Schedule(7L, 3L, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
                new Schedule(8L, 3L, DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0))
        );
    }

    public static List<Survey> expectedSurveys() {
        return List.of(
                new Survey(1L, 1L, 5, "Comentario página web 1", 5, "Comentario especialista 1", 5, "Comentario turno 1"),
                new Survey(2L, 2L, 1, "Comentario página web 2", 1, "Comentario especialista 2", 1, "Comentario turno 2"),
                new Survey(3L, 3L, 3, "Comentario página web 3", 3, "Comentario especialista 3", 3, "Comentario turno 3")
        );
    }

}
