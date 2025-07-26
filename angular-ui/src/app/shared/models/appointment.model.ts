import { AppointmentStatus } from "./enums/appointment-status.enum";

export interface Appointment {
    id: number;
    status: AppointmentStatus;
    specialistId: number;
    specialityId: number;
    patientId: number;
    dateTime: Date;
    commentId: number | null;
    reviewId: number | null;
    surveyId: number | null;
    rating: number | null;
    medicalHistoryId: number | null;
}
