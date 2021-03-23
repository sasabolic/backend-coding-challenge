export class Notification {
    type: NotificationType;
    message: string;
    autoClose: boolean;

    constructor(init?: Partial<Notification>) {
        Object.assign(this, init);
    }
}

export enum NotificationType {
    Success,
    Error,
}
