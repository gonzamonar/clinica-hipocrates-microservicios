import { login } from './auth';
import { createClient } from './utils/apiClient';
import { createAdmin, createPatient, createSpecialist } from './users/createUsers';
import { createRandomAppointments } from './appointments/createAppointments';
import { finishAppointments } from './appointments/finishAppointments';

async function main() {
    console.log("DATA GENERATOR INIT");

    const token = await login('gonza.monar@gmail.com', 'microservicios');
    const client = createClient(token);

    /*
    CREAR USUARIOS
    for (let i = 0; i < 40; i++) {
        await createPatient(client);
        console.log(`Created patient ${i + 1}`);
    }
    
    for (let i = 0; i < 10; i++) {
        await createSpecialist(client);
        console.log(`Created specialist ${i + 1}`);
    }
    
    for (let i = 0; i < 5; i++) {
        await createAdmin(client);
        console.log(`Created admin ${i + 1}`);
    }
    */


    // CREAR HORARIOS
    

    // CREAR TURNOS
    //await createRandomAppointments(50, client);

    // COMPLETAR TURNOS
    //await finishAppointments(client);


    console.log("EXEC FINISHED");
}

main().catch((err) => {
    console.error("Error in generator:", err);
    process.exit(1);
});