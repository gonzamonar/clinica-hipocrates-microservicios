import { Appointment } from '../models/appointment.model';
import { FeedbackReason } from '../models/enums/feedback-reason.enum';
import { FeedbackType } from '../models/enums/feedback-type.enum';
import { Feedback } from '../models/feedback.model';
import { getRandom } from '../utils/helpers';

const cancelationComments = [
    "No voy a poder asistir, disculpas.",
    "Tuve un imprevisto de último momento.",
    "Voy a reprogramar para otro día.",
    "Me olvidé del turno, lo cancelo.",
    "No me siento bien hoy para asistir.",
    "Tengo otro compromiso, lo lamento.",
    "Cancelado por problemas personales.",
    "Prefiero recoordinar en otra fecha.",
];

const rejectionComments = [
    "No estaré disponible en ese horario.",
    "El turno fue asignado fuera de mi disponibilidad.",
    "Ya tengo otro compromiso en esa franja.",
    "No puedo atender ese día.",
    "Ese turno fue asignado por error.",
    "El paciente no cumple los requisitos para la consulta.",
    "Rechazado por solapamiento de agenda.",
];

const calificationComments = [
    "Excelente atención, muy conforme.",
    "Muy buena predisposición del especialista.",
    "Todo fue según lo esperado.",
    "No me sentí del todo cómodo.",
    "Muy puntual y claro en la explicación.",
    "El turno se demoró demasiado.",
    "Volvería a atenderme sin dudas.",
    "No respondió todas mis dudas.",
];

const reviewComments = [
    "El paciente llegó a horario y fue muy claro.",
    "Buena disposición, seguiremos el tratamiento.",
    "Necesita estudios complementarios.",
    "No trajo los estudios previos solicitados.",
    "Consulta productiva, se indicó seguimiento.",
    "Se notó compromiso con el tratamiento.",
    "Paciente poco colaborativo, revisar en próxima visita.",
];

export function getRandomFeedback(reason: FeedbackReason, appointment: Appointment): Feedback {
    switch (reason) {
        case FeedbackReason.CANCELATION:
            return {
                id: 0,
                type: FeedbackType.COMMENT,
                reason: FeedbackReason.CANCELATION,
                appointmentId: appointment.id,
                authorId: appointment.patientId,
                text: getRandom(cancelationComments)
            }

        case FeedbackReason.REJECTION:
            return {
                id: 0,
                type: FeedbackType.COMMENT,
                reason: FeedbackReason.REJECTION,
                appointmentId: appointment.id,
                authorId: appointment.specialistId,
                text: getRandom(rejectionComments)
            }
        
        case FeedbackReason.CALIFICATION:
            return {
                id: 0,
                type: FeedbackType.COMMENT,
                reason: FeedbackReason.CALIFICATION,
                appointmentId: appointment.id,
                authorId: appointment.patientId,
                text: getRandom(calificationComments)
            }

        case FeedbackReason.REVIEW:
            return {
                id: 0,
                type: FeedbackType.REVIEW,
                reason: FeedbackReason.REVIEW,
                appointmentId: appointment.id,
                authorId: appointment.specialistId,
                text: getRandom(reviewComments)
            }
    }
}