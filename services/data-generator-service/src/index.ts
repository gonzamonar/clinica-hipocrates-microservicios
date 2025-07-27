import { login } from './auth';
import { createClient } from './utils/apiClient';
import { createAdmin, createPatient, createSpecialist } from './users/createUsers';
import { createRandomAppointments } from './appointments/createAppointments';
import { finishAppointments } from './appointments/finishAppointments';

async function main() {
    console.log("DATA GENERATOR INIT");

    const token = await login('gonza.monar@gmail.com', 'microservicios');
    const client = createClient(token);


    console.log("EXEC FINISHED");
}

main().catch((err) => {
    console.error("Error in generator:", err);
    process.exit(1);
});