package com.clinica_hipocrates.appointment_service.controller;

import com.clinica_hipocrates.appointment_service.assembler.ScheduleDTOAssembler;
import com.clinica_hipocrates.appointment_service.controller.shared.IntegrationTest;
import com.clinica_hipocrates.appointment_service.model.Schedule;
import com.clinica_hipocrates.common.exception.ErrorCode;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.Method;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.stream.LongStream;

import static com.clinica_hipocrates.appointment_service.controller.shared.TestUtils.*;

@ActiveProfiles("test")
@Sql(scripts = "/controllers-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ScheduleControllerIntegrationTest extends IntegrationTest {

    private static final String ENDPOINT = "/schedules";

    @Autowired
    ScheduleDTOAssembler assembler;


    // GET TESTS
    @Test
    void getAllSchedules_shouldReturnOk200() {
        assertGetAll(ENDPOINT, new TypeRef<>(){}, assembler, expectedSchedules());
    }

    @Test
    void getScheduleById_withValidId_shouldReturnOk200() {
        for (Long id : LongStream.rangeClosed(1, 8).boxed().toList()) {
            assertGetOne(ENDPOINT, id, new TypeRef<>(){}, assembler, expectedSchedules().get(id.intValue() - 1));
        }
    }

    @Test
    void getScheduleById_withValidId_bodyResponseShouldHaveHATEOASLinks() {
        for (Long id : LongStream.rangeClosed(1, 8).boxed().toList()) {
            assertHATEOASLinks(ENDPOINT, id);
        }
    }

    @Test
    void getScheduleById_withInvalidId_shouldReturnNotFoundError404() {
        assertFailedRequest(Method.GET, ENDPOINT + "/10", "", 404, ErrorCode.NOT_FOUND);
    }


    /* POST TESTS */
    String postBody = """
        {
            "specialistId": 3,
            "day": "MONDAY",
            "start": "15:00",
            "end": "18:00"
        }
    """;

    private void assertPostBadRequest(String postBody, ErrorCode errorCode) {
        assertFailedRequest(Method.POST, ENDPOINT, postBody, 400, errorCode);
    }

    @Test
    void createSchedule_withValidFields_shouldReturnCreated201() {
        Schedule newSchedule = new Schedule(9L, 3L, DayOfWeek.MONDAY, LocalTime.of(15, 0), LocalTime.of(18, 0));
        assertCreateOne(ENDPOINT, postBody, 9L,  new TypeRef<>() {}, assembler, newSchedule);
    }

    @Test
    void createSchedule_withEmptySpecialistId_shouldReturnValidationError400() {
        assertPostBadRequest(postBody.replace("\"specialistId\": 3", "\"specialistId\": "), ErrorCode.VALIDATION_ERROR);
    }


    /* PUT TESTS */
    String putBody = """
        {
            "specialistId": 2,
            "day": "THURSDAY",
            "start": "10:00",
            "end": "16:00"
        }
    """;

    private void assertPutBadRequest(String putBody, ErrorCode errorCode) {
        assertFailedRequest(Method.PUT, ENDPOINT + "/1", putBody, 400, errorCode);
    }

    @Test
    void updateSchedule_withValidIdAndFields_shouldReturnOk200() {
        String alteredBody = putBody
                .replace("2", "3")
                .replace("THURSDAY", "FRIDAY")
                .replace("10:00", "09:00")
                .replace("16:00", "15:00");

        Schedule expectedSchedule = new Schedule(4L, 3L, DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(15, 0));

        assertUpdateOne(ENDPOINT, 4L, alteredBody, new TypeRef<>() {}, assembler, expectedSchedule);
    }

    @Test
    void updateSchedule_withBlankSpecialistId_shouldReturnValidationError400() {
        assertPutBadRequest(putBody.replace("2", ""), ErrorCode.VALIDATION_ERROR);
    }

    // DELETE TESTS
    @Test
    void deleteSchedule_withValidId_shouldReturnNoContent204() {
        for (Long id : LongStream.rangeClosed(1, 8).boxed().toList()) {
            assertDeleteOne(ENDPOINT, id);
        }
    }

    @Test
    void deleteSchedule_withInvalidId_shouldReturnNotFound404() {
        assertFailedRequest(Method.DELETE, ENDPOINT + "/10", "", 404, ErrorCode.NOT_FOUND);
    }
}
