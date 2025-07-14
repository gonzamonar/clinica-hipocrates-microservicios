package com.clinica_hipocrates.appointment_service.assembler;

import com.clinica_hipocrates.appointment_service.dto.AppointmentPostDTO;
import com.clinica_hipocrates.appointment_service.model.Appointment;
import com.clinica_hipocrates.appointment_service.model.enums.AppointmentStatus;
import org.springframework.stereotype.Component;

@Component
public class AppointmentRequestDTOAssembler {

    public Appointment toEntity(AppointmentPostDTO dto) {
        Appointment appointment = Appointment.builder().build();
        appointment.setStatus(AppointmentStatus.PENDIENTE);
        appointment.setSpecialistId(dto.getSpecialistId());
        appointment.setSpecialityId(dto.getSpecialityId());
        appointment.setPatientId(dto.getPatientId());
        appointment.setDateTime(dto.getDateTime());
        appointment.setCommentId(null);
        appointment.setReviewId(null);
        appointment.setSurveyId(null);
        appointment.setRating(null);
        appointment.setMedicalHistoryId(null);
        return appointment;
    }
}
