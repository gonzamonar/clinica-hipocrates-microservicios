import axios from 'axios';

export async function login(email: string, pwd: string): Promise<string> {
    const res = await axios.post('http://localhost:8080/auth/token-login', { email, pwd });
    return res.data.token;
}