
export interface IUser {
    username: string;
    firstName: string;
    lastName: string;
    role: string;
    portfolioSize: number;
    token: string;
}

export interface IUserProfile {
    username: string;
    firstName: string;
    lastName: string;
}

export interface IsignIn {
    username: string;
    password: string;
}

export interface IsignUp {
    username: string;
    firstName: string;
    lastName: string;
    password: string;
}