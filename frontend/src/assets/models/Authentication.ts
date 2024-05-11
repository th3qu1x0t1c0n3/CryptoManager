
export interface IUser {
    email: string;
    firstName: string;
    lastName: string;
    role: string;
    token: string;
}

export interface IUserProfile {
    email: string;
    firstName: string;
    lastName: string;
}

export interface IsignIn {
    email: string;
    password: string;
}

export interface IsignUp {
    email: string;
    firstName: string;
    lastName: string;
    password: string;
}