export interface RegisterRequest {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  dni: string;
}

export interface RegisterResponse {
  id: string;
  email: string;
  firstName: string;
  lastName: string;
}
