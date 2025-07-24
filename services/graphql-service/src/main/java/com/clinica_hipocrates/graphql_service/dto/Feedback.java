package com.clinica_hipocrates.graphql_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    private Long id;
    private FeedbackType type;
    private Long appointmentId;
    private User author;
    private String reason;
    private String text;
}
