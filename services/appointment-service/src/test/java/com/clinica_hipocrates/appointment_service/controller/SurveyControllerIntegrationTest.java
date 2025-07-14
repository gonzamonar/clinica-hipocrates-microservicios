package com.clinica_hipocrates.appointment_service.controller;

import com.clinica_hipocrates.appointment_service.assembler.SurveyDTOAssembler;
import com.clinica_hipocrates.appointment_service.controller.shared.IntegrationTest;
import com.clinica_hipocrates.appointment_service.model.Survey;
import com.clinica_hipocrates.common.exception.ErrorCode;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.Method;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.clinica_hipocrates.appointment_service.controller.shared.TestUtils.*;

@ActiveProfiles("test")
@Sql(scripts = "/controllers-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class SurveyControllerIntegrationTest extends IntegrationTest {

    private static final String ENDPOINT = "/surveys";

    @Autowired
    SurveyDTOAssembler assembler;


    // GET TESTS
    @Test
    void getAllSurvey_shouldReturnOk200() {
        assertGetAll(ENDPOINT, new TypeRef<>(){}, assembler, expectedSurveys());
    }

    @Test
    void getSurveyById_withValidId_shouldReturnOk200() {
        for (Long id : List.of(1L, 2L, 3L)) {
            assertGetOne(ENDPOINT, id, new TypeRef<>(){}, assembler, expectedSurveys().get(id.intValue() - 1));
        }
    }

    @Test
    void getSurveyById_withValidId_bodyResponseShouldHaveHATEOASLinks() {
        for (Long id : List.of(1L, 2L, 3L)) {
            assertHATEOASLinks(ENDPOINT, id);
        }
    }

    @Test
    void getSurveyById_withInvalidId_shouldReturnNotFoundError404() {
        assertFailedRequest(Method.GET, ENDPOINT + "/6", "", 404, ErrorCode.NOT_FOUND);
    }


    /* POST TESTS */
    String postBody = """
        {
            "appointmentId": 4,
            "websiteCalification": 3,
            "websiteComment": "Comentario página web",
            "specialistCalification": 3,
            "specialistComment": "Comentario especialista",
            "appointmentCalification": 3,
            "appointmentComment": "Comentario turno"
        }
    """;

    private void assertPostBadRequest(String postBody, ErrorCode errorCode) {
        assertFailedRequest(Method.POST, ENDPOINT, postBody, 400, errorCode);
    }

    @Test
    void createSurvey_withValidFields_shouldReturnCreated201() {
        Survey newSurvey = new Survey(4L, 4L,
                3, "Comentario página web",
                3, "Comentario especialista",
                3, "Comentario turno");
        assertCreateOne(ENDPOINT, postBody, 4L,  new TypeRef<>() {}, assembler, newSurvey);
    }

    @Test
    void createSurvey_withEmptyAppointmentId_shouldReturnValidationError400() {
        assertPostBadRequest(postBody.replace("4", ""), ErrorCode.VALIDATION_ERROR);
    }


    /* PUT TESTS */
    String putBody = """
        {
            "appointmentId": 4,
            "websiteCalification": 3,
            "websiteComment": "Comentario página web",
            "specialistCalification": 3,
            "specialistComment": "Comentario especialista",
            "appointmentCalification": 3,
            "appointmentComment": "Comentario turno"
        }
    """;

    private void assertPutBadRequest(String putBody, ErrorCode errorCode) {
        assertFailedRequest(Method.PUT, ENDPOINT + "/1", putBody, 400, errorCode);
    }

    @Test
    void updateSurvey_withValidIdAndFields_shouldReturnOk200() {
        String alteredBody = putBody.replace("\"appointmentId\": 4", "\"appointmentId\": 6")
                .replace("\"websiteCalification\": 3", "\"websiteCalification\": 2")
                .replace("Comentario página web", "Gran web")
                .replace("\"specialistCalification\": 3", "\"specialistCalification\": 1")
                .replace("Comentario especialista", "Pésimo especialista")
                .replace("\"appointmentCalification\": 3", "\"appointmentCalification\": 5")
                .replace("Comentario turno", "Un muy buen turno");

        Survey expectedSurvey = new Survey(1L, 6L,
                2, "Gran web",
                1, "Pésimo especialista",
                5, "Un muy buen turno");

        assertUpdateOne(ENDPOINT, 1L, alteredBody, new TypeRef<>() {}, assembler, expectedSurvey);
    }

    @Test
    void updateSurvey_withBlankAppointmentId_shouldReturnValidationError400() {
        assertPutBadRequest(putBody.replace("\"appointmentId\": 4", "\"appointmentId\": "), ErrorCode.VALIDATION_ERROR);
    }

    // DELETE TESTS
    @Test
    void deleteSurvey_withValidId_shouldReturnNoContent204() {
        for (Long id : List.of(1L, 2L)) {
            assertDeleteOne(ENDPOINT, id);
        }
    }

    @Test
    void deleteSurvey_withInvalidId_shouldReturnNotFound404() {
        assertFailedRequest(Method.DELETE, ENDPOINT + "/6", "", 404, ErrorCode.NOT_FOUND);
    }


}
