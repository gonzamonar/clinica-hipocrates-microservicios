import { AxiosInstance } from "axios";
import { getRandomNumber } from "../utils/helpers";
import { Appointment } from "../models/appointment.model";
import { AppointmentStatus } from "../models/enums/appointment-status.enum";
import { getRandomFeedback } from "../feedback/getRandomFeedback";
import { FeedbackReason } from "../models/enums/feedback-reason.enum";
import { getRandomSurvey } from "../survey/getRandomSurvey";

export async function finishAppointments(client: AxiosInstance) {
    const appointments: Appointment[] = (await client.get('/appointments')).data;
    const pendingAppointments: Appointment[] = appointments.filter(a => a.status == AppointmentStatus.PENDIENTE);
    const count: number = pendingAppointments.length;

    for (let i = 0; i < count; i++) {
        const appointment = pendingAppointments[i];
        const id = appointment.id;
        console.log("Completing appointment nº" + id);
        let status: number = getRandomNumber(1, 5);

        switch (status) {
            case 1:
                await client.patch(`/appointments/${id}/status`, {status: AppointmentStatus.CANCELADO});
                await client.post('/feedback', getRandomFeedback(FeedbackReason.CANCELATION, appointment));
                break;

            case 2:
                await client.patch(`/appointments/${id}/status`, {status: AppointmentStatus.RECHAZADO});
                await client.post('/feedback', getRandomFeedback(FeedbackReason.REJECTION, appointment));
                break;

            default:
                await client.patch(`/appointments/${id}/status`, {status: AppointmentStatus.ACEPTADO});
                await client.patch(`/appointments/${id}/status`, {status: AppointmentStatus.REALIZADO});
                await client.post('/feedback', getRandomFeedback(FeedbackReason.REVIEW, appointment));
                await client.patch(`/appointments/${id}/rating`, {rating: getRandomNumber(1, 5)});
                await client.post('/feedback', getRandomFeedback(FeedbackReason.CALIFICATION, appointment));
                await client.post('/surveys', getRandomSurvey(id));
                break;
        }

        console.log(`Appointment Nº${id} completed`);
    }
}