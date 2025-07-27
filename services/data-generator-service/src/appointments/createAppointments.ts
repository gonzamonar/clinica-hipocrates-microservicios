import { AxiosInstance } from "axios";
import { User } from "../models/user.model"
import { UserType } from "../models/enums/user-type.enum";
import { getRandom, getRandomFutureDate } from "../utils/helpers";
import { Speciality } from "../models/speciality.model";

export async function createRandomAppointments(count: number, client: AxiosInstance) {
  const users: User[] = (await client.get('/users')).data;
  const patients: User[] = users.filter(u => u.userType == UserType.PACIENTE);
  const specialists: User[] = users.filter(u => u.userType == UserType.ESPECIALISTA);

  for (let i = 0; i < count; i++) {
    const patient: User = getRandom(patients);
    const specialist: User = getRandom(specialists);
    const speciality: Speciality = getRandom(specialist.specialities!);

    const appointment = {
      specialistId: specialist.id,
      patientId: patient.id,
      specialityId: speciality.id,
      dateTime: getRandomFutureDate(),
    };

    await client.post("/appointments", appointment);

    console.log(`Appointment ${i + 1} created`);
  }
}

