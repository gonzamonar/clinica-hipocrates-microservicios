import { Survey } from '../models/survey.model';
import { getRandom, getRandomNumber } from '../utils/helpers';

export function getRandomSurvey(appointmentId: number): Survey {
    return {
        id: 0,
        appointmentId: appointmentId,
        websiteCalification: getRandomNumber(1, 5),
        websiteComment: getRandom(websiteComments),
        specialistCalification: getRandomNumber(1, 5),
        specialistComment: getRandom(specialistComments),
        appointmentCalification: getRandomNumber(1, 5),
        appointmentComment: getRandom(appointmentComments)
    }
}

const websiteComments = [
  "Muy fácil de usar, encontré todo rápido.",
  "La plataforma funciona bien, sin errores.",
  "La interfaz es clara, pero podría ser más atractiva.",
  "Tuve problemas para iniciar sesión.",
  "Sería bueno tener recordatorios por WhatsApp.",
  "Faltan más opciones para buscar especialistas.",
  "Carga rápido, buena experiencia en general.",
  "Algo confusa la parte de agendado.",
  "Tuve que recargar varias veces para que funcione.",
  "Excelente idea, muy útil para organizar mis turnos.",
];

const specialistComments = [
  "Muy profesional y atento.",
  "Respondió todas mis dudas con paciencia.",
  "Me sentí cómodo durante la consulta.",
  "Fue algo apurado, pero correcto.",
  "Muy buena atención, volvería sin dudas.",
  "Me gustaría que explique un poco más.",
  "Puntual y cordial.",
  "No me inspiró confianza.",
  "Me derivó correctamente al especialista indicado.",
  "Excelente trato humano.",
];

const appointmentComments = [
  "Todo salió perfecto, sin demoras.",
  "Me atendieron a horario, muy organizado.",
  "Se atrasó un poco el turno.",
  "Muy buena experiencia en general.",
  "Hubo algo de espera, pero valió la pena.",
  "No me notificaron la confirmación.",
  "Sistema ágil, recomiendo usarlo.",
  "El especialista fue puntual y atento.",
  "No pude encontrar el lugar fácilmente.",
  "Volvería a sacar turno por esta vía.",
];