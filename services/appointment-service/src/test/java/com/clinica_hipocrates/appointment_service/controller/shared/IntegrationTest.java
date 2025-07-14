package com.clinica_hipocrates.appointment_service.controller.shared;

import com.clinica_hipocrates.appointment_service.assembler.DTOAssembler;
import com.clinica_hipocrates.common.exception.ErrorCode;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.Method;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.clinica_hipocrates.appointment_service.controller.shared.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@TestConfiguration
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {

    static {
        SharedMySQLContainer.getInstance();
    }

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        SharedMySQLContainer container = SharedMySQLContainer.getInstance();
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @LocalServerPort
    protected int port;

    @BeforeEach
    void setUpRestAssured() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }


    // GET TESTS
    protected <D, E> void assertGetAll(
            String endpoint,
            TypeRef<List<D>> dtoType,
            DTOAssembler<E, D> assembler,
            List<E> expected
    ) {
        List<D> actualDtos = assertSuccessfulRequest(Method.GET, endpoint, "", 200)
                .extract().body().as(dtoType);
        List<E> actualEntities = assembler.toEntityList(actualDtos);

        assertThat(actualEntities)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    protected <D, E> void assertGetOne(
            String endpoint,
            Long id,
            TypeRef<D> dtoType,
            DTOAssembler<E, D> assembler,
            E expectedEntity
    ) {
        String idEndpoint = endpoint + "/" + id;
        D dto = assertSuccessfulRequest(Method.GET, idEndpoint, "", 200).extract().as(dtoType);
        E actualEntity = assembler.toEntity(dto);
        assertThat(actualEntity).isEqualTo(expectedEntity);
    }

    protected void assertHATEOASLinks(
            String endpoint,
            Long id
    ) {
        String idEndpoint = endpoint + "/" + id;
        String jsonBody = assertSuccessfulRequest(Method.GET, idEndpoint, "", 200).extract().body().asString();
        assertThat(countSubstr(jsonBody, "_links")).isEqualTo(1);
    }

    // POST TESTS
    protected <D, E> void assertCreateOne(
            String endpoint,
            String postBody,
            Long expectedId,
            TypeRef<D> dtoType,
            DTOAssembler<E, D> assembler,
            E newEntity
    ) {
        assertSuccessfulRequest(Method.POST, endpoint, postBody, 201);
        String getEndpoint = endpoint + "/" + expectedId.toString();
        D dto = assertSuccessfulRequest(Method.GET, getEndpoint, "", 200).extract().as(dtoType);
        E actualEntity = assembler.toEntity(dto);
        assertThat(actualEntity).isEqualTo(newEntity);
    }

    // PUT TESTS
    protected <D, E> void assertUpdateOne(
            String endpoint,
            Long idToUpdate,
            String putBody,
            TypeRef<D> dtoType,
            DTOAssembler<E, D> assembler,
            E updatedEntity
    ) {
        String putEndpoint = endpoint + "/" + idToUpdate.toString();
        assertSuccessfulRequest(Method.PUT, putEndpoint, putBody, 200);
        D dto = assertSuccessfulRequest(Method.GET, putEndpoint, "", 200).extract().as(dtoType);
        E actualEntity = assembler.toEntity(dto);
        assertThat(actualEntity).isEqualTo(updatedEntity);
    }

    // PATCH TESTS
    protected <D, E> void assertPatchOne(
            String endpoint,
            Long idToPatch,
            String patchField,
            String patchBody,
            TypeRef<D> dtoType,
            DTOAssembler<E, D> assembler,
            E patchedEntity
    ) {
        String patchEndpoint = endpoint + "/" + idToPatch.toString() + "/" + patchField;
        String getEndpoint = endpoint + "/" + idToPatch;
        assertSuccessfulRequest(Method.PATCH, patchEndpoint, patchBody, 200);
        D dto = assertSuccessfulRequest(Method.GET, getEndpoint, "", 200).extract().as(dtoType);
        E actualEntity = assembler.toEntity(dto);
        assertThat(actualEntity).isEqualTo(patchedEntity);
    }

    // DELETE TESTS
    protected void assertDeleteOne(
            String endpoint,
            Long idToDelete
    ) {
        String deleteEndpoint = endpoint + "/" + idToDelete.toString();
        assertSuccessfulRequest(Method.GET, deleteEndpoint, "", 200);
        assertSuccessfulRequest(Method.DELETE, deleteEndpoint, "", 204);
        assertFailedRequest(Method.GET, deleteEndpoint, "", 404, ErrorCode.NOT_FOUND);
    }
}
