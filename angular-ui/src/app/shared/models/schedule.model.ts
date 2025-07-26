import { Time } from "@angular/common";
import { DayOfWeek } from "./enums/day-of-week.enum";

export interface Schedule {
    id: number;
    specialistId: number;
    day: DayOfWeek;
    start: Time;
    end: Time;
}
