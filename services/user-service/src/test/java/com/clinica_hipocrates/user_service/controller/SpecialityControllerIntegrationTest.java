package com.clinica_hipocrates.user_service.controller;

import com.clinica_hipocrates.common.exception.ApiError;
import com.clinica_hipocrates.common.exception.ErrorCode;
import com.clinica_hipocrates.user_service.assembler.SpecialityDTOAssembler;
import com.clinica_hipocrates.user_service.dto.SpecialityDTO;
import com.clinica_hipocrates.user_service.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.MySQLContainer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@ActiveProfiles("test")
@Sql(scripts = "/speciality-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpecialityControllerIntegrationTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    SpecialityDTOAssembler assembler;

    /* GET TESTS */
    @Test
    void getAllSpecialities_shouldReturnOk200() {
        ResponseEntity<List<SpecialityDTO>> response = restTemplate.exchange(
                "/specialities",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SpecialityDTO>>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.hasBody()).isTrue();
        List<SpecialityDTO> body = response.getBody();
        assertThat(body)
                .isNotNull()
                .isNotEmpty();
        assertThat(body.get(0).getId()).isEqualTo(1L);
        assertThat(body.get(0).getName()).isEqualTo("Cardiología");
        assertThat(body.get(1).getId()).isEqualTo(2L);
        assertThat(body.get(1).getName()).isEqualTo("Nefrología");
        assertThat(body.get(2).getId()).isEqualTo(3L);
        assertThat(body.get(2).getName()).isEqualTo("Pediatría");
    }

    @Test
    void getSpecialityById_withValidId_shouldReturnOk200() {
        ResponseEntity<SpecialityDTO> response = restTemplate.getForEntity("/specialities/2", SpecialityDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        SpecialityDTO body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getId()).isEqualTo(2L);
        assertThat(body.getName()).isEqualTo("Nefrología");
    }

    @Test
    void getSpecialityById_withInvalidId_shouldReturnNotFoundError404() {
        ResponseEntity<ApiError> response = restTemplate.getForEntity("/specialities/6", ApiError.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.hasBody()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo(ErrorCode.NOT_FOUND);
    }

    @Test
    void getDoctorsBySpeciality_shouldReturnOk200() {
        ResponseEntity<List<User>> response = restTemplate.exchange(
                "/specialities/1/doctors",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.hasBody()).isTrue();
        List<User> body = response.getBody();
        assertThat(body).isNotNull().isEmpty();
    }


    /* POST TESTS */
    @Test
    void createSpeciality_withValidName_shouldReturnCreated201() {
        SpecialityDTO request = new SpecialityDTO();
        request.setName("Kinesiología");

        ResponseEntity<SpecialityDTO> response = restTemplate.postForEntity(
                "/specialities",
                request,
                SpecialityDTO.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.hasBody()).isTrue();
        SpecialityDTO body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getId()).isEqualTo(4L);
        assertThat(body.getName()).isEqualTo("Kinesiología");

        ResponseEntity<SpecialityDTO> responseGet = restTemplate.getForEntity("/specialities/4", SpecialityDTO.class);
        SpecialityDTO bodyGET = responseGet.getBody();
        assertThat(bodyGET).isNotNull();
        assertThat(bodyGET.getId()).isEqualTo(4L);
        assertThat(bodyGET.getName()).isEqualTo("Kinesiología");
    }

    @Test
    void createSpeciality_withUnformattedName_shouldReturnFormatedNameAndOk200() {
        SpecialityDTO request = new SpecialityDTO();
        request.setName("kiNESiolOGÍa");

        ResponseEntity<SpecialityDTO> response = restTemplate.postForEntity(
                "/specialities",
                request,
                SpecialityDTO.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.hasBody()).isTrue();
        SpecialityDTO body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getId()).isEqualTo(4L);
        assertThat(body.getName()).isEqualTo("Kinesiología");

        ResponseEntity<SpecialityDTO> responseGet = restTemplate.getForEntity("/specialities/4", SpecialityDTO.class);
        SpecialityDTO bodyGET = responseGet.getBody();
        assertThat(bodyGET).isNotNull();
        assertThat(bodyGET.getId()).isEqualTo(4L);
        assertThat(bodyGET.getName()).isEqualTo("Kinesiología");
    }

    @Test
    void createSpeciality_withBlankName_shouldReturnValidationError400() {
        SpecialityDTO request = new SpecialityDTO();
        request.setName("");

        ResponseEntity<ApiError> response = restTemplate.postForEntity(
                "/specialities",
                request,
                ApiError.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.hasBody()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo(ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void createSpeciality_withExistingName_shouldReturnDuplicateError400() {
        SpecialityDTO request = new SpecialityDTO();
        request.setName("Cardiología");

        ResponseEntity<ApiError> response = restTemplate.postForEntity(
                "/specialities",
                request,
                ApiError.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.hasBody()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo(ErrorCode.DUPLICATE_RESOURCE);
    }

    /* PUT TESTS */
    @Test
    void updateSpeciality_withValidIdAndName_shouldReturnOk200() {
        SpecialityDTO request = new SpecialityDTO();
        request.setName("Traumatología");
        HttpEntity<SpecialityDTO> entity = new HttpEntity<>(request);

        ResponseEntity<SpecialityDTO> response = restTemplate.exchange(
                "/specialities/3",
                HttpMethod.PUT,
                entity,
                SpecialityDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.hasBody()).isTrue();
        assertThat(response.getBody()).isNotNull();


    }

    @Test
    void updateSpeciality_withBlankName_shouldReturnValidationError400() {
        SpecialityDTO request = new SpecialityDTO();
        request.setName("");
        HttpEntity<SpecialityDTO> entity = new HttpEntity<>(request);

        ResponseEntity<ApiError> response = restTemplate.exchange(
                "/specialities/6",
                HttpMethod.PUT,
                entity,
                ApiError.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.hasBody()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo(ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void updateSpeciality_withExistingName_shouldReturnDuplicateError400() {
        SpecialityDTO request = new SpecialityDTO();
        request.setName("Cardiología");
        HttpEntity<SpecialityDTO> entity = new HttpEntity<>(request);

        ResponseEntity<ApiError> response = restTemplate.exchange(
                "/specialities/6",
                HttpMethod.PUT,
                entity,
                ApiError.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.hasBody()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo(ErrorCode.DUPLICATE_RESOURCE);
    }

    @Test
    void updateSpeciality_withInvalidId_shouldReturnNotFound404() {
        SpecialityDTO request = new SpecialityDTO();
        request.setName("Traumatología");
        HttpEntity<SpecialityDTO> entity = new HttpEntity<>(request);

        ResponseEntity<ApiError> response = restTemplate.exchange(
                "/specialities/6",
                HttpMethod.PUT,
                entity,
                ApiError.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.hasBody()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo(ErrorCode.NOT_FOUND);
    }

    /* DELETE TESTS */
    @Test
    void deleteSpeciality_withValidId_shouldReturnNoContent204() {
        ResponseEntity<SpecialityDTO> getResponse = restTemplate.getForEntity("/specialities/1", SpecialityDTO.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Void> response = restTemplate.exchange(
                "/specialities/1",
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<>() {}
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<SpecialityDTO> checkResponse = restTemplate.getForEntity("/specialities/1", SpecialityDTO.class);
        assertThat(checkResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteSpeciality_withInvalidId_shouldReturnNotFound404() {
        ResponseEntity<ApiError> response = restTemplate.exchange(
                "/specialities/6",
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<ApiError>() {}
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.hasBody()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo(ErrorCode.NOT_FOUND);
    }

    // This test applies to a resource used by another endpoint with a FK constraint.
    @Test
    void deleteSpeciality_withValidIdAndUsedResource_shouldReturnSqlConstraintConflict409() {
        ResponseEntity<ApiError> response = restTemplate.exchange(
                "/specialities/2",
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<ApiError>() {}
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.hasBody()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo(ErrorCode.DATA_INTEGRITY_VIOLATION);
    }
}
