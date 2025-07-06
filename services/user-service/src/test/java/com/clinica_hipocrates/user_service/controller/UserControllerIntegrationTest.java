package com.clinica_hipocrates.user_service.controller;

import com.clinica_hipocrates.common.exception.ApiError;
import com.clinica_hipocrates.common.exception.ErrorCode;
import com.clinica_hipocrates.user_service.assembler.UserAssemblerRegistry;
import com.clinica_hipocrates.user_service.dto.AbstractUserDTO;
import com.clinica_hipocrates.user_service.dto.AdminDTO;
import com.clinica_hipocrates.user_service.dto.PatientDTO;
import com.clinica_hipocrates.user_service.dto.SpecialistDTO;
import com.clinica_hipocrates.user_service.model.*;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.*;

@ActiveProfiles("test")
@Sql(scripts = "/user-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerIntegrationTest {

    // TEST CONFIG
    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @LocalServerPort
    private int port;

    @BeforeAll
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static {
        mysql.start();
    }

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    UserAssemblerRegistry assembler;


    // HELPERS
    List<User> expectedUsers = List.of(
            buildUser1(),
            buildUser2(),
            buildUser3(),
            buildUser4()
    );
    private Patient buildUser1() {
        return Patient.builder()
                .id(1L)
                .userType(UserType.PACIENTE)
                .name("Morty")
                .lastname("Smith")
                .age(18)
                .dni(45177915)
                .email("morty@gmail.com")
                .profilePic("morty.png")
                .healthInsurance("OSDE")
                .profilePicAlt("morty2.png")
                .build();
    }
    private Specialist buildUser2() {
        Speciality s1 = new Speciality(1L, "Cardiología");
        Speciality s2 = new Speciality(2L, "Nefrología");
        List<Speciality> specialities = new ArrayList<Speciality>();
        specialities.add(s1);
        specialities.add(s2);

        return Specialist.builder()
                .id(2L)
                .userType(UserType.ESPECIALISTA)
                .name("Rick")
                .lastname("Sanchez")
                .age(60)
                .dni(10949875)
                .email("rick.sanchez@mail.com")
                .profilePic("rick.png")
                .specialities(specialities)
                .build();
    }
    private Specialist buildUser3() {
        Speciality s3 = new Speciality(3L, "Pediatría");
        List<Speciality> specialities = new ArrayList<Speciality>();
        specialities.add(s3);

        return Specialist.builder()
                .id(3L)
                .userType(UserType.ESPECIALISTA)
                .name("Gregory")
                .lastname("House")
                .age(52)
                .dni(27191594)
                .email("drhouse@mail.com")
                .profilePic("house.png")
                .specialities(specialities)
                .build();
    }
    private Admin buildUser4() {
        return Admin.builder()
                .id(4L)
                .userType(UserType.ADMIN)
                .name("Gonza")
                .lastname("Monar")
                .age(32)
                .dni(36149489)
                .email("gonza.monar@mail.com")
                .profilePic("gonza_monar.png")
                .build();
    }
    private Patient buildNewUser5() {
        return Patient.builder()
                .id(5L)
                .userType(UserType.PACIENTE)
                .name("Rodolfo")
                .lastname("García")
                .age(32)
                .dni(29647919)
                .email("rodolfo.garcia@mail.com")
                .profilePic("rodolfo-garcia.png")
                .healthInsurance("Swiss Medical")
                .profilePicAlt("rodolfo-garcia-alt.png")
                .build();
    }
    String postBody = """
        {
            "userType": "PACIENTE",
            "name": "Rodolfo",
            "lastname": "García",
            "age": 32,
            "dni": 29647919,
            "email": "rodolfo.garcia@mail.com",
            "profilePic": "rodolfo-garcia.png",
            "healthInsurance": "Swiss Medical",
            "profilePicAlt": "rodolfo-garcia-alt.png"
        }
        """;
    String putBody = """
        {
            "userType": "ESPECIALISTA",
            "name": "Rick",
            "lastname": "Sanchez",
            "age": 60,
            "dni": 10949875,
            "email": "rick.sanchez@mail.com",
            "profilePic": "rick.png",
            "specialities": [
                    {
                        "id": 1,
                        "name": "Cardiología"
                    },
                    {
                        "id": 2,
                        "name": "Nefrología"
                    }
                ]
        }
        """;
    

    private void sendFailedRequest(Method method, String endpoint, Object body, int expectedStatus, ErrorCode errorCode) {
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

    private ValidatableResponse sendSuccessfulRequest(Method method, String endpoint, Object body, int expectedStatus) {
        RequestSpecification spec = given().contentType(ContentType.JSON);

        if (method != Method.GET && method != Method.DELETE && body != null && body != "") {
            spec = spec.body(body);
        }

        Response response = spec.when().request(method, endpoint);
        System.out.println("Response status: " + response.statusCode());
        System.out.println("Response body: " + response.asString());
        return response.then().statusCode(expectedStatus);
    }

    // GET TESTS
    @Test
    void getAllUsers_shouldReturnOk200() {
        List<AbstractUserDTO> actualDtos = sendSuccessfulRequest(Method.GET, "/users", "", 200)
                .extract().body().as(new TypeRef<>() {});
        List<User> actualUsers = assembler.toEntityList(actualDtos);
        assertThat(actualUsers)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedUsers);
    }

    @Test
    void getUserById_withValidId_shouldReturnOk200() {
        List<Long> idList = List.of(1L, 2L, 3L, 4L);

        for (Long id : idList) {
            AbstractUserDTO dtoUser = sendSuccessfulRequest(Method.GET, "/users/" + id, "", 200).extract().as(AbstractUserDTO.class);
            User actualUser = assembler.toEntity(dtoUser);
            User expectedUser = expectedUsers.get(id.intValue() - 1);
            assertThat(actualUser).isEqualTo(expectedUser);
        }
    }

    @Test
    void getUserById_withInvalidId_shouldReturnNotFoundError404() {
        sendFailedRequest(Method.GET, "/users/6", "", 404, ErrorCode.NOT_FOUND);
    }

    /* POST TESTS */
    @Test
    void createUser_withValidFields_shouldReturnCreated201() {
        sendSuccessfulRequest(Method.POST, "/users", postBody, 201);
        AbstractUserDTO dtoUser = sendSuccessfulRequest(Method.GET, "/users/5", "", 200).extract().as(AbstractUserDTO.class);
        User actualUser = assembler.toEntity(dtoUser);
        assertThat(actualUser).isEqualTo(buildNewUser5());
    }

    @Test
    void createUser_withUnformattedNamesAndEmail_shouldReturnFormattedAndOk200() {
        String unformattedBody = postBody
                .replace("Rodolfo", "  rODoLfo")
                .replace("García", " gARCíA  ")
                .replace("rodolfo.garcia@mail.com", "  RODOLFO.gaRCia@MAIL.cOm  ");
        sendSuccessfulRequest(Method.POST, "/users", unformattedBody, 201);

        /* GET CHECK */
        AbstractUserDTO dtoUser = sendSuccessfulRequest(Method.GET, "/users/5", "", 200).extract().as(AbstractUserDTO.class);
        User actualUser = assembler.toEntity(dtoUser);
        assertThat(actualUser.getName()).isEqualTo("Rodolfo");
        assertThat(actualUser.getLastname()).isEqualTo("García");
        assertThat(actualUser.getEmail()).isEqualTo("rodolfo.garcia@mail.com");
    }

    private void sendBadPostRequest(String postBody, ErrorCode errorCode) {
        sendFailedRequest(Method.POST, "/users", postBody, 400, errorCode);
    }

    @Test
    void createUser_withBlankName_shouldReturnValidationError400() {
        sendBadPostRequest(postBody.replace("Rodolfo", ""), ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void createUser_withBlankLastname_shouldReturnValidationError400() {
        sendBadPostRequest(postBody.replace("García", ""), ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void createUser_withEmptyEmail_shouldReturnValidationError400() {
        sendBadPostRequest(postBody.replace("rodolfo.garcia@mail.com", ""), ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void createUser_withExistingDni_shouldReturnDuplicateError400() {
        sendBadPostRequest(postBody.replace("29647919", "45177915"), ErrorCode.DUPLICATE_RESOURCE);
    }

    @Test
    void createUser_withExistingEmail_shouldReturnDuplicateError400() {
        sendBadPostRequest(postBody.replace("rodolfo.garcia@mail.com", "rick.sanchez@mail.com"), ErrorCode.DUPLICATE_RESOURCE);
    }

    @Test
    void createUser_withNegativeAge_shouldReturnValidationError400() {
        sendBadPostRequest(postBody.replace("32", "-32"), ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void createUser_withNegativeDni_shouldReturnValidationError400() {
        sendBadPostRequest(postBody.replace("29647919", "-29647919"), ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void createUser_withHugeDni_shouldReturnValidationError400() {
        sendBadPostRequest(postBody.replace("29647919", "3000000000"), ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void createUser_withNullUserType_shouldReturnValidationError400() {
        sendBadPostRequest(postBody.replace("\"PACIENTE\"", "null"), ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void createUser_withInvalidUserType_shouldReturnValidationError400() {
        sendBadPostRequest(postBody.replace("PACIENTE", "SUPERADMIN"), ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void createUser_withEmptyBody_shouldReturnValidationError400() {
        sendBadPostRequest("{ }", ErrorCode.VALIDATION_ERROR);
    }

    /* PUT TESTS */
    @Test
    void updateUser_withValidIdAndFields_shouldReturnOk200() {
        String alteredBody = putBody
                .replace("Rick", "Santiago")
                .replace("Sanchez", "Gomez")
                .replace("10949875", "10999555")
                .replace("60", "62")
                .replace("rick.sanchez@mail.com", "rick.sanchez@gmail.com");
        sendSuccessfulRequest(Method.PUT, "/users/2", alteredBody, 200);

        AbstractUserDTO updatedUser = sendSuccessfulRequest(Method.GET, "/users/2", "", 200)
                .extract().body().as(AbstractUserDTO.class);

        assertThat(updatedUser.getName()).isEqualTo("Santiago");
        assertThat(updatedUser.getLastname()).isEqualTo("Gomez");
        assertThat(updatedUser.getDni()).isEqualTo(10999555);
        assertThat(updatedUser.getAge()).isEqualTo(62);
        assertThat(updatedUser.getEmail()).isEqualTo("rick.sanchez@gmail.com");
    }

    @Test
    void updateSpecialist_withValidIdAndNewSpecialities_shouldReturnOk200() {
        String alteredBody = putBody
                .replace("\"id\": 1,", "\"id\": 3,")
                .replace("Cardiología", "Pediatría");
        sendSuccessfulRequest(Method.PUT, "/users/2", alteredBody, 200);

        SpecialistDTO updatedUser = sendSuccessfulRequest(Method.GET, "/users/2", "", 200)
                .extract().body().as(SpecialistDTO.class);
        List<Speciality> specialities = List.of(new Speciality(3L, "Pediatría"), new Speciality(2L, "Nefrología"));
        assertThat(updatedUser.getSpecialities())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(specialities);
    }

    @Test // Currently Admin has not Specific Fields -- Test for coverage & future class expansion
    void updateAdmin_withValidIdAndNewSpecificFields_shouldReturnOk200() {
        String originalUser4 = """
        {
            "userType": "ADMIN",
            "name": "Gonza",
            "lastname": "Monar",
            "age": 32,
            "dni": 36149489,
            "email": "gonza.monar@mail.com",
            "profilePic": "gonza_monar.png"
        }
        """;
        String alteredBody = originalUser4.replace("Gonza", "Gonzalo");
        alteredBody = alteredBody.replace("gonza.monar@mail.com", "gonza.monar@gmail.com");
        sendSuccessfulRequest(Method.PUT, "/users/4", alteredBody, 200);

        AdminDTO updatedUser = sendSuccessfulRequest(Method.GET, "/users/4", "", 200)
                .extract().body().as(AdminDTO.class);
        assertThat(updatedUser.getName()).isEqualTo("Gonzalo");
        assertThat(updatedUser.getEmail()).isEqualTo("gonza.monar@gmail.com");
    }

    @Test
    void updatePatient_withValidIdAndNewSpecificFields_shouldReturnOk200() {
        String originalUser1 = """
        {
            "userType": "PACIENTE",
            "name": "Morty",
            "lastname": "Smith",
            "age": 18,
            "dni": 45177915,
            "email": "morty@gmail.com",
            "profilePic": "morty.png",
            "healthInsurance": "OSDE",
            "profilePicAlt": "morty2.png"
        }
        """;
        String alteredBody = originalUser1.replace("OSDE", "OSDEPYM");
        alteredBody = alteredBody.replace("morty2.png", "morty_2.png");
        sendSuccessfulRequest(Method.PUT, "/users/1", alteredBody, 200);

        PatientDTO updatedUser = sendSuccessfulRequest(Method.GET, "/users/1", "", 200)
                .extract().body().as(PatientDTO.class);
        assertThat(updatedUser.getHealthInsurance()).isEqualTo("OSDEPYM");
        assertThat(updatedUser.getProfilePicAlt()).isEqualTo("morty_2.png");
    }

    private void sendBadPutRequest(String putBody, ErrorCode errorCode) {
        sendFailedRequest(Method.PUT, "/users/2", putBody, 400, errorCode);
    }

    @Test
    void updateUser_withBlankName_shouldReturnValidationError400() {
        sendBadPutRequest(putBody.replace("Rick", ""), ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void updateUser_withBlankLastname_shouldReturnValidationError400() {
        sendBadPutRequest(putBody.replace("Sanchez", ""), ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void updateUser_withEmptyEmail_shouldReturnValidationError400() {
        sendBadPutRequest(putBody.replace("rick.sanchez@mail.com", ""), ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void updateUser_withExistingDni_shouldReturnDuplicateError400() {
        sendBadPutRequest(putBody.replace("10949875", "45177915"), ErrorCode.DUPLICATE_RESOURCE);
    }

    @Test
    void updateUser_withExistingEmail_shouldReturnDuplicateError400() {
        sendBadPutRequest(putBody.replace("rick.sanchez@mail.com", "morty@gmail.com"), ErrorCode.DUPLICATE_RESOURCE);
    }

    @Test
    void updateUser_withNegativeAge_shouldReturnValidationError400() {
        sendBadPutRequest(putBody.replace("60", "-60"), ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void updateUser_withNegativeDni_shouldReturnValidationError400() {
        sendBadPutRequest(putBody.replace("10949875", "-10949875"), ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void updateUser_withHugeDni_shouldReturnValidationError400() {
        sendBadPutRequest(putBody.replace("10949875", "3000000000"), ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void updateUser_withNullUserType_shouldReturnValidationError400() {
        sendBadPutRequest(putBody.replace("\"ESPECIALISTA\"", "null"), ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void updateUser_withEmptyBody_shouldReturnValidationError400() {
        sendBadPutRequest("{ }", ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void updateUser_withInvalidId_shouldReturnNotFound404() {
        sendFailedRequest(Method.PUT, "/users/6", putBody, 404, ErrorCode.NOT_FOUND);
    }


    // DELETE TESTS
    @Test
    void deleteUser_withValidId_shouldReturnNoContent204() {
        sendSuccessfulRequest(Method.GET, "/users/1", "", 200);
        sendSuccessfulRequest(Method.DELETE, "/users/1", "", 204);
        sendFailedRequest(Method.GET, "/users/1", "", 404, ErrorCode.NOT_FOUND);
    }
    
    @Test
    void deleteUser_withInvalidId_shouldReturnNotFound404() {
        sendFailedRequest(Method.DELETE, "/users/6", "", 404, ErrorCode.NOT_FOUND);
    }
}
