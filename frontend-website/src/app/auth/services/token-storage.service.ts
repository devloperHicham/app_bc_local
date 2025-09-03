import { Injectable } from '@angular/core';
import { AuthTokens } from '../modules/auth-tokens.model';

@Injectable({ providedIn: 'root' })
export class TokenStorageService {
  private readonly ACCESS_TOKEN_KEY = 'access_token';
  private readonly REFRESH_TOKEN_KEY = 'refresh_token';
  private readonly TOKEN_EXPIRATION_KEY = 'token_expiration';
  private readonly LAST_ACTIVITY_KEY = 'last_activity';
  private readonly INACTIVITY_TIMEOUT = 15 * 60 * 1000; // 15 minutes

  /**
   * Saves authentication tokens with validation
   * @param tokens AuthTokens object containing tokens
   * @throws Error if tokens are invalid
   */
  saveTokens(tokens: AuthTokens): void {
    try {
      const { accessToken, refreshToken } = this.extractTokens(tokens);
      this.validateTokens(accessToken, refreshToken);

      localStorage.setItem(this.ACCESS_TOKEN_KEY, accessToken);
      localStorage.setItem(this.REFRESH_TOKEN_KEY, refreshToken);
      this.updateLastActivity();

      if (tokens.response?.accessTokenExpiresAt) {
        this.setTokenExpiration(tokens.response.accessTokenExpiresAt);
      }
    } catch (error) {
      this.clear();
      throw error;
    }
  }

  /**
   * Clears all authentication tokens from storage
   */
  clear(): void {
    try {
      localStorage.removeItem(this.ACCESS_TOKEN_KEY);
      localStorage.removeItem(this.REFRESH_TOKEN_KEY);
      localStorage.removeItem(this.TOKEN_EXPIRATION_KEY);
      localStorage.removeItem(this.LAST_ACTIVITY_KEY);
    } catch (error) {
      console.error('Error clearing tokens:', error);
    }
  }

  getAccessToken(): string | null {
    return this.getToken(this.ACCESS_TOKEN_KEY);
  }

  getRefreshToken(): string | null {
    return this.getToken(this.REFRESH_TOKEN_KEY);
  }

  isTokenExpired(): boolean {
    const lastActivity = this.getLastActivity();
    if (!lastActivity) return true;

    return Date.now() - lastActivity > this.INACTIVITY_TIMEOUT;
  }

  hasAccessToken(): boolean {
    return !!this.getAccessToken();
  }

  hasRefreshToken(): boolean {
    return !!this.getRefreshToken();
  }

  isAuthenticated(): boolean {
    return this.hasAccessToken() && !this.isTokenExpired();
  }

  private extractTokens(tokens: AuthTokens): {
    accessToken: string;
    refreshToken: string;
  } {
    return {
      accessToken: tokens.response?.accessToken ?? tokens.accessToken ?? '',
      refreshToken: tokens.response?.refreshToken ?? tokens.refreshToken ?? '',
    };
  }

  private validateTokens(accessToken: string, refreshToken: string): void {
    if (!accessToken || !refreshToken) {
      throw new Error(
        'Invalid tokens: Both access and refresh tokens are required'
      );
    }

    if (typeof accessToken !== 'string' || typeof refreshToken !== 'string') {
      throw new Error('Invalid tokens: Tokens must be strings');
    }
  }

  private getToken(key: string): string | null {
    try {
      return localStorage.getItem(key);
    } catch (error) {
      console.error(`Error getting ${key}:`, error);
      return null;
    }
  }

  private updateLastActivity(): void {
    try {
      localStorage.setItem(this.LAST_ACTIVITY_KEY, Date.now().toString());
    } catch (error) {
      console.error('Error updating last activity:', error);
    }
  }

  private getLastActivity(): number | null {
    const lastActivity = localStorage.getItem(this.LAST_ACTIVITY_KEY);
    return lastActivity ? parseInt(lastActivity, 10) : null;
  }

  private setTokenExpiration(expiresIn: number): void {
    try {
      const expirationDate = new Date();
      expirationDate.setSeconds(expirationDate.getSeconds() + expiresIn);
      localStorage.setItem(
        this.TOKEN_EXPIRATION_KEY,
        expirationDate.toISOString()
      );
    } catch (error) {
      console.error('Error setting token expiration:', error);
    }
  }
}
