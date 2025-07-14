package com.clinica_hipocrates.appointment_service.model.enums;

public enum FeedbackReason {
    CANCELATION("Motivo de cancelación"),
    REJECTION("Motivo de rechazo"),
    CALIFICATION("Comentario de calificación"),
    REVIEW("Reseña del turno");

    private final String label;

    FeedbackReason(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
