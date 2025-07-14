package com.clinica_hipocrates.appointment_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name="surveys")
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @Column(name = "id")
    Long id;

    @Column(name = "appointment_id", nullable = false)
    Long appointmentId;

    @Column(name = "website_calification", nullable = false)
    Integer websiteCalification;

    @Column(name = "website_comment", nullable = false)
    String websiteComment;

    @Column(name = "specialist_calification", nullable = false)
    Integer specialistCalification;

    @Column(name = "specialist_comment", nullable = false)
    String specialistComment;

    @Column(name = "appointment_calification", nullable = false)
    Integer appointmentCalification;

    @Column(name = "appointment_comment", nullable = false)
    String appointmentComment;
}
