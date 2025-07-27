import { UserType } from "./enums/user-type.enum";
import { Speciality } from "./speciality.model";

export interface User {
    id: number;
    enabled: boolean;
    userType: UserType;
    name: string;
    lastname: string;
    age: number;
    dni: number;
    email: string;
    profilePic: string;
    healthInsurance: string | null;
    profilePicAlt: string | null;
    specialities: Speciality[] | null;
}
