package com.clinica_hipocrates.appointment_service.model;

import com.clinica_hipocrates.appointment_service.model.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Column(name = "specialist_id")
    private Long specialistId;

    @Column(name = "speciality_id")
    private Long specialityId;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "survey_id")
    private Long surveyId;
    
    @Column(name = "rating")
    private Integer rating;
    
    @Column(name = "medical_history_id")
    private Long medicalHistoryId;

}
