package com.clinica_hipocrates.appointment_service.controller;

import com.clinica_hipocrates.appointment_service.assembler.AppointmentDTOAssembler;
import com.clinica_hipocrates.appointment_service.controller.shared.IntegrationTest;
import com.clinica_hipocrates.appointment_service.model.Appointment;
import com.clinica_hipocrates.appointment_service.model.enums.AppointmentStatus;
import com.clinica_hipocrates.common.exception.ErrorCode;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.Method;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static com.clinica_hipocrates.appointment_service.controller.shared.TestUtils.*;

@ActiveProfiles("test")
@Sql(scripts = "/controllers-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AppointmentControllerIntegrationTest extends IntegrationTest {

    private static final String ENDPOINT = "/appointments";
    
    @Autowired
    AppointmentDTOAssembler assembler;


    // GET TESTS
    @Test
    void getAllAppointment_shouldReturnOk200() {
        assertGetAll(ENDPOINT, new TypeRef<>(){}, assembler, expectedAppointments());
    }

    @Test
    void getAppointmentById_withValidId_shouldReturnOk200() {
        for (Long id : List.of(1L, 2L, 3L, 4L, 5L)) {
            assertGetOne(ENDPOINT, id, new TypeRef<>(){}, assembler, expectedAppointments().get(id.intValue() - 1));
        }
    }

    @Test
    void getAppointmentById_withValidId_bodyResponseShouldHaveHATEOASLinks() {
        for (Long id : List.of(1L, 2L, 3L, 4L, 5L)) {
            assertHATEOASLinks(ENDPOINT, id);
        }
    }

    @Test
    void getAppointmentById_withInvalidId_shouldReturnNotFoundError404() {
        assertFailedRequest(Method.GET, ENDPOINT + "/6", "", 404, ErrorCode.NOT_FOUND);
    }


    /* POST TESTS */
    String postBody = """
        {
            "specialistId": 2,
            "specialityId": 1,
            "patientId": 4,
            "dateTime": "2025-12-05T11:00:00"
        }
        """;

    private void assertPostBadRequest(String postBody, ErrorCode errorCode) {
        assertFailedRequest(Method.POST, ENDPOINT, postBody, 400, errorCode);
    }

    @Test
    void createAppointment_withValidFields_shouldReturnCreated201() {
        Appointment expectedAppointment =
                Appointment.builder()
                        .id(6L)
                        .status(AppointmentStatus.PENDIENTE)
                        .specialistId(2L)
                        .specialityId(1L)
                        .patientId(4L)
                        .dateTime(LocalDateTime.of(2025, 12, 5, 11, 0))
                        .build();
        assertCreateOne(ENDPOINT, postBody, 6L,  new TypeRef<>() {}, assembler, expectedAppointment);
    }

    @Test
    void createAppointment_withAlreadyExistingAppointment_shouldReturnValidationError400() {
        // DUPLICATION OF APPOINTMENT Nº1
        String existingAppointmentBody = """
        {
            "specialistId": 2,
            "specialityId": 1,
            "patientId": 1,
            "dateTime": "2025-12-05T15:00:00"
        }
        """;
        assertPostBadRequest(existingAppointmentBody, ErrorCode.DUPLICATE_RESOURCE);
    }

    @Test
    void createAppointment_withSameDateTimeAndSpecialistThanExistingAppointment_shouldReturnValidationError400() {
        // SAME DATETIME AND SPECIALIST THAN APPOINTMENT Nº2
        String existingAppointmentBody = """
        {
            "specialistId": 2,
            "specialityId": 2,
            "patientId": 1,
            "dateTime": "2025-12-06T11:00:00"
        }
        """;
        assertPostBadRequest(existingAppointmentBody, ErrorCode.DUPLICATE_RESOURCE);
    }

    @Test
    void createAppointment_withSameDateTimeAndPatientThanExistingAppointment_shouldReturnValidationError400() {
        // SAME DATETIME AND PATIENT THAN APPOINTMENT Nº3
        String existingAppointmentBody = """
        {
            "specialistId": 3,
            "specialityId": 3,
            "patientId": 1,
            "dateTime": "2025-12-06T16:00:00"
        }
        """;
        assertPostBadRequest(existingAppointmentBody, ErrorCode.DUPLICATE_RESOURCE);
    }

    @Test
    void createAppointment_withEmptySpecialistId_shouldReturnValidationError400() {
        assertPostBadRequest(postBody.replace("\"specialistId\": 2", "\"specialistId\": "), ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void createAppointment_withNonExistingSpecialistId_shouldReturnBadRequest400() {
        assertPostBadRequest(postBody.replace("\"specialistId\": 2", "\"specialistId\": 10"), ErrorCode.BAD_REQUEST);
    }

    @Test
    void createAppointment_withAPatientIdAsSpecialist_shouldReturnBadRequest400() {
        assertPostBadRequest(postBody.replace("\"specialistId\": 2", "\"specialistId\": 4"), ErrorCode.BAD_REQUEST);
    }

    @Test
    void createAppointment_withEmptySpecialityId_shouldReturnValidationError400() {
        assertPostBadRequest(postBody.replace("\"specialityId\": 1", "\"specialityId\": "), ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void createAppointment_withNonExistingSpecialityId_shouldReturnBadRequest400() {
        assertPostBadRequest(postBody.replace("\"specialityId\": 1", "\"specialityId\": 10"), ErrorCode.BAD_REQUEST);
    }

    @Test
    void createAppointment_withExistingSpecialityButWrongSpecialistCombination_shouldReturnBadRequest400() {
        assertPostBadRequest(postBody.replace("\"specialityId\": 1", "\"specialityId\": 3"), ErrorCode.BAD_REQUEST);
    }

    @Test
    void createAppointment_withEmptyPatientId_shouldReturnValidationError400() {
        assertPostBadRequest(postBody.replace("\"patientId\": 4", "\"patientId\": "), ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void createAppointment_withNonExistingPatientId_shouldReturnBadRequest400() {
        assertPostBadRequest(postBody.replace("\"patientId\": 4", "\"patientId\": 10"), ErrorCode.BAD_REQUEST);
    }

    @Test
    void createAppointment_withAnSpecialistIdAsPatientId_shouldReturnBadRequest400() {
        assertPostBadRequest(postBody.replace("\"patientId\": 4", "\"patientId\": 2"), ErrorCode.BAD_REQUEST);
    }

    @Test
    void createAppointment_withEmptyDateTime_shouldReturnValidationError400() {
        assertPostBadRequest(postBody.replace("2025-12-05T11:00:00", ""), ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void createAppointment_withPastDateTime_shouldReturnValidationError400() {
        assertPostBadRequest(postBody.replace("2025", "2024"), ErrorCode.VALIDATION_ERROR);
    }


    /* PUT TESTS */
    String putBody = """
        {
            "status": "PENDIENTE",
            "specialistId": 2,
            "specialityId": 1,
            "patientId": 1,
            "dateTime": "2025-12-05T15:00:00"
        }
        """;

    private void assertPutBadRequest(String putBody, ErrorCode errorCode) {
        assertFailedRequest(Method.PUT, ENDPOINT + "/1", putBody, 400, errorCode);
    }

    @Test
    void updateAppointment_withValidIdAndFields_shouldReturnOk200() {
        String alteredBody = putBody
                .replace("PENDIENTE", "ACEPTADO")
                .replace("\"specialistId\": 2", "\"specialistId\": 3")
                .replace("\"specialityId\": 1", "\"specialityId\": 3")
                .replace("\"patientId\": 1", "\"patientId\": 4")
                .replace("\"dateTime\": \"2025-12-05T15:00:00\"", "\"dateTime\": \"2025-12-21T16:00:00\"");

        Appointment expectedAppointment =
                Appointment.builder()
                        .id(1L)
                        .status(AppointmentStatus.ACEPTADO)
                        .specialistId(3L)
                        .specialityId(3L)
                        .patientId(4L)
                        .dateTime(LocalDateTime.of(2025, 12, 21, 16, 0))
                        .build();

        assertUpdateOne(ENDPOINT, 1L, alteredBody, new TypeRef<>() {}, assembler, expectedAppointment);
    }

    @Test
    void updateAppointment_withBlankStatus_shouldReturnValidationError400() {
        assertPutBadRequest(putBody.replace("PENDIENTE", ""), ErrorCode.VALIDATION_ERROR);
    }

    // DELETE TESTS
    @Test
    void deleteAppointment_withValidId_shouldReturnNoContent204() {
        for (Long id : List.of(1L,2L,3L,4L,5L)) {
            assertDeleteOne(ENDPOINT, id);
        }
    }

    @Test
    void deleteAppointment_withInvalidId_shouldReturnNotFound404() {
        assertFailedRequest(Method.DELETE, ENDPOINT + "/6", "", 404, ErrorCode.NOT_FOUND);
    }
}
