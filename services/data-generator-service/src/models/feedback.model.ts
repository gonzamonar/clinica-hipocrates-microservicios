import { FeedbackReason } from "./enums/feedback-reason.enum";
import { FeedbackType } from "./enums/feedback-type.enum";

export interface Feedback {
    id: number;
    type: FeedbackType;
    appointmentId: number;
    authorId: number;
    reason: FeedbackReason;
    text: string;
}
