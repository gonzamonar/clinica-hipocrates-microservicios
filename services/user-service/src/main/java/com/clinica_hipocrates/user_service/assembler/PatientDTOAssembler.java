package com.clinica_hipocrates.user_service.assembler;

import com.clinica_hipocrates.user_service.dto.PatientDTO;
import com.clinica_hipocrates.user_service.model.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientDTOAssembler extends AbstractUserDTOAssembler<Patient, PatientDTO> {

    @Override
    protected PatientDTO createDtoInstance() {
        return new PatientDTO();
    }

    @Override
    protected Patient createEntityInstance() {
        return new Patient();
    }

    @Override
    protected void mapSpecificFieldsToDTO(Patient user, PatientDTO dto) {
        dto.setHealthInsurance(user.getHealthInsurance());
        dto.setProfilePicAlt(user.getProfilePicAlt());
    }

    @Override
    protected void mapSpecificFieldsToEntity(PatientDTO dto, Patient user) {
        user.setHealthInsurance(dto.getHealthInsurance());
        user.setProfilePicAlt(dto.getProfilePicAlt());
    }
}
