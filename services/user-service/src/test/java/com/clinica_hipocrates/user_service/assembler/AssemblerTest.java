package com.clinica_hipocrates.user_service.assembler;

import com.clinica_hipocrates.user_service.dto.PatientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AssemblerTest {

    private UserAssemblerRegistry assembler;

    @BeforeEach
    void setUp() {
        assembler = new UserAssemblerRegistry(
                new AdminDTOAssembler(),
                new SpecialistDTOAssembler(),
                new PatientDTOAssembler()
        );
    }

    @Test
    void getAssembler_withNullUserType_shouldThrowIllegalArgumentException() throws IllegalArgumentException {
        PatientDTO dto = new PatientDTO();
        dto.setUserType(null);
        assertThrows(IllegalArgumentException.class, () -> assembler.toEntity(dto));
    }
}
