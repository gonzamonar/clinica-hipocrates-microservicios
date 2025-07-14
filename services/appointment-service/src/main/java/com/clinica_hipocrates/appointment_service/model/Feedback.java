package com.clinica_hipocrates.appointment_service.model;

import com.clinica_hipocrates.appointment_service.model.enums.FeedbackReason;
import com.clinica_hipocrates.appointment_service.model.enums.FeedbackType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name="feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @Column(name = "id")
    Long id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    FeedbackType type;

    @Column(name = "appointment_id", nullable = false)
    Long appointmentId;

    @Column(name = "author_id", nullable = false)
    Long authorId;

    @Column(name = "reason", nullable = false)
    @Enumerated(EnumType.STRING)
    FeedbackReason reason;

    @Column(name = "text", nullable = false)
    String text;
}
