import { faker } from '@faker-js/faker';
import { AxiosInstance } from 'axios';
import { getRandomUniqueNumbers } from "../utils/helpers";

export async function createPatient(client: AxiosInstance) {
  const patient = {
    email: faker.internet.email(),
    pwd: '123456',
    userType: 'PACIENTE',
    name: faker.person.firstName(),
    lastname: faker.person.lastName(),
    age: faker.number.int({ min: 18, max: 85 }),
    dni: faker.number.int({ min: 1000000, max: 50000000 }),
    profilePic: 'default.jpg',
    healthInsurance: faker.company.name(),
    profilePicAlt: 'default-alt.jpg',
  };

  await client.post('/auth/register', patient);
}

export async function createSpecialist(client: AxiosInstance) {
  const specialityCount = faker.number.int({ min: 1, max: 4 });
  const specialities = getRandomUniqueNumbers(1, 10, specialityCount);

  const specialist = {
    email: faker.internet.email(),
    pwd: '123456',
    userType: 'ESPECIALISTA',
    name: faker.person.firstName(),
    lastname: faker.person.lastName(),
    age: faker.number.int({ min: 18, max: 85 }),
    dni: faker.number.int({ min: 1000000, max: 50000000 }),
    profilePic: 'default.jpg',
    specialities: specialities
  };

  await client.post('/auth/register', specialist);
}

export async function createAdmin(client: AxiosInstance) {
  const admin = {
    email: faker.internet.email(),
    pwd: '123456',
    userType: 'ADMIN',
    name: faker.person.firstName(),
    lastname: faker.person.lastName(),
    age: faker.number.int({ min: 18, max: 85 }),
    dni: faker.number.int({ min: 1000000, max: 50000000 }),
    profilePic: 'default.jpg'
  };

  await client.post('/auth/register', admin);
}