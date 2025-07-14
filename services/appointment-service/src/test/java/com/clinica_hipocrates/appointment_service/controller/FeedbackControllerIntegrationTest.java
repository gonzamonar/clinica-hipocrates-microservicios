package com.clinica_hipocrates.appointment_service.controller;

import com.clinica_hipocrates.appointment_service.assembler.FeedbackDTOAssembler;
import com.clinica_hipocrates.appointment_service.controller.shared.IntegrationTest;
import com.clinica_hipocrates.appointment_service.model.Feedback;
import com.clinica_hipocrates.appointment_service.model.enums.FeedbackReason;
import com.clinica_hipocrates.appointment_service.model.enums.FeedbackType;
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
class FeedbackControllerIntegrationTest extends IntegrationTest {

    private static final String ENDPOINT = "/feedback";

    @Autowired
    FeedbackDTOAssembler assembler;


    // GET TESTS
    @Test
    void getAllFeedback_shouldReturnOk200() {
        assertGetAll(ENDPOINT, new TypeRef<>(){}, assembler, expectedFeedback());
    }

    @Test
    void getFeedbackById_withValidId_shouldReturnOk200() {
        for (Long id : List.of(1L, 2L)) {
            assertGetOne(ENDPOINT, id, new TypeRef<>(){}, assembler, expectedFeedback().get(id.intValue() - 1));
        }
    }

    @Test
    void getFeedbackById_withValidId_bodyResponseShouldHaveHATEOASLinks() {
        for (Long id : List.of(1L, 2L)) {
            assertHATEOASLinks(ENDPOINT, id);
        }
    }

    @Test
    void getFeedbackById_withInvalidId_shouldReturnNotFoundError404() {
        assertFailedRequest(Method.GET, ENDPOINT + "/6", "", 404, ErrorCode.NOT_FOUND);
    }


    /* POST TESTS */
    String postBody = """
        {
            "type": "REVIEW",
            "appointmentId": 3,
            "authorId": 2,
            "reason": "REVIEW",
            "text": "Nueva reseña"
        }
    """;

    private void assertPostBadRequest(String postBody, ErrorCode errorCode) {
        assertFailedRequest(Method.POST, ENDPOINT, postBody, 400, errorCode);
    }

    @Test
    void createFeedback_withValidFields_shouldReturnCreated201() {
        Feedback newFeedback = new Feedback(3L, FeedbackType.REVIEW, 3L, 2L, FeedbackReason.REVIEW, "Nueva reseña");
        assertCreateOne(ENDPOINT, postBody, 3L,  new TypeRef<>() {}, assembler, newFeedback);
    }

    @Test
    void createFeedback_withEmptyAppointmentId_shouldReturnValidationError400() {
        assertPostBadRequest(postBody.replace("3", ""), ErrorCode.VALIDATION_ERROR);
    }


    /* PUT TESTS */
    String putBody = """
        {
            "type": "REVIEW",
            "appointmentId": 1,
            "authorId": 1,
            "reason": "REVIEW",
            "text": "Reseña de prueba"
        }
    """;

    private void assertPutBadRequest(String putBody, ErrorCode errorCode) {
        assertFailedRequest(Method.PUT, ENDPOINT + "/1", putBody, 400, errorCode);
    }

    @Test
    void updateFeedback_withValidIdAndFields_shouldReturnOk200() {
        String alteredBody = putBody
                .replace("\"type\": \"REVIEW\"", "\"type\": \"COMMENT\"")
                .replace("\"appointmentId\": 1,", "\"appointmentId\": 2,")
                .replace("\"authorId\": 1,", "\"authorId\": 2,")
                .replace("\"reason\": \"REVIEW\"", "\"reason\": \"CANCELATION\"")
                .replace("Reseña de prueba", "Texto actualizado del comentario de prueba");

        Feedback expectedFeedback = new Feedback(1L, FeedbackType.COMMENT, 2L, 2L,
                                                    FeedbackReason.CANCELATION, "Texto actualizado del comentario de prueba");

        assertUpdateOne(ENDPOINT, 1L, alteredBody, new TypeRef<>() {}, assembler, expectedFeedback);
    }

    @Test
    void updateFeedback_withBlankReason_shouldReturnValidationError400() {
        assertPutBadRequest(putBody.replace("\"reason\": \"REVIEW\"", "\"reason\": \"\""), ErrorCode.VALIDATION_ERROR);
    }

    // DELETE TESTS
    @Test
    void deleteFeedback_withValidId_shouldReturnNoContent204() {
        for (Long id : List.of(1L, 2L)) {
            assertDeleteOne(ENDPOINT, id);
        }
    }

    @Test
    void deleteFeedback_withInvalidId_shouldReturnNotFound404() {
        assertFailedRequest(Method.DELETE, ENDPOINT + "/6", "", 404, ErrorCode.NOT_FOUND);
    }
}
