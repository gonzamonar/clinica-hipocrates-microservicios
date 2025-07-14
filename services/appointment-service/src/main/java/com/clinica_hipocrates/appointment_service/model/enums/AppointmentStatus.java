package com.clinica_hipocrates.appointment_service.model.enums;

public enum AppointmentStatus {
    PENDIENTE,
    CANCELADO,
    RECHAZADO,
    ACEPTADO,
    REALIZADO;

    public boolean canTransitionTo(AppointmentStatus newStatus) {
        return switch (this) {
            case PENDIENTE -> newStatus == CANCELADO || newStatus == RECHAZADO || newStatus == ACEPTADO;
            case ACEPTADO -> newStatus == REALIZADO;
            default -> false;
        };
    }
}
