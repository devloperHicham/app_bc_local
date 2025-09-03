import { jwtDecode } from 'jwt-decode';
import { DecodedToken } from '../modules/auth-tokens.model';

export function decodeToken(token: string): DecodedToken | null {
  try {
    return jwtDecode(token);
  } catch {
    return null;
  }
}

export function isTokenExpired(token: string): boolean {
  const decoded = decodeToken(token);
  if (!decoded?.exp) return true;
  return Date.now() >= decoded.exp * 1000;
}