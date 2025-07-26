
export interface LoginAPIRequest {
  email: string;
  pwd: string;
}

export interface LoginAPIResponse {
  token: string;
}

export interface LoginResult {
  token: string;
  error: boolean;
}
